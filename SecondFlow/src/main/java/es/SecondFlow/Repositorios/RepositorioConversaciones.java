package es.SecondFlow.Repositorios;

import es.SecondFlow.Entidades.Conversacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioConversaciones extends JpaRepository<Conversacion, Long> {
}
