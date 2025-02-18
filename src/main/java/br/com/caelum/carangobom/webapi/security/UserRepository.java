package br.com.caelum.carangobom.webapi.security;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {

  Optional<User> findByEmail(String email);

}
