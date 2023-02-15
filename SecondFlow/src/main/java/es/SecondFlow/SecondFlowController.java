package es.SecondFlow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class SecondFlowController {

    @Autowired
    private GestionProductos gestionProductos;

    @GetMapping("/")
    public String PaginaPrincipal(Model model) {
        mostrarListaProductos(model);
        return "Inicio";
    }

    @GetMapping("/productos")
    public String mostrarListaProductos(Model model) {
        model.addAttribute("listaProductos", gestionProductos.findAll());
        return "productos";
    }


    @GetMapping("/{nombreABuscar}")
    public String showProducto(Model model, @PathVariable String nombreABuscar) {

        Producto p = gestionProductos.findByNombre(nombreABuscar);

        if (p!=null) {
            model.addAttribute("producto",p);
            return "producto";
        } else {
            return "productos";
        }

    }

    @GetMapping("/eliminarproducto/{id}")
    public String eliminarProducto(Model model, @PathVariable long id) {

        Optional<Producto> producto = gestionProductos.findById(id);
        if (producto.isPresent()) {
            gestionProductos.delete(id);
            model.addAttribute("producto", producto.get());
        }
        return "productoeliminado";
    }



    @GetMapping("/Formulario")
    public String Formulario(Model model) {
        return "FormularioProducto";
    }

    @RequestMapping("/elFormulario")
    public String crearProducto(Model model, String nombreProducto, String categoriaProducto, String descripcionProducto,double precioProducto) {

        Producto p = new Producto(nombreProducto, categoriaProducto, descripcionProducto, precioProducto);
        gestionProductos.save(p);

        return "FormularioProducto";
    }
}

