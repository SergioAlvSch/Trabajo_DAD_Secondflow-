package es.SecondFlow.Seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RepositoryUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Public pages
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/loginerror").permitAll();
        http.authorizeRequests().antMatchers("/logout").permitAll();
        http.authorizeRequests().antMatchers("/productos").permitAll();
        http.authorizeRequests().antMatchers("/productos/*").permitAll();
        http.authorizeRequests().antMatchers("/buscar").permitAll();
        http.authorizeRequests().antMatchers("/Perfil/*").permitAll();
        http.authorizeRequests().antMatchers("/producto/*").permitAll();

        // Private pages
        http.authorizeRequests().antMatchers("/Formulario").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/NuevoProducto").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/conversaciones/*").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/conversacion/*").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/modificando/*").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/modificado/*").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/producto/vendido/*").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/comprar/producto/*").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/eliminarProducto/*").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/productos/Comprados/*").hasAnyRole("USER");

        // Login form
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/");
        http.formLogin().failureUrl("/loginerror");

        // Logout
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");
    }
}
