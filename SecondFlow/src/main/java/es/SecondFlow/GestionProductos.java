package es.SecondFlow;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GestionProductos {

    @Autowired
    private RepositorioProductos repositorio;

    public Optional<Producto> findById(long id) {
        return repositorio.findById(id);
    }

    public Producto findByNombre(String nombre) {
        for (Producto aux: repositorio.findAll()){
            if (aux.getNombre().toLowerCase().equals(nombre.toLowerCase())){
                return aux;
            }
        }
        return null;
    }
    public long getIDbyNombre(String nombre){
        return this.findByNombre(nombre).getId();
    }

    public void update(Producto Producto){repositorio.flush();}

    public boolean exist(long id) {
        return repositorio.existsById(id);
    }

    public List<Producto> findAll() {
        return repositorio.findAll();
    }

    public void save(Producto producto) {
        repositorio.saveAndFlush(producto);
    }

    public void delete(long id) {
        repositorio.deleteById(id);
    }
}
