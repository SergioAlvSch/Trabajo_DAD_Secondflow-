package es.SecondFlow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class DataBaseUsage implements CommandLineRunner {
    @Autowired
    private ProductoRepositorio repositorio;

    @Override
    public void run(String... args) throws Exception {
        repositorio.save(new Producto("Tacos Futbol 11", "Calzado", "Par de zapatillas del 39"));
        repositorio.save(new Producto("Filosofia 2 bachillerato", "Libros", "Libro para estudiantes de instituto"));
        repositorio.save(new Producto("Tacones Channel", "Calzado", "Par de tacones para bodas"));

        List<Producto> zapatos = repositorio.findByCategoria("Calzado");
        for (Producto aux : zapatos) {
            System.out.println(aux);
        }

    }
}
