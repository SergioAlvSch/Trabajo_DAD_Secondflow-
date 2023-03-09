package es.SecondFlow.Controladores;

import es.SecondFlow.Entidades.*;
import es.SecondFlow.Servicios.*;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SecondFlowController {

    @Autowired
    private GestionProductos gestionProductos;

    @Autowired
    private GestionUsuarios gestionUsuarios;

    @Autowired
    private GestionConversaciones gestionConversaciones;
    @Autowired
    private GestionMensajes gestionMensajes;

    @GetMapping("/")
    public String mostrarPaginaPrincipal(Model model) {
/*

        //LUGAR DONDE PONEMOS LOS USUARIOS POR DEFECTO, SOLO SE HARÁ EN LA PRIMERA VEZ
        Usuario comprador=new Usuario("juan");
        Usuario vendedor=new Usuario("pepe");
        gestionUsuarios.save(comprador);
        gestionUsuarios.save(vendedor);
        gestionUsuarios.update();
*/
        Optional<Usuario> u = gestionUsuarios.findById(1);
        mostrarListaProductos(model);
        model.addAttribute("usuario", u.get());
        return "Inicio";
    }

    @GetMapping("/Perfil/{id}")
    public String mostrarPerfil(Model model, @PathVariable long id) {

        Optional<Usuario> u = gestionUsuarios.findById(id);//solo hay un usuario, como no se podrá pasar, pues debe de ser el 1
        if (u.isPresent()) {
            model.addAttribute("usuario", u.get());
            return "Perfil";
        } else {
            model.addAttribute("usuario", gestionUsuarios.findById(1).get());
            return "Inicio";
        }
    }

    @RequestMapping("/buscar")
    public String busqueda(Model model, String nombreProductoABuscar) {
        model.addAttribute("usuario", gestionUsuarios.findById(1).get());
        model.addAttribute("listaProductos", gestionProductos.findByNombre(nombreProductoABuscar));
        return "Inicio";
    }


    @GetMapping("/productos")
    public String mostrarListaProductos(Model model) {
        model.addAttribute("listaProductos", gestionProductos.findAllDisponibles());
        return "productos";
    }

    @GetMapping("/productos/Comprados/{id}")
    public String mostrarHistorialCompras(Model model, @PathVariable long id) {
        Optional<Usuario> aux = gestionUsuarios.findById(id);
        if (aux.isPresent()) {
            model.addAttribute("usuario", aux.get());
            model.addAttribute("listaProductos", aux.get().getListaProductosComprados());
            return "MiListaProductosComprados";
        }
        model.addAttribute("usuario", gestionUsuarios.findById(1).get());
        return "Inicio";
    }


    @GetMapping("/productos/{id}")
    public String getProducto(Model model, @PathVariable long id) {

        Optional<Producto> p = gestionProductos.findById(id);

        if (p.isPresent()) {
            model.addAttribute("producto", p.get());
            return "producto";
        } else {
            model.addAttribute("listaProductos", gestionProductos.findAllDisponibles());
            return "productos";
        }
    }

    @GetMapping("/comprar/producto/{id}")
    public String comprarProducto(Model model, @PathVariable long id) {

        Optional<Producto> p = gestionProductos.findById(id);

        if (p.isPresent()) {
            Usuario vendedor = p.get().getVendedor();
            Optional<Usuario> comprador = gestionUsuarios.findById(1);
            model.addAttribute("producto", p.get());
            Conversacion c = gestionConversaciones.findByUsuarios(comprador.get(), vendedor, p.get());
            if (c == null) {
                c = new Conversacion(comprador.get(), vendedor, p.get());
                gestionConversaciones.save(c);
                comprador.get().getListaMisConversacionesE().add(c);
                vendedor.getListaMisConversacionesR().add(c);
                gestionUsuarios.update();
            }
            List<Conversacion> auxConversaciones = new ArrayList<>();
            auxConversaciones.addAll(comprador.get().getListaMisConversacionesE());
            auxConversaciones.addAll(comprador.get().getListaMisConversacionesR());
            model.addAttribute("listaConversaciones", auxConversaciones);
            model.addAttribute("conversacionAbierta", c);
            model.addAttribute("listaMensajes", c.getListaMensajes());
            model.addAttribute("idComprador", comprador.get().getId());
            return "Chat";
        } else {
            model.addAttribute("listaProductos", gestionProductos.findAllDisponibles());
            return "productos";
        }
    }

    @RequestMapping("/producto/vendido/{id}/{idComprador}")
    public String productoVendido(Model model, @PathVariable long id, @PathVariable long idComprador) {
        Optional<Producto> p = gestionProductos.findById(id);
        if (p.isPresent()) {
            Usuario vendedor = p.get().getVendedor();
            Optional<Usuario> comprador = gestionUsuarios.findById(idComprador);
            if (comprador.isPresent()) {
                comprador.get().getListaProductosComprados().add(p.get());
                p.get().setHayComprador();
                vendedor.getListaProductos().remove(p.get());
                p.get().setComprador(comprador.get());
                gestionProductos.delete(p.get().getId());
                gestionUsuarios.update();
                gestionProductos.update();
                model.addAttribute("producto", p.get());
                return "productoVendido";
            }
        }
        model.addAttribute("usuario", gestionUsuarios.findById(1).get());
        return "Inicio";
    }

    @GetMapping("/eliminarProducto/{id}")
    public String eliminarProducto(Model model, @PathVariable long id) {

        Optional<Producto> producto = gestionProductos.findById(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            List<Conversacion> conversacionesAsociadas = gestionConversaciones.findByProducto(producto.get());
            for (Conversacion aux : conversacionesAsociadas) {
                gestionConversaciones.delete(aux.getId());
            }
            gestionProductos.delete(id);
            return "productoeliminado";
        } else {
            model.addAttribute("listaProductos", gestionProductos.findAllDisponibles());
            return "productos";
        }
    }


    @GetMapping("/modificando/{id}")
    public String modificarProducto(Model model, @PathVariable long id) {

        Optional<Producto> producto = gestionProductos.findById(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "modificarProducto";
        } else {
            model.addAttribute("listaProductos", gestionProductos.findAllDisponibles());
            return "productos";
        }
    }

    @RequestMapping("/modificado/{id}")
    public String productoModificado(Model model, @PathVariable long id, String nombreProducto, String categoriaProducto, double precioProducto, String descripcionProducto, MultipartFile imageField, boolean removeImage) throws IOException {


        Optional<Producto> producto = gestionProductos.findById(id);
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
            return "producto";
        } else {
            model.addAttribute("listaProductos", gestionProductos.findAllDisponibles());
            return "productos";
        }
    }

    @RequestMapping("/NuevoProducto")
    public String crearProducto(Model model, String nombreProducto, String categoriaProducto, String descripcionProducto, double precioProducto, MultipartFile imageField) throws IOException {

        Usuario vendedor = gestionUsuarios.findById(2).get();
        Producto p = new Producto(nombreProducto, categoriaProducto, descripcionProducto, precioProducto, vendedor);

        if (!imageField.isEmpty()) {
            p.setImagenProducto(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
            p.setHayImagen(true);
        }
        vendedor.getListaProductos().add(p);
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
                // Maintain the same image loading it before updating the book
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
    public String Formulario(Model model) {
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
    public String mostrarListaConversaciones(Model model, @PathVariable long id) {
        Optional<Usuario> aux = gestionUsuarios.findById(id);
        if (aux.isPresent()) {
            List<Conversacion> auxConversaciones = new ArrayList<>();
            auxConversaciones.addAll(aux.get().getListaMisConversacionesE());
            auxConversaciones.addAll(aux.get().getListaMisConversacionesR());
            model.addAttribute("listaConversaciones", auxConversaciones);
            return "ListaConversaciones";
        }
        model.addAttribute("usuario", gestionUsuarios.findById(1).get());
        return "Inicio";
    }


    @RequestMapping("/conversacion/{id}/Enviado/{idEmisor}")
    public String getConversación(Model model, @PathVariable long id, String mensaje, @PathVariable long idEmisor) {


        Optional<Conversacion> conversacion = gestionConversaciones.findById(id);

        if (conversacion.isPresent()) {
            Mensaje nuevoMensaje = new Mensaje(mensaje, LocalDateTime.now(), idEmisor, conversacion.get());
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
                model.addAttribute("producto", p);
                model.addAttribute("idComprador", idEmisor);
                model.addAttribute("conversacionAbierta", conversacion.get());
                model.addAttribute("listaMensajes", conversacion.get().getListaMensajes());
                model.addAttribute("listaConversaciones", auxConversaciones);

                return "Chat";
            }

            model.addAttribute("usuario", gestionUsuarios.findById(1).get());
            return "Inicio";


        } else {
            model.addAttribute("usuario", gestionUsuarios.findById(1).get());
            return "Inicio";
        }
    }

    @RequestMapping("/login")
    public String login(){return "login";}

    @RequestMapping("/loginError")
    public String loginError(){return "loginError";}
}


