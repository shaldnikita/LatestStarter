package ru.shaldnikita.starter.web.components;

import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.PageableDataProvider;
import ru.shaldnikita.starter.backend.model.AbstractEntity;
import ru.shaldnikita.starter.backend.service.CrudService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author n.shaldenkov on 25/01/2018
 */


public class FilterablePageableDataProvider<T extends AbstractEntity, S extends CrudService<T>> extends PageableDataProvider<T, Object> {

    private T filter;

    private S service;

    private int sizeInBackend;

    protected List<SizeInBackendChangeListener> sizeInBackendChangeListeners;

    public FilterablePageableDataProvider(S service) {
        this.service = service;
        sizeInBackendChangeListeners = new ArrayList();
    }

    @Override
    protected Page<T> fetchFromBackEnd(Query<T, Object> query, Pageable pageable) {
        return service.findAnyMatching(filter, pageable);
    }

    @Override
    protected List<QuerySortOrder> getDefaultSortOrders() {
        return QuerySortOrder.desc("id").build();
    }

    @Override
    protected int sizeInBackEnd(Query<T, Object> query) {
        sizeInBackend = (int) service.countAnyMatching(filter);

        for (SizeInBackendChangeListener listener : sizeInBackendChangeListeners) {
            listener.onSizeInBackendChange(sizeInBackend);
        }
        return sizeInBackend;
    }


    public T getFilter() {
        return filter;
    }

    public void setFilter(T filter) {
        this.filter = filter;
    }

    public int getSizeInBackend() {
        return sizeInBackend;
    }

    public interface SizeInBackendChangeListener {
        void onSizeInBackendChange(int newSize);
    }
}

