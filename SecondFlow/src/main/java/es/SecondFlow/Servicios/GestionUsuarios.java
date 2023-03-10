package es.SecondFlow.Servicios;

import es.SecondFlow.Repositorios.RepositorioUsuarios;
import es.SecondFlow.Entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GestionUsuarios {
    @Autowired
    private RepositorioUsuarios repositorioUsuarios;

    public Optional<Usuario> findById(long id) {
        return repositorioUsuarios.findById(id);
    }

    public Usuario findByNombre(String nombre) {
        for (Usuario aux: repositorioUsuarios.findAll()){
            if (aux.getNombreUsuario().toLowerCase().equals(nombre.toLowerCase())){
                return aux;
            }
        }
        return null;
    }

    public void update(){repositorioUsuarios.flush();}

    public boolean exist(long id) {
        return repositorioUsuarios.existsById(id);
    }

    public List<Usuario> findAll() {
        return repositorioUsuarios.findAll();
    }

    public void save(Usuario usuario) {
        repositorioUsuarios.saveAndFlush(usuario);
    }

    public void delete(long id) {
        repositorioUsuarios.deleteById(id);
    }
}
