package com.grupo3.sportslife_app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.grupo3.sportslife_app.model.User;
import com.grupo3.sportslife_app.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
    
    @Autowired
    TokenService tokenService;
    
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            var token = this.recoverToken(request);
            
            // Se não há token, apenas continue
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }
            
            var login = tokenService.validateToken(token);

            if (login != null) {
                logger.info("Valid Token for user: {}", login);
                
                // MUDANÇA CRÍTICA: Não carregue o objeto User completo
                // Em vez disso, capture apenas as informações básicas do usuário
                User user = userRepository.findByEmail(login)
                        .orElseThrow(() -> new RuntimeException("User Not Found"));
                
                // Criar uma autenticação simplificada que não contém o objeto user completo
                var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
                
                // UsernamePasswordAuthenticationToken com apenas as informações essenciais
                var authentication = new UsernamePasswordAuthenticationToken(
                    login, // principal - apenas o login como string
                    null,  // credentials - não necessário aqui
                    authorities // authorities simplificadas
                );
                
                // Adicionar detalhes básicos da requisição
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Definir a autenticação no contexto
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                logger.info("user authenticated: {}", login);
            } else if (token != null) {
                logger.warn("Invalid Token");
            }

            // Continuar a cadeia de filtros
            filterChain.doFilter(request, response);
            
        } catch (Exception e) {
            // Log detalhado do erro
            logger.error("Security filter error: " + e.getMessage(), e);
            
            // CRUCIAL: Limpar o contexto de segurança se qualquer exceção ocorrer
            SecurityContextHolder.clearContext();
            
            // Verificar se a resposta já foi enviada antes de tentar modificá-la
            if (!response.isCommitted()) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setContentType("application/json");
                response.getWriter().write("{'error':'Authentication error: " + e.getMessage() + "'}");
            } else {
                logger.warn("Response already sent");
            }
            
            // Continuar com a cadeia de filtros
            filterChain.doFilter(request, response);
        }
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}