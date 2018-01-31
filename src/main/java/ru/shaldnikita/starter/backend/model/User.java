package ru.shaldnikita.starter.backend.model;

import com.vaadin.spring.annotation.SpringComponent;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vaadin.spring.annotation.PrototypeScope;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author n.shaldenkov on 19.11.2017
 */

@Entity
@SpringComponent
@PrototypeScope
public class User extends AbstractEntity {


    @NotNull
    @Column(unique = true)
    private String login;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String role;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> groups = new HashSet<>();

    private boolean locked = false;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public void setGroups(Set<String> groups) {
        this.groups = groups;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                //", groups=" + groups +
                ", locked=" + locked +
                "} " + super.toString();
    }
}
