package es.SecondFlow.Repositorios;

import es.SecondFlow.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository

import java.util.Optional;

public interface RepositorioUsuarios extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String nombreUsurio);
}
