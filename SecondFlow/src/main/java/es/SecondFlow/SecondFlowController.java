package es.SecondFlow;

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
import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Controller
public class SecondFlowController {

    @Autowired
    private GestionProductos gestionProductos;

    @Autowired
    private GestionUsuarios gestionUsuarios;
    
    @Autowired
    private GestionConversaciones gestionConversaciones;

    @Autowired
    Usuario usuarioComprador;

    @Autowired
    Usuario usarioVendedor;

    @GetMapping("/")
    public String mostrarPaginaPrincipal(Model model) {

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
            return "inicio";
        }
    }

    @GetMapping("/buscar/{nombre}")
    public String busqueda(Model model, @PathVariable String nombre) {
        model.addAttribute("misProductos", gestionProductos.findByNombre(nombre));
        return "Inicio";
    }


    @GetMapping("/productos")
    public String mostrarListaProductos(Model model) {
        model.addAttribute("listaProductos", gestionProductos.findAllDisponibles());
        return "productos";
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
            Usuario vendedor=p.get().getVendedor();
            Optional<Usuario> comprador =gestionUsuarios.findById(1);
            model.addAttribute("producto", p.get());
            Conversacion c = gestionConversaciones.findByUsuarios(comprador.get(),vendedor,p.get());
            if(c==null){
                c= new Conversacion(comprador.get(),vendedor,p.get());
                gestionConversaciones.save(c);
                comprador.get().listaMisConversacionesE.add(c);
                vendedor.listaMisConversacionesR.add(c);
                gestionUsuarios.update();
            }
            List<Conversacion>auxConversaciones=new ArrayList<>();
            auxConversaciones.addAll(comprador.get().listaMisConversacionesE);
            auxConversaciones.addAll(comprador.get().listaMisConversacionesR);
            model.addAttribute("listaConversaciones", auxConversaciones);
            model.addAttribute("conversacionAbierta", c);
            model.addAttribute("idComprador",comprador.get().getId());
            return "Chat";
        } else {
            model.addAttribute("listaProductos", gestionProductos.findAllDisponibles());
            return "productos";
        }
    }

    @RequestMapping("/producto/vendido/{id}/{idComprador}")
    public String productoVendido(Model model,@PathVariable long id,@PathVariable long idComprador){
        Optional<Producto> p = gestionProductos.findById(id);
        if (p.isPresent()){
            Usuario vendedor= p.get().getVendedor();
            Optional<Usuario> comprador =gestionUsuarios.findById(idComprador);
            if (comprador.isPresent()){
                comprador.get().listaMisProductosComprados.add(p.get());
                p.get().setHayComprador();
                vendedor.listaMisProductos.remove(p.get());
                p.get().setComprador(comprador.get());
                gestionProductos.delete(p.get().getId());
                gestionUsuarios.update();
                gestionProductos.update();
                model.addAttribute("producto", p.get());
                return "productoVendido";
            }
        }
        return "Inicio";
    }

    @GetMapping("/eliminarProducto/{id}")
    public String eliminarProducto(Model model, @PathVariable long id) {

        Optional<Producto> producto = gestionProductos.findById(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
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
            Usuario vendedor = producto.get().getVendedor();

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
        vendedor.listaMisProductos.add(p);
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

    @GetMapping("/conversaciones")
    public String mostrarListaConversaciones(Model model) {
        model.addAttribute("listaConversaciones", gestionProductos.findAll());
        return "conversaciones";
    }


    @RequestMapping("/conversaciones/{id}/Enviado/{idEmisor}")
    public String getConversación(Model model, @PathVariable long id,String mensaje,@PathVariable long idEmisor) {


        Optional<Producto> p = gestionProductos.findById(id);

        if (p.isPresent()) {
            Usuario vendedor=p.get().getVendedor();
            Optional<Usuario> comprador =gestionUsuarios.findById(1);
            model.addAttribute("producto", p.get());

            Conversacion c = gestionConversaciones.findByUsuarios(comprador.get(),vendedor,p.get());

            Mensaje nuevoMensaje=new Mensaje(mensaje, LocalDateTime.now(),idEmisor);
            model.addAttribute("conversacion", nuevoMensaje);
            return "conversacion";
        } else {
            model.addAttribute("listaConversaciones", gestionProductos.findAllDisponibles());
            return "conversacion";
        }
    }
}


