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
    Usuario usuario;

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
        model.addAttribute("listaProductos", gestionProductos.findAll());
        return "productos";
    }


    @GetMapping("/productos/{id}")
    public String getProducto(Model model, @PathVariable long id) {

        Optional<Producto> p = gestionProductos.findById(id);

        if (p.isPresent()) {
            model.addAttribute("producto", p.get());
            return "producto";
        } else {
            model.addAttribute("listaProductos", gestionProductos.findAll());
            return "productos";
        }
    }
    @GetMapping("/comprar/producto/{id}")
    public String comprarProducto(Model model, @PathVariable long id) {

        Optional<Producto> p = gestionProductos.findById(id);

        if (p.isPresent()) {
            model.addAttribute("producto", p.get());
            return "//////";
        } else {
            model.addAttribute("listaProductos", gestionProductos.findAll());
            return "productos";
        }
    }

    @GetMapping("/eliminarProducto/{id}")
    public String eliminarProducto(Model model, @PathVariable long id) {

        Optional<Producto> producto = gestionProductos.findById(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            gestionProductos.delete(id);
            return "productoeliminado";
        } else {
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


    @GetMapping("/conversaciones/{id}")
    public String getConversación(Model model, @PathVariable long id) {

        Optional<Producto> p = gestionProductos.findById(id);

        if (p.isPresent()) {
            model.addAttribute("producto", p.get());
            return "producto";
        } else {
            model.addAttribute("listaProductos", gestionProductos.findAll());
            return "productos";
        }
    }
}


