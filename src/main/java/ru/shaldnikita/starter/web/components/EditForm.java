package ru.shaldnikita.starter.web.components;

import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author n.shaldenkov on 24/01/2018
 */


public class EditForm extends EditFormDesign {

    protected List<AbstractField> fields = new ArrayList<>();

    public void clear() {
        fields.forEach(f -> f.clear());
    }

    public VerticalLayout getContent() {
        return content;
    }

    public Button getSave() {
        return save;
    }

    public Button getDelete() {
        return delete;
    }

    public Button getCancel() {
        return cancel;
    }

    public void setContent(VerticalLayout content) {
        this.content = content;
        content.setHeight("100%");
    }
}
