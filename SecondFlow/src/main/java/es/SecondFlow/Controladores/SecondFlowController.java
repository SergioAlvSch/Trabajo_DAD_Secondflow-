package es.SecondFlow.Controladores;

import antlr.Token;
import es.SecondFlow.Entidades.*;
import es.SecondFlow.Servicios.*;
import es.SecondFlow.ServicioInterno.*;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Controller
public class SecondFlowController {

    CsrfToken token;

    @Autowired
    private GestionProductos gestionProductos;

    @Autowired
    private GestionUsuarios gestionUsuarios;

    @Autowired
    private GestionConversaciones gestionConversaciones;
    @Autowired
    private GestionMensajes gestionMensajes;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String mostrarPaginaPrincipalSinSesion(Model model, HttpServletRequest request) {
        mostrarListaProductos(model);
        Principal usuario = request.getUserPrincipal();
        if (usuario != null) {

            model.addAttribute("logged", true);
            model.addAttribute("admin", model.getAttribute("admin"));
            model.addAttribute("usuario", gestionUsuarios.findByNombre(usuario.getName()));
            return "Inicio";
        }
        model.addAttribute("logged", false);


        return "InicioSinSesion";
    }

    @GetMapping("/Inicio")
    @ModelAttribute
    public String mostrarPaginaPrincipal(Model model, HttpServletRequest request) {
        Principal usuario = request.getUserPrincipal();
        mostrarListaProductos(model);
        if (usuario == null) {
            return "InicioSinSesion";
        }
        model.addAttribute("logged", true);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        model.addAttribute("usuario", gestionUsuarios.findByNombre(usuario.getName()));
        String aux = usuario.getName();

        return "Inicio";
    }

    @GetMapping("/Perfil/{id}")
    public String mostrarPerfil(Model model, @PathVariable long id, HttpServletRequest request) {
        Optional<Usuario> u = gestionUsuarios.findById(id);
        if (u.isPresent()) {
            if (request.getUserPrincipal() == null) {
                model.addAttribute("logged", false);
                model.addAttribute("usuario", u.get());
                return "Perfil";
            } else {
                model.addAttribute("logged", true);
                model.addAttribute("admin", request.isUserInRole("ADMIN"));
                model.addAttribute("usuario", u.get());
                return "Perfil";
            }

        } else {
            return "InicioSinSesion";
        }
    }

    @RequestMapping("/buscar")
    public String busqueda(Model model, String nombreProductoABuscar, HttpServletRequest request) {
        Principal usuario = request.getUserPrincipal();
        model.addAttribute("listaProductos", gestionProductos.findByNombre(nombreProductoABuscar));
        if (usuario == null) {
            return "inicioSinSesion";
        }

        model.addAttribute("logged", true);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        model.addAttribute("usuario", gestionUsuarios.findByNombre(usuario.getName()));
        return "Inicio";
    }


    @GetMapping("/productos")
    public String mostrarListaProductos(Model model) {
        model.addAttribute("listaProductos", gestionProductos.findAllDisponibles());
        return "productos";
    }

    @GetMapping("/productos/Comprados/{id}")
    public String mostrarHistorialCompras(Model model, @PathVariable long id, HttpServletRequest request) {
        Principal usuario = request.getUserPrincipal();
        Usuario aux = gestionUsuarios.findByNombre(usuario.getName());
        model.addAttribute("logged", true);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        if (aux.getId() == id) {
            model.addAttribute("usuario", aux);
            model.addAttribute("listaProductos", aux.getListaProductosComprados());
            return "MiListaProductosComprados";
        }

        model.addAttribute("usuario", gestionUsuarios.findByNombre(usuario.getName()));
        return "Inicio";
    }


    @GetMapping("/productos/{id}")
    public String getProducto(Model model, @PathVariable long id, HttpServletRequest request) {
        Principal usuario = request.getUserPrincipal();
        Usuario aux = null;

        if (usuario != null) {
            aux = gestionUsuarios.findByNombre(usuario.getName());

            model.addAttribute("logged", true);
        } else {

            model.addAttribute("logged", false);
        }

        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        Optional<Producto> p = gestionProductos.findById(id);

        if (p.isPresent()) {
            Boolean isVendedor;
            if (aux == null) {
                isVendedor = false;
            } else {
                isVendedor = aux.getNombreUsuario().equals(p.get().getVendedor().getNombreUsuario());
            }

            model.addAttribute("isVendedor", isVendedor);
            model.addAttribute("producto", p.get());
            return "producto";
        } else {
            model.addAttribute("listaProductos", gestionProductos.findAllDisponibles());
            return "productos";
        }
    }

