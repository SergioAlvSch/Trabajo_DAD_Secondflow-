package es.SecondFlow;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioMensajes extends JpaRepository<Mensaje, Long> {
}
