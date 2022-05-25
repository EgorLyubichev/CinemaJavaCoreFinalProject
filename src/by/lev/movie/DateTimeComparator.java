package by.lev.movie;

import java.sql.Timestamp;
import java.util.Comparator;

public class DateTimeComparator implements Comparator<Timestamp> {
    @Override
    public int compare(Timestamp o1, Timestamp o2) {
        return o1.compareTo(o2);
    }
}