    @GetMapping("/comprar/producto/{id}")
    public String comprarProducto(Model model, @PathVariable long id, HttpServletRequest request) {
        Principal usuario = request.getUserPrincipal();
        Usuario aux = gestionUsuarios.findByNombre(usuario.getName());
        Optional<Producto> p = gestionProductos.findById(id);

        model.addAttribute("logged", true);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        if (p.isPresent()) {
            Usuario vendedor = p.get().getVendedor();
            Usuario comprador = aux;
            Boolean isVendedor = aux.getNombreUsuario().equals(vendedor.getNombreUsuario());
            if (isVendedor) {
                model.addAttribute("listaProductos", gestionProductos.findAllDisponibles());
                return "Inicio";
            }
            model.addAttribute("isVendedor", isVendedor);
            model.addAttribute("producto", p.get());


            Conversacion c = gestionConversaciones.findByUsuarios(comprador, vendedor, p.get());
            if (c == null) {
                c = new Conversacion(comprador, vendedor, p.get());
                gestionConversaciones.save(c);
                comprador.getListaMisConversacionesE().add(c);
                vendedor.getListaMisConversacionesR().add(c);
                gestionUsuarios.update();
            }
            List<Conversacion> auxConversaciones = new ArrayList<>();
            auxConversaciones.addAll(comprador.getListaMisConversacionesE());
            auxConversaciones.addAll(comprador.getListaMisConversacionesR());
            model.addAttribute("listaConversaciones", auxConversaciones);
            model.addAttribute("conversacionAbierta", c);
            model.addAttribute("listaMensajes", c.getListaMensajes());
            model.addAttribute("idComprador", comprador.getId());
            model.addAttribute("id", comprador.getId());
            return "Chat";
        } else {
            model.addAttribute("listaProductos", gestionProductos.findAllDisponibles());
            return "Inicio";
        }
    }

    @RequestMapping("/producto/vendido/{id}/{idComprador}")
    public String productoVendido(Model model, @PathVariable long id, @PathVariable long idComprador, HttpServletRequest request) {
        Principal usuario = request.getUserPrincipal();
        Optional<Producto> p = gestionProductos.findById(id);

        model.addAttribute("logged", true);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        if (p.isPresent()) {
            Usuario vendedor = p.get().getVendedor();
            Optional<Usuario> comprador = gestionUsuarios.findById(idComprador);
            if (comprador.isPresent()) {
                comprador.get().getListaProductosComprados().add(p.get());
                p.get().setHayComprador();
                vendedor.getListaProductos().remove(p.get());
                p.get().setComprador(comprador.get());
                gestionProductos.delete(p.get());

                gestionConversaciones.update();
                gestionUsuarios.update();
                gestionProductos.update();
                model.addAttribute("producto", p.get());
                if (ServicioInternoEmail.sendCompraVentaEmail(p.get())) {
                    return "productoVendido";
                }
                return "productoVendido";
            }
        }
        model.addAttribute("listaProductos", gestionProductos.findAllDisponibles());
        model.addAttribute("usuario", gestionUsuarios.findByNombre(usuario.getName()));
        return "Inicio";
    }

