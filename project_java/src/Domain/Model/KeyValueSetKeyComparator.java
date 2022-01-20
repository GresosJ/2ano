package Domain.Model;

import java.io.Serializable;
import java.util.Comparator;

import Domain.Interfaces.IKeyValueSet;

public class KeyValueSetKeyComparator<V> implements Comparator<IKeyValueSet<Integer, V>>, Serializable {

    @Override
    public int compare(IKeyValueSet<Integer, V> o1, IKeyValueSet<Integer, V> o2) {
        return o1.getKey().compareTo(o2.getKey());
    }

}
