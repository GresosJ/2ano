package Domain.Interfaces;

import java.util.List;

public interface IKeyValueSet<K, V> {
    public K getKey();

    public void addValue(V v);

    public void addValue(Iterable<V> sv);

    public V removeValue(V v) throws NullPointerException;

    public boolean hasValue(V v);

    public List<V> getValues();

    public int size();
}