    @GetMapping("/eliminarProducto/{id}")
    public String eliminarProducto(Model model, @PathVariable long id, HttpServletRequest request) {
        Principal usuario = request.getUserPrincipal();
        Optional<Producto> producto = gestionProductos.findById(id);

        model.addAttribute("logged", true);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            List<Conversacion> conversacionesAsociadas = gestionConversaciones.findByProducto(producto.get());
            for (Conversacion aux : conversacionesAsociadas) {
                gestionConversaciones.delete(aux.getId());
            }
            gestionProductos.delete(producto.get());
            return "productoeliminado";
        } else {
            model.addAttribute("usuario", gestionUsuarios.findByNombre(usuario.getName()));
            model.addAttribute("listaProductos", gestionProductos.findAllDisponibles());
            return "Inicio";
        }
    }


    @GetMapping("/modificando/{id}")
    public String modificarProducto(Model model, @PathVariable long id, HttpServletRequest request) {
        Principal usuario = request.getUserPrincipal();
        Optional<Producto> producto = gestionProductos.findById(id);

        model.addAttribute("logged", true);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "modificarProducto";
        } else {
            model.addAttribute("usuario", gestionUsuarios.findByNombre(usuario.getName()));
            model.addAttribute("listaProductos", gestionProductos.findAllDisponibles());
            return "Inicio";
        }
    }

    @RequestMapping("/modificado/{id}")
    public String productoModificado(Model model, @PathVariable long id, String nombreProducto, String categoriaProducto, double precioProducto, String descripcionProducto, MultipartFile imageField, boolean removeImage, HttpServletRequest request) throws IOException {

        Principal usuario = request.getUserPrincipal();
        Optional<Producto> producto = gestionProductos.findById(id);

        model.addAttribute("logged", true);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        if (producto.isPresent()) {
            producto.get().setNombre(nombreProducto);
            producto.get().setCategoria(categoriaProducto);
            producto.get().setPrecio(precioProducto);
            producto.get().setDescripcion(descripcionProducto);
            try {
                updateImage(producto.get(), removeImage, imageField);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            gestionProductos.update();
            model.addAttribute("producto", producto.get());
            model.addAttribute("isVendedor", true);
            return "producto";
        } else {
            model.addAttribute("usuario", gestionUsuarios.findByNombre(usuario.getName()));
            model.addAttribute("listaProductos", gestionProductos.findAllDisponibles());
            return "Inicio";
        }
    }

    @PostMapping("/NuevoProducto")
    public String crearProducto(Model model, HttpServletRequest request, String nombreProducto, String categoriaProducto, String descripcionProducto, double precioProducto, MultipartFile imageField) throws IOException {
        Principal usuario = request.getUserPrincipal();
        Usuario aux = gestionUsuarios.findByNombre(usuario.getName());
        Producto p = new Producto(nombreProducto, categoriaProducto, descripcionProducto, precioProducto, aux);

        model.addAttribute("logged", true);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        if (!imageField.isEmpty()) {
            p.setImagenProducto(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
            p.setHayImagen(true);
        }
        aux.getListaProductos().add(p);
        model.addAttribute("isVendedor", true);
        model.addAttribute("producto", p);
        gestionProductos.save(p);
        return "producto";
    }

    private void updateImage(Producto producto, boolean removeImage, MultipartFile imageField) throws IOException, SQLException {

        if (!imageField.isEmpty()) {
            producto.setImagenProducto(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
            producto.setHayImagen(true);
        } else {
            if (removeImage) {
                producto.setImagenProducto(null);
                producto.setHayImagen(false);
            } else {
                Producto dbProducto = gestionProductos.findById(producto.getId()).orElseThrow();
                if (dbProducto.isHayImagen()) {
                    producto.setImagenProducto(BlobProxy.generateProxy(dbProducto.getImagenProducto().getBinaryStream(),
                            dbProducto.getImagenProducto().length()));
                    producto.setHayImagen(true);
                }
            }
        }
    }


    @GetMapping("/Formulario")
    public String Formulario(Model model, HttpServletRequest request) {

        model.addAttribute("logged", true);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        return "FormularioProducto";
    }


    @GetMapping("/producto/{id}/image")
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException {

        Optional<Producto> producto = gestionProductos.findById(id);
        if (producto.isPresent() && producto.get().getImagenProducto() != null) {

            Resource file = new InputStreamResource(producto.get().getImagenProducto().getBinaryStream());

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/png")
                    .contentLength(producto.get().getImagenProducto().length()).body(file);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/conversaciones/{id}")
    public String mostrarListaConversaciones(Model model, @PathVariable long id, HttpServletRequest request) {
        Principal usuario = request.getUserPrincipal();
        Usuario aux = gestionUsuarios.findByNombre(usuario.getName());

        model.addAttribute("logged", true);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        if (aux.getId() == id) {
            model.addAttribute("listaConversacionesR", aux.getListaMisConversacionesR());
            model.addAttribute("listaConversacionesE", aux.getListaMisConversacionesE());
            return "ListaConversaciones";
        }
        model.addAttribute("usuario", aux);
        model.addAttribute("listaProductos", gestionProductos.findAllDisponibles());
        return "Inicio";
    }

    @RequestMapping("/conversacion/{id}")
    public String getConversación2(Model model, @PathVariable long id, HttpServletRequest request) {

        Principal usuario = request.getUserPrincipal();
        Optional<Conversacion> conversacion = gestionConversaciones.findById(id);

        model.addAttribute("logged", true);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        if (conversacion.isPresent()) {

            conversacion.get().getListaMensajes();
            Producto p = conversacion.get().getProducto();

            Usuario usuarioEmisor = conversacion.get().getEmisor();

            if (usuarioEmisor.getId() == gestionUsuarios.findByNombre(usuario.getName()).getId()) {
                List<Conversacion> auxConversaciones = new ArrayList<>();
                auxConversaciones.addAll(usuarioEmisor.getListaMisConversacionesE());
                auxConversaciones.addAll(usuarioEmisor.getListaMisConversacionesR());
                for (Mensaje aux : conversacion.get().getListaMensajes()) {
                    if (aux.getIdEmisor() == usuarioEmisor.getId())
                        aux.setUsuarioLog(true);
                    else
                        aux.setUsuarioLog(false);
                }
                model.addAttribute("isVendedor", false);
                model.addAttribute("producto", p);
                model.addAttribute("idComprador", usuarioEmisor.getId());
                model.addAttribute("conversacionAbierta", conversacion.get());
                model.addAttribute("listaMensajes", conversacion.get().getListaMensajes());
                model.addAttribute("listaConversaciones", auxConversaciones);
                model.addAttribute("id", usuarioEmisor.getId());
                return "Chat";
            } else {

                List<Conversacion> auxConversaciones = new ArrayList<>();
                auxConversaciones.addAll(usuarioEmisor.getListaMisConversacionesE());
                auxConversaciones.addAll(usuarioEmisor.getListaMisConversacionesR());

                for (Mensaje aux : conversacion.get().getListaMensajes()) {
                    if (aux.getIdEmisor() == p.getVendedor().getId())
                        aux.setUsuarioLog(true);
                    else
                        aux.setUsuarioLog(false);
                }

                model.addAttribute("isVendedor", true);
                model.addAttribute("producto", p);
                model.addAttribute("idComprador", conversacion.get().getEmisor().getId());
                model.addAttribute("conversacionAbierta", conversacion.get());
                model.addAttribute("listaMensajes", conversacion.get().getListaMensajes());
                model.addAttribute("listaConversaciones", auxConversaciones);
                model.addAttribute("id", p.getVendedor().getId());

                return "Chat";
            }


        } else {
            model.addAttribute("usuario", gestionUsuarios.findByNombre(usuario.getName()));
            return "Inicio";
        }
    }

    @RequestMapping("/conversacion/{id}/Enviado/{idEmisor}")
    public String getConversación(Model model, @PathVariable long id, String mensaje, @PathVariable long idEmisor, HttpServletRequest request) {

        Principal usuario = request.getUserPrincipal();
        Optional<Conversacion> conversacion = gestionConversaciones.findById(id);

        model.addAttribute("logged", true);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        if (conversacion.isPresent()) {
            Mensaje nuevoMensaje = new Mensaje(mensaje, LocalDateTime.now(), idEmisor, conversacion.get(), gestionUsuarios.findById(idEmisor).get().getNombreUsuario());
            gestionMensajes.save(nuevoMensaje);
            conversacion.get().getListaMensajes().add(nuevoMensaje);
            gestionConversaciones.update();
            gestionMensajes.update();
            Producto p = conversacion.get().getProducto();

            Optional<Usuario> usuarioEmisor = gestionUsuarios.findById(idEmisor);
            if (usuarioEmisor.isPresent()) {
                List<Conversacion> auxConversaciones = new ArrayList<>();
                auxConversaciones.addAll(usuarioEmisor.get().getListaMisConversacionesE());
                auxConversaciones.addAll(usuarioEmisor.get().getListaMisConversacionesR());
                for (Mensaje aux : conversacion.get().getListaMensajes()) {
                    if (aux.getIdEmisor() == idEmisor)
                        aux.setUsuarioLog(true);
                    else
                        aux.setUsuarioLog(false);
                }
                long idUsuarioLog = gestionUsuarios.findByNombre(usuario.getName()).getId();
                model.addAttribute("producto", p);
                model.addAttribute("isVendedor", idUsuarioLog == p.getVendedor().getId());
                model.addAttribute("idComprador", conversacion.get().getEmisor().getId());
                model.addAttribute("conversacionAbierta", conversacion.get());
                model.addAttribute("listaMensajes", conversacion.get().getListaMensajes());
                model.addAttribute("listaConversaciones", auxConversaciones);
                model.addAttribute("id", idUsuarioLog);
                return "Chat";
            }

            model.addAttribute("usuario", gestionUsuarios.findByNombre(usuario.getName()));
            return "Inicio";

        } else {
            model.addAttribute("usuario", gestionUsuarios.findByNombre(usuario.getName()));
            return "Inicio";
        }
    }

    @RequestMapping("/login")
    public String login(Model model, HttpServletRequest request) {

        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        return "IniciarSesion";
    }

    @GetMapping("/registrarse")
    public String irARegistrarse(Model model) {
        model.addAttribute("nombreTemp", "");
        model.addAttribute("correoTemp", "");
        model.addAttribute("passwordTemp", "");
        model.addAttribute("password2Temp", "");
        model.addAttribute("errorNombre", false);
        model.addAttribute("errorClave", false);
        return "Registrarse";
    }

    @PostMapping("/")
    public String registrado(Model model, String nombreUsuario, String passwordUsuario, String password2Usuario, String correoUsuario) {
        if (password2Usuario.equals(passwordUsuario) && gestionUsuarios.findByNombre(nombreUsuario) == null) {
            Usuario registrado = new Usuario(nombreUsuario, passwordEncoder.encode(password2Usuario), correoUsuario, "USER");
            gestionUsuarios.save(registrado);

            if (ServicioInternoEmail.sendRegisterEmail(registrado)) {
                return "InicioSinSesion";
            }
            return "InicioSinSesion";
        }
        if (password2Usuario != passwordUsuario) {
            model.addAttribute("errorClave", true);

            model.addAttribute("passwordTemp", "");
            model.addAttribute("password2Temp", "");
        } else {
            model.addAttribute("passwordTemp", passwordUsuario);
            model.addAttribute("password2Temp", password2Usuario);
        }
        if (gestionUsuarios.findByNombre(nombreUsuario) != null) {
            model.addAttribute("errorNombre", true);
        }
        model.addAttribute("correoTemp", correoUsuario);
        model.addAttribute("nombreTemp", nombreUsuario);
        return "Registrarse";
    }

    @PostMapping("/login")
    public String errorInicio(Model model, String nombreUsuario, String passwordUsuario, String password2Usuario, String correoUsuario) {
        if (password2Usuario.equals(passwordUsuario) && gestionUsuarios.findByNombre(nombreUsuario) == null) {
            Usuario registrado = new Usuario(nombreUsuario, passwordEncoder.encode(password2Usuario), correoUsuario, "USER");
            gestionUsuarios.save(registrado);

            if (ServicioInternoEmail.sendRegisterEmail(registrado)) {
                return "InicioSinSesion";
            }
            return "InicioSinSesion";
        }
        if (password2Usuario != passwordUsuario) {
            model.addAttribute("errorClave", true);

            model.addAttribute("passwordTemp", "");
            model.addAttribute("password2Temp", "");
        } else {
            model.addAttribute("passwordTemp", passwordUsuario);
            model.addAttribute("password2Temp", password2Usuario);
        }
        if (gestionUsuarios.findByNombre(nombreUsuario) != null) {
            model.addAttribute("errorNombre", true);
        }
        model.addAttribute("correoTemp", correoUsuario);
        model.addAttribute("nombreTemp", nombreUsuario);
        return "Registrarse";
    }

    @RequestMapping("/loginError")
    public String loginError() {
        return "loginError";
    }
}
