package es.SecondFlow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioProductos extends JpaRepository<Producto, Long> {

}
