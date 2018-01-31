package ru.shaldnikita.starter.web.forms;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shaldnikita.starter.backend.model.dict.Role;
import ru.shaldnikita.starter.backend.repositories.TerminalRepository;

import javax.annotation.PostConstruct;

/**
 * @author n.shaldenkov on 25/01/2018
 */

@SpringComponent
@ViewScope
public class UserEditForm extends UserEditFormDesign {

    @Autowired
    TerminalRepository terminalRepository;

    @PostConstruct
    private void init(){
        role.setDataProvider(DataProvider.ofItems(Role.getAllRoles()));
        //grups.setDataProvider(DataProvider.ofCollection(terminalRepository.findAllGroupNames()));
    }

    public TextField getFirstName() {
        return firstName;
    }

    public TextField getLastName() {
        return lastName;
    }

    public TextField getLogin() {
        return login;
    }

    public TextField getPassword() {
        return password;
    }

    public TextField getEmail() {
        return email;
    }

    public ComboBox<String> getRole() {
        return role;
    }

    public TwinColSelect<String> getGroups() {
        return groups;
    }
}
