package HCP.Utils;

import HCP.Enums.PatientAge;

import java.util.HashMap;

public class Counter {
    private final int limit;
    private final HashMap<PatientAge, Integer> counter = new HashMap<>();

    public Counter(int limit) {
        this.limit = limit;
        for (PatientAge age: PatientAge.values()) {
            counter.put(age, 0);
        }
    }

    public boolean reachedLimit(PatientAge age) {
        return this.limit == counter.get(age);
    }

    public void increment(PatientAge age) {
        int count = counter.get(age);

        if (count == limit) return;

        counter.put(age, count + 1);
    }

    public void decrement(PatientAge age) {
        int count = counter.get(age);

        if (count == 0) return;

        counter.put(age, count - 1);
    }

}
