package es.SecondFlow.Servicios;

import es.SecondFlow.Entidades.Usuario;
import es.SecondFlow.Repositorios.RepositorioProductos;
import es.SecondFlow.Repositorios.RepositorioUsuarios;
import es.SecondFlow.Entidades.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
@Service
public class DataBaseInitialitation {

    @Autowired
    private RepositorioUsuarios repositorioUsuarios;

    @Autowired
    private RepositorioProductos repositorioProductos;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        if(!repositorioUsuarios.findByNombreUsuario("Martin").isPresent()) {
            Usuario martin = new Usuario("Martin", passwordEncoder.encode("1234"), "martinnosolofutbol@gmail.com", "USER");
            Usuario sergio = new Usuario("Sergio", passwordEncoder.encode("sas"), "s.alvarezs.2019@alumnos.urjc.es", "USER");
            Usuario edu = new Usuario("Edu", passwordEncoder.encode("qwert"), "ev.stancu.2019@alumnos.urjc.es", "USER");

            repositorioUsuarios.save(martin);
            repositorioUsuarios.save(sergio);
            repositorioUsuarios.save(edu);
            repositorioUsuarios.save(new Usuario("Admin", passwordEncoder.encode("Admin"), "SecondFlowApp@gmail.com", "USER", "ADMIN"));

            repositorioProductos.save(new Producto("Balon", "Deporte", "Der betis", 32.05, martin));
            repositorioProductos.save(new Producto("Sombrero", "Deporte", "The one piece", 1132.05, martin));
            repositorioProductos.save(new Producto("Televisor", "Electronica", "Esta roto jeje", 320.05, edu));
            repositorioProductos.save(new Producto("Lampara", "Hogar", "Es muy acogedora", 5, edu));
            repositorioProductos.save(new Producto("Falda", "Deporte", "Muy comoda", 15, sergio));
            repositorioProductos.save(new Producto("10 en el examen", "Hogar", "Es un buen programa, profe creame", 32.05, sergio));
            repositorioProductos.save(new Producto("Zapato", "Ropa", "Calzado comodo", 32.05, martin));
        }
    }
}
