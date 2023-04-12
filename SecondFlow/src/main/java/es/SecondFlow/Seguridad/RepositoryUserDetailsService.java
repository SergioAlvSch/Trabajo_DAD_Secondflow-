package es.SecondFlow.Seguridad;

import es.SecondFlow.Entidades.Usuario;
import es.SecondFlow.Repositorios.RepositorioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RepositoryUserDetailsService implements UserDetailsService {

    @Autowired
    private RepositorioUsuarios userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> user = userRepository.findByNombreUsuario(username);

        List<GrantedAuthority> roles = new ArrayList<>();

            for (String role : user.get().getRoles()) {
            roles.add(new SimpleGrantedAuthority("ROLE_" + role));
            }


        return new org.springframework.security.core.userdetails.User(user.get().getNombreUsuario(),
                user.get().getClave(), roles);
    }
}
