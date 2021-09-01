package br.com.caelum.carangobom.seguranca;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface AppUserRepository extends Repository<AppUser, Long> {
  Optional<AppUser> findByEmail(String email);
}
