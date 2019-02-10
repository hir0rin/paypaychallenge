package task1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ImmutableQueue<T> implements Queue<T> {

    private final ArrayList<T> q;

    public ImmutableQueue() {
        this.q = new ArrayList<>();
    }

    public ImmutableQueue(Collection<T> c) {
        this.q = new ArrayList<>(c);
    }

    private ImmutableQueue(Collection<T> c, T tail) {
        this.q = new ArrayList<>(c);
        this.q.add(tail);
    }

    @Override
    public Queue<T> enQueue(T t) {
        return new ImmutableQueue<T>(q, t);
    }

    @Override
    public Queue<T> deQueue() {
        if (this.isEmpty() || this.size() == 1) {
            return new ImmutableQueue<T>();
        }
        return new ImmutableQueue<T>(q.subList(1, q.size()));
    }

    @Override
    public T head() {
        return q.stream().findFirst().orElse(null);
    }

    @Override
    public boolean isEmpty() {
        return q.isEmpty();
    }

    @Override
    public int size() {
        return q.size();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[")
                .append(String.join(",", q.stream().map(T::toString).collect(Collectors.toList())))
                .append("]")
                .toString();
    }
}
