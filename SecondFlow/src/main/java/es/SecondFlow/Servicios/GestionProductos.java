package es.SecondFlow.Servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import es.SecondFlow.Entidades.Producto;
import es.SecondFlow.Repositorios.RepositorioProductos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@CacheConfig(cacheNames = "productos")
public class GestionProductos {

    @Autowired
    private RepositorioProductos repositorio;
    @Cacheable
    public Optional<Producto> findById(long id) {
        return repositorio.findById(id);
    }
    @Cacheable
    public List<Producto> findByNombre(String nombre) {
        List<Producto> productosConNombre=new ArrayList<>();
        for (Producto aux: repositorio.findAll()){
            if (aux.getNombre().toLowerCase().contains(nombre.toLowerCase())){
                productosConNombre.add(aux);
            }
        }
        return productosConNombre;
    }
    @CacheEvict(allEntries = true)
    @Transactional
    public void update(){repositorio.flush();}
    @Cacheable
    public boolean exist(long id) {
        return repositorio.existsById(id);
    }
    @Cacheable
    public List<Producto> findAll() {
        return repositorio.findAll();
    }
    @CacheEvict(allEntries = true)
    public void save(Producto producto) {
        repositorio.saveAndFlush(producto);
    }
    @CacheEvict(allEntries = true)
    public void delete(Producto p) {
        repositorio.delete(p);

    }
    @Cacheable
    public List<Producto> findAllDisponibles() {
        List<Producto> listaDisponibles = new ArrayList<>();
        for(Producto aux : repositorio.findAll()){
            if(aux.getComprador()==null){
                listaDisponibles.add(aux);
            }
        }
        return listaDisponibles;
    }

    public RepositorioProductos getRepositorio() {
        return repositorio;
    }
}
