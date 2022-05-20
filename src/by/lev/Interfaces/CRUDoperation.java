package by.lev.Interfaces;

import java.util.List;

public interface CRUDoperation<T, R> {
    boolean create(T t) throws Exception;
    T read(R r) throws Exception;
    List<T> readAll() throws Exception;
    boolean update(R oldW, R newW) throws Exception;
    boolean delete(R r) throws Exception;
}
