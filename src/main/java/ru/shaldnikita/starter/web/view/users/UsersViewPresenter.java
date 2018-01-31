package ru.shaldnikita.starter.web.view.users;

import com.vaadin.spring.annotation.ViewScope;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shaldnikita.starter.backend.model.User;
import ru.shaldnikita.starter.backend.service.UserService;
import ru.shaldnikita.starter.web.navigation.NavigationManager;
import ru.shaldnikita.starter.web.view.common.CrudPresenter;

/**
 * @author n.shaldenkov on 25/01/2018
 */

@Component
@ViewScope
public class UsersViewPresenter extends CrudPresenter<User, UserService, UsersView> {

    @Autowired
    public UsersViewPresenter(NavigationManager navigationManager, UserService service, BeanFactory beanFactory) {
        super(navigationManager, service, User.class, beanFactory);
    }

    @Override
    protected void editItem(User item) {
        getView().setPasswordRequired(item.isNew());
        super.editItem(item);
    }
}
