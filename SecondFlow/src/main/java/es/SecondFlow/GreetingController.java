package es.SecondFlow;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GreetingController {
    List<Producto> listaProductos = new ArrayList<>();

    @GetMapping("/")
    public String PaginaPrincipal(Model model) {
        model.addAttribute("name", "World");
        return "Inicio";
    }

    @GetMapping("/Formulario")
    public String Formulario(Model model) {

        listaProductos.add(new Producto("gorro", "Ropa", "Visera tipica de Los Ángeles", 3.40));
        model.addAttribute("misProductos", listaProductos);
        return "FormularioProducto";
    }

    @RequestMapping("/elFormulario")
    public String crearProducto(Model model, String nombreProducto, String categoriaProducto, String descripcionProducto) {
        listaProductos.add(new Producto(nombreProducto, categoriaProducto, descripcionProducto, 12));
        model.addAttribute("misProductos", listaProductos);
        return "FormularioProducto";
    }

}

