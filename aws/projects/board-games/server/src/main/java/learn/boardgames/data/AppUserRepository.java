package learn.boardgames.data;

import learn.boardgames.models.AppUser;
import org.springframework.transaction.annotation.Transactional;

public interface AppUserRepository {

    @Transactional
    AppUser findByUsername(String username);

    @Transactional
    AppUser create(AppUser user);

    @Transactional
    boolean update(AppUser user);
}
