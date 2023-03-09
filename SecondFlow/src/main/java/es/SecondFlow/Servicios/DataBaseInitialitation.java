package es.SecondFlow.Servicios;

import es.SecondFlow.Entidades.Usuario;
import es.SecondFlow.Repositorios.RepositorioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
@Service
public class DataBaseInitialitation {

    @Autowired
    private RepositorioUsuarios repositorioUsuarios;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        repositorioUsuarios.save(new Usuario("Martin",passwordEncoder.encode("1234"),"martinnosolofutbol@gmail.com","USER"));
        repositorioUsuarios.save(new Usuario("Edu",passwordEncoder.encode("qwert"),"ev.stancu.2019@alumnos.urjc.es","USER"));
        repositorioUsuarios.save(new Usuario("Sergio",passwordEncoder.encode("sas"),"s.alvarezs.2019@alumnos.urjc.es","USER"));
        repositorioUsuarios.save(new Usuario("Admin",passwordEncoder.encode("Admin"),"SecondFlowApp@gmail.com","USER","ADMIN"));
    }
}
