package ru.shaldnikita.starter.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shaldnikita.starter.backend.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByLogin(String login);
}
