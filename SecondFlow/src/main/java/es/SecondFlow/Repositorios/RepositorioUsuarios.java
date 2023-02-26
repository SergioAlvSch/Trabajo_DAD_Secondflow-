package es.SecondFlow.Repositorios;

import es.SecondFlow.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioUsuarios extends JpaRepository<Usuario, Long> {
}
