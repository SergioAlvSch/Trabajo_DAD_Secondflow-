package es.SecondFlow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GestionConversaciones {

    @Autowired
    private RepositorioConversaciones repositorio;

    public Optional<Conversacion> findById(long id) {
        return repositorio.findById(id);
    }

    public void update(){repositorio.flush();}

    public boolean exist(long id) {
        return repositorio.existsById(id);
    }

    public List<Conversacion> findAll() {
        return repositorio.findAll();
    }

    public void save(Conversacion conversacion) {
        repositorio.saveAndFlush(conversacion);
    }

    public void delete(long id) {
        repositorio.deleteById(id);
    }

}
