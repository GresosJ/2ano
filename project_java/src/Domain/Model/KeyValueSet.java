package Domain.Model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import Domain.Interfaces.IKeyValueSet;

public class KeyValueSet<K, V> implements IKeyValueSet<K, V>, Serializable {

    private K key;
    private Set<V> values;

    public KeyValueSet() {
        this.values = new TreeSet<>();
    }

    public KeyValueSet(K key) {
        this();
        this.key = key;
    }

    public KeyValueSet(K key, Iterable<V> values) {
        this();
        this.key = key;
        this.addValue(values);
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public void addValue(V v) {
        this.values.add(v);
    }

    @Override
    public void addValue(Iterable<V> sv) {
        for (V v : sv) {
            addValue(v);
        }
    }

    @Override
    public V removeValue(V v) throws NullPointerException {
        if (values.contains(v)) {
            values.remove(v);
            return v;
        }
        throw new NullPointerException("O valor não se encontra no set para ser removido.");
    }

    @Override
    public boolean hasValue(Object v) {
        return values.contains(v);
    }

    @Override
    public List<V> getValues() {
        return values.stream().collect(Collectors.toList());
    }

    @Override
    public int size() {
        return this.values.size();
    }

}
