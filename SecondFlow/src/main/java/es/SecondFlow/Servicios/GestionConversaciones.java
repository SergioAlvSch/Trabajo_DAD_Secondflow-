package es.SecondFlow.Servicios;

import es.SecondFlow.Entidades.Conversacion;
import es.SecondFlow.Entidades.Producto;
import es.SecondFlow.Repositorios.RepositorioConversaciones;
import es.SecondFlow.Entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GestionConversaciones {

    @Autowired
    private RepositorioConversaciones repositorio;

    public Optional<Conversacion> findById(long id) {
        return repositorio.findById(id);
    }

    public Conversacion findByUsuarios(Usuario usuario1, Usuario usuario2, Producto producto) {
        for (Conversacion aux: repositorio.findAll()){
            if ((aux.getEmisor().equals(usuario1)&&aux.getReceptor().equals(usuario2)&&aux.getProducto()==producto)){
                return aux;
            }
        }
        return null;
    }

    public List<Conversacion> findByProducto(Producto producto) {
        List<Conversacion> auxConversaciones=new ArrayList<>();
        for (Conversacion aux: repositorio.findAll()){
            if ((aux.getProducto()==producto)){
                auxConversaciones.add(aux);
            }
        }
        return auxConversaciones;
    }

    public void update(){repositorio.flush();}

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
