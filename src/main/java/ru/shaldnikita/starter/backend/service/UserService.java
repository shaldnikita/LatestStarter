package ru.shaldnikita.starter.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shaldnikita.starter.backend.model.User;
import ru.shaldnikita.starter.backend.repositories.UserRepository;

@Service
public class UserService extends CrudService<User> {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return getRepository().findByEmail(email);
    }

    public User findByLogin(String login) {
        return getRepository().findByLogin(login);
    }

    protected UserRepository getRepository() {
        return userRepository;
    }

}
