package es.SecondFlow.Repositorios;

import es.SecondFlow.Entidades.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioMensajes extends JpaRepository<Mensaje, Long> {
}
