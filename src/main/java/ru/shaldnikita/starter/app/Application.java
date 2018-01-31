package ru.shaldnikita.starter.app;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.vaadin.spring.events.annotation.EnableEventBus;
import ru.shaldnikita.starter.app.security.SecurityConfig;
import ru.shaldnikita.starter.backend.model.User;
import ru.shaldnikita.starter.backend.model.dict.Role;
import ru.shaldnikita.starter.backend.repositories.TerminalRepository;
import ru.shaldnikita.starter.backend.repositories.UserRepository;
import ru.shaldnikita.starter.backend.service.UserService;
import ru.shaldnikita.starter.web.VaadinUI;

/**
 * @author n.shaldenkov on 23.11.2017
 */

@SpringBootApplication(scanBasePackageClasses = {VaadinUI.class, Application.class,
        SecurityConfig.class,
        UserService.class, User.class})
@EntityScan(basePackageClasses = User.class)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EnableEventBus
public class Application extends SpringBootServletInitializer {
    public static final String APP_URL = "/";
    public static final String LOGIN_URL = "/login.html";
    public static final String LOGOUT_URL = "/login.html?logout";
    public static final String LOGIN_FAILURE_URL = "/login.html?error";
    public static final String LOGIN_PROCESSING_URL = "/login";

    @Autowired
    UserService userService;

    @Autowired
    TerminalRepository terminalRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BeanFactory beanFactory;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return (args) -> {
            User user = beanFactory.getBean(User.class);
            user.setPassword("123");
            user.setRole(Role.ADMIN);
            user.setFirstName("nikita");
            user.setLastName("shaldenkov");
            user.setLogin("admin");
            user.setEmail("admin@admin.ru");
            userService.save(user);

            user = beanFactory.getBean(User.class);
            user.setPassword("operator1");
            user.setRole(Role.OPERATOR);
            user.setFirstName("operator");
            user.setLastName("operatorov");
            user.setLogin("operator");
            user.setEmail("operator@operator.ru");
            userService.save(user);

        };
    }
}
