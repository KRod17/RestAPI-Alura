package com.jose.restapialura.infra.security;

import com.jose.restapialura.domain.usuarios.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository repository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Obtener el token del header
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null){
            var token = authHeader.replace("Bearer ", "");
            var nombreUsuario = tokenService.getSubject(token);
            if (nombreUsuario != null){
                // Token válido
                var usuario = repository.findByLogin(nombreUsuario);
                var authentication = new UsernamePasswordAuthenticationToken(
                        usuario,
                        null,
                        usuario.getAuthorities()); // forzamos el inicio de sesión
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response); //Filtro debe pasar la llamada al siguiente filtro
    }
}
