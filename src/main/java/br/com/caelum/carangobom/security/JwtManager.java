package br.com.caelum.carangobom.security;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtManager {

  private String issuer;
  private String secret;
  private long expiration;

  public JwtManager(@Value("${jwt.issuer}") String issuer, @Value("${jwt.secret}") String secret,
      @Value("${jwt.expiration}") long expiration) {
    this.issuer = issuer;
    this.secret = secret;
    this.expiration = expiration;
  }

  public String generateToken(User user) {
    var date = new Date();
    var expirationDate = new Date(date.getTime() + expiration);

    return Jwts.builder().setIssuer(issuer).setSubject(user.getEmail()).setIssuedAt(date)
        .setExpiration(expirationDate).signWith(SignatureAlgorithm.HS256, secret).compact();
  }

  public boolean isValid(String token) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public User getUserFromToken(String token) {
    var claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

    var user = new User();
    user.setEmail(claims.getSubject());

    return user;
  }

}
