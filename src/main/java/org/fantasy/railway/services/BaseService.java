package org.fantasy.railway.services;

import org.fantasy.railway.model.Identified;

import java.util.Comparator;
import java.util.List;

/**
 * Extending this class signifies that the service manages Identified objects
 * @param <T> the Identified object that is being managed
 */
@SuppressWarnings("java:S1610")
public abstract class BaseService<T extends Identified> {

    abstract List<T> getItems();

    Integer nextId() {
        return getItems().stream()
                .max(Comparator.comparing(T::getId))
                .map(T::getId)
                .orElse(0) + 1;
    }

    /**
     * @param id the id of the item to get
     * @return the item with the given id
     */
    public T getById(Integer id) {
        return getItems().stream()
                .filter(item -> id.equals(item.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("No item with id %s", id)));
    }

}
