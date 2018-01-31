package ru.shaldnikita.starter.web.view.common;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.HasValue;
import com.vaadin.data.StatusChangeEvent;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValidationResult;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.vaadin.teemusa.gridextensions.paging.PagingControls;
import ru.shaldnikita.starter.app.HasLogger;
import ru.shaldnikita.starter.backend.model.AbstractEntity;
import ru.shaldnikita.starter.backend.service.CrudService;
import ru.shaldnikita.starter.web.components.ConfirmDialogWindow;
import ru.shaldnikita.starter.web.components.FilterablePageableDataProvider;
import ru.shaldnikita.starter.web.components.FilterablePagedDataProvider;
import ru.shaldnikita.starter.web.navigation.NavigationManager;

import java.io.Serializable;
import java.util.List;

/**
 * @author n.shaldenkov on 24/01/2018
 */


public abstract class CrudPresenter
        <T extends AbstractEntity,
                S extends CrudService<T>,
                V extends CrudView>
        implements HasLogger,Serializable {

    private NavigationManager navigationManager;

    private FilterablePagedDataProvider<T, S> dataProvider;

    private BeanValidationBinder<T> binder;

    private S service;

    private V view;

    private T selection;


    private Class<T> selectionType;

    private BeanFactory beanFactory;

    private PagingControls pagingControls;


    protected CrudPresenter(NavigationManager navigationManager, S service,
                            Class<T> entityType, BeanFactory beanFactory) {
        this.service = service;
        this.navigationManager = navigationManager;
        this.selectionType = entityType;
        this.beanFactory = beanFactory;

        FilterablePageableDataProvider fpdp = new FilterablePageableDataProvider(service);
        this.dataProvider = new FilterablePagedDataProvider<T, S>(fpdp);
        dataProvider.addSizeInBackendChangeListener((size) -> {
            getView().setTotalRecordsCount(size);
        });
        pagingControls = dataProvider.getPagingControls();

        initBinder();
    }


    public void init(V view) {
        this.view = view;
        getView().getGrid().setDataProvider(dataProvider);
        view.bindFormFields(getBinder());
        view.showInitialState();
    }

    protected void initBinder() {
        binder = new BeanValidationBinder<>(selectionType);
        binder.addStatusChangeListener(this::stateChanged);
    }

    public void stateChanged(StatusChangeEvent event) {
        boolean hasChanges = event.getBinder().hasChanges();
        boolean hasValidationErrors = event.hasValidationErrors();
        getView().setUpdateEnabled(hasChanges && !hasValidationErrors);
        getView().setCancelEnabled(hasChanges);
    }


    private void runWithConfirmation(Runnable onConfirmation, Runnable onCancel) {
        if (hasUnsavedChanges()) {
            ConfirmDialogWindow confirmPopup = beanFactory.getBean(ConfirmDialogWindow.class);
            confirmPopup.showLeaveViewConfirmDialog(view, onConfirmation, onCancel);
        } else {
            onConfirmation.run();
        }
    }

    public void enteringView(ViewChangeListener.ViewChangeEvent event) {
        if (!event.getParameters().isEmpty()) {
            editRequest(event.getParameters());
        }
    }

    public void leavingView(ViewBeforeLeaveEvent event) {
        runWithConfirmation(event::navigate, () -> {
        });
    }

    public void editRequest(String parameters) {
        try {
            long id = Long.parseLong(parameters);
            selectAndEditEntity(loadEntity(id));
        } catch (NumberFormatException e) {
        }
    }

    private void selectAndEditEntity(T entity) {
        if (entity == null)
            return;

        getView().getGrid().select(entity);
        getView().editForm.setVisible(true);
        editRequest(entity);
    }

    public void editRequest(T entity) {
        setSelection(entity);
        runWithConfirmation(() -> {
            T freshEntity = loadEntity(entity.getId());
            editItem(freshEntity);
        }, () -> {
            Grid<T> grid = getView().getGrid();
            if (selection == null) {
                grid.deselectAll();
            } else {
                grid.select(selection);
            }
        });
    }

    protected void editItem(T item) {
        if (item == null) {
            throw new IllegalArgumentException("The entity to edit cannot be null");
        }

        boolean isNew = item.isNew();
        if (isNew) {
            navigationManager.updateViewParameter("new");
        } else {
            navigationManager.updateViewParameter(String.valueOf(item.getId()));
        }

        getBinder().readBean(selection);
        getView().editItem(isNew);
    }

    protected T createEntity() {
        try {
            return getSelectionType().newInstance();
        } catch (Exception e) {
            throw new UnsupportedOperationException(
                    "Entity of type " + getSelectionType().getName() + " is missing a public no-args constructor", e);
        }
    }

    protected T loadEntity(long id) {
        return service.load(id);
    }

    private void deleteEntity(T entity) {
        service.delete(entity.getId());
        dataProvider.refreshAll();
        getView().showInitialState();
    }

    public void addNewClicked() {
        runWithConfirmation(() -> {
            T entity = createEntity();
            setSelection(entity);
            editItem(entity);
        }, () -> {
        });
    }

    public void deleteClicked() {
        deleteEntity(selection);
        revertToInitialState();
    }

    public void saveClicked() {
        try {
            getBinder().validate();
            getBinder().writeBean(selection);
        } catch (ValidationException e) {
            List<BindingValidationStatus<?>> fieldErrors = e.getFieldValidationErrors();
            if (!fieldErrors.isEmpty()) {
                HasValue<?> firstErrorField = fieldErrors.get(0).getField();
                getView().focusField(firstErrorField);
            } else {
                ValidationResult firstError = e.getBeanValidationErrors().get(0);
                Notification.show(firstError.getErrorMessage(), Notification.Type.ERROR_MESSAGE);
            }
            return;
        }

        boolean isNew = selection.isNew();
        T entity;

        try {
            entity = service.save(selection);
        } catch (OptimisticLockingFailureException e) {
            Notification.show("Somebody else might have updated the data. Please refresh and try again.",
                    Notification.Type.ERROR_MESSAGE);
            getLogger().debug("Optimistic locking error while saving entity of type " + selection.getClass().getName(),
                    e);
            return;
        } catch (Exception e) {
            Notification.show("A problem occured while saving the data. Please check the fields.", Notification.Type.ERROR_MESSAGE);
            getLogger().error("Unable to save entity of type " + selection.getClass().getName(), e);
            return;
        }

        if (isNew) {
            dataProvider.refreshAll();
            selectAndEditEntity(entity);
        } else {
            dataProvider.refreshItem(entity);
            editRequest(entity);
        }
    }

    public void cancelClicked() {
        if (selection == null) {
            revertToInitialState();
        } else {
            editItem(selection);
        }
    }

    protected void lastPageClicked() {
        pagingControls.setPageLength(pagingControls.getPageCount() - 1);
    }

    protected void nextPageClicked() {
        pagingControls.nextPage();
    }

    protected void prevPageClicked() {
        pagingControls.previousPage();
    }

    protected void firstPageClicked() {
        pagingControls.setPageNumber(0);
    }

    protected void recordsPerPageValueChanged(HasValue.ValueChangeEvent<Integer> event) {
        pagingControls.setPageLength(event.getValue());
    }

    private void revertToInitialState() {
        selection = null;
        getBinder().readBean(null);
        getView().showInitialState();
        getView().getForm().setVisible(false);
        navigationManager.updateViewParameter("");
    }

    private boolean hasUnsavedChanges() {
        getLogger().info("{}", getBinder().hasChanges());
        return selection != null && getBinder().hasChanges();
    }

    public void setSelection(T selection) {
        this.selection = selection;
    }

    public T getSelection() {
        return selection;
    }

    public S getService() {
        return service;
    }

    public V getView() {
        return view;
    }

    public Class<T> getSelectionType() {
        return selectionType;
    }

    public BeanValidationBinder<T> getBinder() {
        return binder;
    }
}

