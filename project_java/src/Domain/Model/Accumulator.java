package Domain.Model;

import java.io.Serializable;

import Domain.Interfaces.IAccumulator;

public class Accumulator implements IAccumulator, Serializable, Comparable<Accumulator> {

    private Integer key;
    private Integer acc;

    public Accumulator(Integer key) {
        this.key = key;
        this.acc = 0;
    }

    @Override
    public void add() {
        this.acc++;
    }

    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public Integer getAcc() {
        return this.acc;
    }

    @Override
    public int compareTo(Accumulator o) {
        return key.compareTo(o.getKey());
    }

}
