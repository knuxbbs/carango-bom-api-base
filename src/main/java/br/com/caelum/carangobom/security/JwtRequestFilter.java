package br.com.caelum.carangobom.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private JwtManager jwtManager;

  public JwtRequestFilter(JwtManager jwtManager) {
    this.jwtManager = jwtManager;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    var jwt = getToken(request);

    if (jwtManager.isValid(jwt)) {
      authenticate(jwt);
    }

    filterChain.doFilter(request, response);
  }

  private String getToken(HttpServletRequest request) {
    var token = request.getHeader("Authorization");

    if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
      return null;
    }

    return token.substring(7);
  }

  private void authenticate(String token) {
    var user = jwtManager.getUserFromToken(token);
    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

}
