package com.cafepos.menu;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class CompositeIterator implements Iterator<MenuComponent> {
    private final Deque<Iterator<MenuComponent>> stack = new
            ArrayDeque<>();
    public CompositeIterator(Iterator<MenuComponent> root) {
        stack.push(root); }
    @Override public boolean hasNext() {
        while (!stack.isEmpty()) {
            if (stack.peek().hasNext()) return true;
            stack.pop();
        }
        return false;
    }
    @Override public MenuComponent next() {
        if (!hasNext()) throw new NoSuchElementException();
        Iterator<MenuComponent> it = stack.peek();
        MenuComponent comp = it.next();
        if (comp instanceof Menu m) {
            stack.push(m.childrenIterator());
        }
        return comp;
    }

}
