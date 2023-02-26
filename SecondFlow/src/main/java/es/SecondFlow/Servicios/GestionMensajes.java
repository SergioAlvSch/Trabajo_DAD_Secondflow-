package es.SecondFlow.Servicios;

import es.SecondFlow.Entidades.Mensaje;
import es.SecondFlow.Repositorios.RepositorioMensajes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class GestionMensajes {

    @Autowired
    private RepositorioMensajes repositorio;

    public Optional<Mensaje> findById(long id) {
        return repositorio.findById(id);
    }

    public void save(Mensaje mensaje) {
        repositorio.saveAndFlush(mensaje);
    }
    public void update(){repositorio.flush();}
    public List<Mensaje> findAll() {
        return repositorio.findAll();
    }
}
