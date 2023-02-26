package es.SecondFlow.Repositorios;
import es.SecondFlow.Entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioProductos extends JpaRepository<Producto, Long> {
}
