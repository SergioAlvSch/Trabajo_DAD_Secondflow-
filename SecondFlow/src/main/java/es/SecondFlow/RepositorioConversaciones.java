package es.SecondFlow;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioConversaciones extends JpaRepository<Conversacion, Long> {
}
