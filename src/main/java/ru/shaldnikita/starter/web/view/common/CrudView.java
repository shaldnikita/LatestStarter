package ru.shaldnikita.starter.web.view.common;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import ru.shaldnikita.starter.app.HasLogger;
import ru.shaldnikita.starter.backend.model.AbstractEntity;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @author n.shaldenkov on 24/01/2018
 */

public abstract class CrudView<T extends AbstractEntity,F extends AbstractLayout> extends CrudViewDesign implements View, HasLogger {

    public static final String CAPTION_DISCARD = "Discard";
    public static final String CAPTION_CANCEL = "Cancel";
    public static final String CAPTION_UPDATE = "Update";
    public static final String CAPTION_SAVE = "Save";


    @PostConstruct
    private void init() {
        getGrid().addSelectionListener(e -> {
            if (!e.isUserOriginated())
                return;

            T selectedItem = e.getFirstSelectedItem().orElse(null);

            if (selectedItem != null) {
                getPresenter().editRequest(selectedItem);
            } else {
                showInitialState();
            }
        });
        recordsPerPage.setDataProvider(DataProvider.ofItems(10, 25, 50, 100, 250, 500, 1000));
        recordsPerPage.setValue(10);
        attachListeners();
    }

    public void showInitialState() {
        editForm.clear();
        editForm.setVisible(false);
        grid.deselectAll();
        getPresenter().setSelection(null);
    }

    public void editItem(boolean isNew) {

        Window window = new Window("test");
        VerticalLayout verticalLayout = new VerticalLayout(editForm.getContent(),new HorizontalLayout(editForm.getSave(),editForm.getDelete(),editForm.getCancel()));
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);
        window.setContent(verticalLayout);
        UI.getCurrent().addWindow(extendToAutosizable(window,window.getContent(),15F, 55F));

        if (isNew) {
            getGrid().deselectAll();
            getCancel().setCaption(CAPTION_CANCEL);
            getSave().setCaption(CAPTION_SAVE);
            getFirstFormField().focus();
        } else {
            getSave().setCaption(CAPTION_UPDATE);
            getCancel().setCaption(CAPTION_DISCARD);
        }

        getDelete().setEnabled(!isNew);
    }

    public Window extendToAutosizable(Window window, Component resizeTarget, float widthMargin, float heightMargin) {
        window.addStyleName("window");
        if (window.getContent() == null) {
            return window;
        }

        if (resizeTarget.getId() == null) {
            resizeTarget.setId("content_of_window_to_be_extended " + new Date().getTime());
        }

        //function for resizing window
        //arg(0) width of resulted window / of component that has undefied size, that fits that window
        //arg(1) height of resulted window / of component that has undefied size, that fits that window
        //arg(2) width margin to avoid h-scrollbar
        //arg(3) height margin to avoid v-scrollbar

        JavaScript.getCurrent().addFunction("adjustSize",
                arguments -> {
                    if (arguments.getNumber(0) > 0.0 && arguments.getNumber(1) > 0.0) {

                        window.setWidth((float) arguments.getNumber(0) + (float) arguments.getNumber(2), Sizeable.Unit.PIXELS);
                        window.setHeight((float) arguments.getNumber(1) + (float) arguments.getNumber(3), Sizeable.Unit.PIXELS);

                    } else {
                        window.setWidth(390f, Sizeable.Unit.PIXELS);
                        window.setHeight(250f, Sizeable.Unit.PIXELS);
                    }

                    window.setModal(true);
                });

        JavaScript.getCurrent().execute("adjustSize(document.getElementById('" + resizeTarget.getId() + "').clientWidth, " +
                "document.getElementById('" + resizeTarget.getId() + "').clientHeight, " + widthMargin + ", " + heightMargin + ");");

        return window;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getPresenter().enteringView(event);
    }

    @Override
    public void beforeLeave(ViewBeforeLeaveEvent event) {
        getPresenter().leavingView(event);
    }

    public void focusField(HasValue<?> field) {
        if (field instanceof Focusable) {
            ((Focusable) field).focus();
        }
    }

    public void bindFormFields(BeanValidationBinder<T> binder) {
        binder.bindInstanceFields(editForm.getContent().getComponent(0));
    }

    private void attachListeners() {
        getSave().addClickListener(event -> getPresenter().saveClicked());
        getCancel().addClickListener(event -> getPresenter().cancelClicked());
        getDelete().addClickListener(event -> getPresenter().deleteClicked());
        add.addClickListener(event -> getPresenter().addNewClicked());
        firstPage.addClickListener(event -> getPresenter().firstPageClicked());
        prevPage.addClickListener(event -> getPresenter().prevPageClicked());
        nextPage.addClickListener(event -> getPresenter().nextPageClicked());
        lastPage.addClickListener(event -> getPresenter().lastPageClicked());
        recordsPerPage.addValueChangeListener(event -> getPresenter().recordsPerPageValueChanged(event));
    }

    public void setTotalRecordsCount(int size) {
        totalRecordsCount.setValue(String.valueOf(size));
    }

    public void setAddNewEnabled(boolean enabled) {
        add.setEnabled(enabled);
    }

    public void setUpdateEnabled(boolean enabled) {
        getSave().setEnabled(enabled);
    }

    public void setCancelEnabled(boolean enabled) {
        getCancel().setEnabled(enabled);
    }

    public void setDeleteEnabled(boolean enabled) {
        getDelete().setEnabled(enabled);
    }

    protected Button getCancel() {
        return editForm.getCancel();
    }

    protected Button getDelete() {
        return editForm.getDelete();
    }

    protected Button getSave() {
        return editForm.getSave();
    }

    protected void setForm(F content) {
        editForm.getContent().removeAllComponents();
        editForm.getContent().addComponent(content);
    }

    protected F getForm(){
        return (F)editForm.getContent().getComponent(0);
    }

    public Button getFilter() {
        return filter;
    }

    protected Grid<T> getGrid() {
        return grid;
    }

    protected abstract CrudPresenter<T, ?, ? extends CrudView<T,F>> getPresenter();

    protected abstract Focusable getFirstFormField();


}
