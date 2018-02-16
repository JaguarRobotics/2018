package org.usd232.robotics.autonomous.generator.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.swing.AbstractListModel;

public abstract class ListModelBase<TElement, TParentElement, TParent extends ListModelBase<TParentElement, ?, ?>>
                extends AbstractListModel<String> {
    private static final long serialVersionUID = -397189983200261079L;
    protected List<TElement>  list;
    protected TParent         parent;
    protected TParentElement  parentElement;

    protected abstract String toString(TElement obj);

    protected abstract TElement create(String name);

    protected Collection<TElement> load(TParentElement element) {
        return new ArrayList<>();
    }

    protected void onRemove(TElement element) {
    }
    
    protected void onUpdate() {
        parent.onUpdate();
    }

    @Override
    public int getSize() {
        return list.size();
    }

    public TElement getRawElementAt(int index) {
        return list.get(index);
    }

    @Override
    public String getElementAt(int index) {
        return toString(getRawElementAt(index));
    }

    public TElement add(String name) {
        int i = list.size();
        TElement element = create(name);
        list.add(element);
        fireIntervalAdded(this, i, i);
        onUpdate();
        return element;
    }

    public void remove(int i) {
        onRemove(list.remove(i));
        fireIntervalRemoved(this, i, i);
        onUpdate();
    }

    @SuppressWarnings("unchecked")
    public void setParent(ListModelBase<?, ?, ?> parent) {
        this.parent = (TParent) parent;
    }

    public void setParentSelected(Object obj) {
        @SuppressWarnings("unchecked")
        TParentElement parentElement = (TParentElement) obj;
        this.parentElement = parentElement;
        int i = list.size() - 1;
        if (i >= 0) {
            list.clear();
            fireIntervalRemoved(this, 0, i);
        }
        list.addAll(load(parentElement));
        if (!list.isEmpty()) {
            fireIntervalAdded(this, 0, list.size() - 1);
        }
    }

    public ListModelBase() {
        list = new LinkedList<>();
    }
}
