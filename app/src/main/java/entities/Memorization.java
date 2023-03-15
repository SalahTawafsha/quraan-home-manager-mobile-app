package entities;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Memorization {
    private String name;
    private int percentOfMemorization;

    public Memorization() {
    }

    public Memorization(String name, int percentOfMemorization) {
        this.name = name;
        this.percentOfMemorization = percentOfMemorization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPercentOfMemorization() {
        return percentOfMemorization;
    }

    public void setPercentOfMemorization(int percentOfMemorization) {
        this.percentOfMemorization = percentOfMemorization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Memorization that = (Memorization) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @NonNull
    @Override
    public String toString() {
        return name +
                ", التقييم: " + percentOfMemorization;
    }
}
