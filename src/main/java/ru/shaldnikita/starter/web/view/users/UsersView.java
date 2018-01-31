package ru.shaldnikita.starter.web.view.users;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import ru.shaldnikita.starter.backend.model.User;
import ru.shaldnikita.starter.backend.model.dict.Role;
import ru.shaldnikita.starter.web.forms.UserEditForm;
import ru.shaldnikita.starter.web.view.common.CrudPresenter;
import ru.shaldnikita.starter.web.view.common.CrudView;

import javax.annotation.PostConstruct;

/**
 * @author n.shaldenkov on 25/01/2018
 */


@SpringView
@Secured(Role.ADMIN)
public class UsersView extends CrudView<User, UserEditForm>{

    @Autowired
    private UsersViewPresenter usersViewPresenter;

    @Autowired
    private UserEditForm userEditForm;

    private boolean passwordRequired;

    private Validator<String> passwordValidator = new Validator<String>() {

        BeanValidator passwordBeanValidator = new BeanValidator(User.class, "password");

        @Override
        public ValidationResult apply(String value, ValueContext context) {
            if (!passwordRequired && value.isEmpty()) {
                return ValidationResult.ok();
            } else {
                return passwordBeanValidator.apply(value, context);
            }
        }
    };


    @PostConstruct
    private void init() {
        setForm(userEditForm);
        usersViewPresenter.init(this);
        getGrid().addColumn(User::getLogin).setCaption("Login");
        getGrid().addColumn(User::getEmail).setCaption("Email");

    }

    @Override
    public void bindFormFields(BeanValidationBinder<User> binder) {
        binder.forField(getForm().getPassword()).withValidator(passwordValidator).bind(bean -> "",
                (bean, value) -> {
                    if (value.isEmpty()) {
                    } else {
                        bean.setPassword(value);
                    }
                });
        super.bindFormFields(binder);
    }

    public void setPasswordRequired(boolean passwordRequired) {
        this.passwordRequired = passwordRequired;
        getForm().getPassword().setRequiredIndicatorVisible(passwordRequired);
    }

    @Override
    protected CrudPresenter<User, ?, ? extends CrudView<User, UserEditForm>> getPresenter() {
        return usersViewPresenter;
    }

    @Override
    protected Focusable getFirstFormField() {
        return (Focusable) userEditForm.getComponent(0);
    }


}
