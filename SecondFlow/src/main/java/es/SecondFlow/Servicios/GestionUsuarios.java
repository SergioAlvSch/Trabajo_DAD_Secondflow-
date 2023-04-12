package es.SecondFlow.Servicios;

import es.SecondFlow.Repositorios.RepositorioUsuarios;
import es.SecondFlow.Entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "usuarios")
public class GestionUsuarios {

    @Autowired
    private RepositorioUsuarios repositorioUsuarios;

    public Optional<Usuario> findById(long id) {
        return repositorioUsuarios.findById(id);
    }
    @Cacheable
    public Usuario findByNombre(String nombreUsuario) {
        for (Usuario aux: repositorioUsuarios.findAll()){
            if (aux.getNombreUsuario().toLowerCase().equals(nombreUsuario.toLowerCase())){
                return aux;
            }
        }
        return null;
    }
    @CacheEvict(allEntries = true)
    @Transactional
    public void update(){repositorioUsuarios.flush();}
    @Cacheable
    public boolean exist(long id) {
        return repositorioUsuarios.existsById(id);
    }
    @Cacheable
    public List<Usuario> findAll() {
        return repositorioUsuarios.findAll();
    }
    @CacheEvict(allEntries = true)
    public void save(Usuario usuario) {
        repositorioUsuarios.saveAndFlush(usuario);
    }
    @CacheEvict(allEntries = true)
    public void delete(long id) {
        repositorioUsuarios.deleteById(id);
    }
}
