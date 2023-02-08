package es.SecondFlow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(String categoria);

    List<Producto> findByNombre(String nombre);

    List<Producto> findByDescripcion(String descripcion);

}
