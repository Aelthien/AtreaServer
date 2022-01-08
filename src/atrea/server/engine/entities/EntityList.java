package atrea.server.game.entity;

import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class EntityList<E extends Entity> implements Iterable<E> {

    private E[] entities;
    private final Queue<Integer> queue = new ArrayDeque<>();
    private @Getter final int capacity;
    private @Getter int size;

    public EntityList(int capacity) {
        this.capacity = ++capacity;
        this.entities = (E[]) new Entity[capacity];
        this.size = 0;
        IntStream.rangeClosed(1, capacity).forEach(queue::add);
    }

    @Override public Iterator<E> iterator() {
        return null;
    }

    @Override public void forEach(Consumer<? super E> action) {
        for (E entity : entities) {
            if (entity == null)
                continue;

            action.accept(entity);
        }
    }

    @Override public Spliterator<E> spliterator() {
        return Spliterators.spliterator(entities, Spliterator.ORDERED);
    }

    public Optional<E> search(Predicate<? super E> filter) {
        for (E entity : entities) {
            if (entity == null)
                continue;
            if (filter.test(entity))
                return Optional.of(entity);
        }

        return Optional.empty();
    }

    public E get(int index) {
        return entities[index];
    }

    public void remove(E e) {
        Objects.requireNonNull(e);

        /*if (e.isRegistered() && entities[e.getIndex()] != null) {
            e.setRegistered(false);
            entities[e.getIndex()] = null;
            queue.add(e.getIndex());
            e.onRemove();
            size--;
        }*/
    }

    public boolean add(E e) {
        Objects.requireNonNull(e);

        if(isFull()) {
            return false;
        }

        if (!e.isRegistered()) {
            int slot = queue.remove();
            e.setRegistered(true);
            //e.setIndex(slot);
            entities[slot] = e;
            e.onAdd();
            size++;
            return true;
        }
        return false;
    }

    private boolean isFull() {
        return size + 1 >= capacity;
    }

    private static final class EntityListIterator<E extends Entity> implements Iterator<E> {

        private EntityList<E> list;
        private int index;
        private int lastIndex;

        public EntityListIterator(EntityList<E> list) {
            this.list = list;
        }

        @Override
        public boolean hasNext() {
            return !(index + 1 > list.getCapacity());
        }

        @Override
        public E next() {
            if (index >= list.getCapacity()) {
                throw new ArrayIndexOutOfBoundsException("There are no " + "elements left to iterate over!");
            }
            lastIndex = index;
            index++;
            return list.get(index);
        }

        @Override
        public void remove() {
            if (lastIndex == -1) {
                throw new IllegalStateException("This method can only be " + "called once after \"next\".");
            }
            list.remove(list.get(lastIndex));
            lastIndex = -1;
        }
    }
}