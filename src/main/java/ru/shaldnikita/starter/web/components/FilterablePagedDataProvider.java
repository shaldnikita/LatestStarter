package ru.shaldnikita.starter.web.components;

import org.vaadin.teemusa.gridextensions.paging.PagedDataProvider;
import ru.shaldnikita.starter.backend.model.AbstractEntity;
import ru.shaldnikita.starter.backend.service.CrudService;

/**
 * @author n.shaldenkov on 25/01/2018
 */


public class FilterablePagedDataProvider<T extends AbstractEntity, S extends CrudService<T>> extends PagedDataProvider<T, Object> {

    private FilterablePageableDataProvider<T, S> dataProvider;


    public FilterablePagedDataProvider(FilterablePageableDataProvider<T, S> dataProvider) {
        super(dataProvider);
        this.dataProvider = dataProvider;
    }

    public void addSizeInBackendChangeListener(FilterablePageableDataProvider.SizeInBackendChangeListener listener) {
        dataProvider.sizeInBackendChangeListeners.add(listener);
    }

    public void removeSizeInBackendChangeListener(FilterablePageableDataProvider.SizeInBackendChangeListener listener) {
        dataProvider.sizeInBackendChangeListeners.remove(listener);
    }

    public T getFilter() {
        return dataProvider.getFilter();
    }

    public void setFilter(T filter) {
        dataProvider.setFilter(filter);
    }
}
