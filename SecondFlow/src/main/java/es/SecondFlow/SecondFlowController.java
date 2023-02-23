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
        mostrarListaProductos(model);
        return "Inicio";
    }

    @GetMapping("/Perfil")
    public String mostrarPerfil(Model model, @PathVariable long id) {
        model.addAttribute("miPerfil", usuario);

        Optional<Usuario> u = gestionUsuarios.findById(id);
        if (u.isPresent()) {
            model.addAttribute("producto", u);
            return "producto";
        } else {
            return "productos";
        }
    }

    @GetMapping("/buscar")
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

    @GetMapping("/modificado/{id}")
    public String modificarProducto(Model model, @PathVariable long id, String nombreProducto, String categoriaProducto, double precioProducto, String descripcionProducto) {


        Optional<Producto> producto = gestionProductos.findById(id);
        if (producto.isPresent()) {
            producto.get().setNombre(nombreProducto);
            producto.get().setCategoria(categoriaProducto);
            producto.get().setPrecio(precioProducto);
            producto.get().setDescripcion(descripcionProducto);
            gestionProductos.update(producto.get());
            model.addAttribute("producto", producto.get());
            return "producto";
        } else {
            return "productos";
        }
    }

    @RequestMapping("/NuevoProducto")
    public String crearProducto(Model model, String nombreProducto, String categoriaProducto, String descripcionProducto, double precioProducto) {
        Producto p = new Producto(nombreProducto, categoriaProducto, descripcionProducto, precioProducto);
        model.addAttribute("producto", p);
        gestionProductos.save(p);
        return "producto";
    }

    @PostMapping("/subirImagen")
    public ResponseEntity<Object> subirImagen(@RequestParam MultipartFile imagen, String nombre) throws IOException {

        Producto p = gestionProductos.findById(gestionProductos.getIDbyNombre(nombre)).orElseThrow();
        URI localizacion = fromCurrentRequest().build().toUri();
        p.setImagenProducto(BlobProxy.generateProxy(imagen.getInputStream(), imagen.getSize()));
        gestionProductos.save(p);
        return ResponseEntity.created(localizacion).build();
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

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "jpeg")
                    .contentLength(producto.get().getImagenProducto().length()).body(file);

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


