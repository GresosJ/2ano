package Services.Repositories;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map.Entry;

import Domain.Interfaces.IClassification;

public class EntryClassificationComparator implements Comparator<Entry<Integer, IClassification>>, Serializable {

    @Override
    public int compare(Entry<Integer, IClassification> o1, Entry<Integer, IClassification> o2) {
        return o2.getKey().compareTo(o2.getKey());
    }
    
}
