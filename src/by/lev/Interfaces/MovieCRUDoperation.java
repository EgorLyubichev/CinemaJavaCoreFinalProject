package by.lev.Interfaces;

import java.util.List;

public interface MovieCRUDoperation<T, R> {
    boolean create(T t) throws Exception;
    T read(R r) throws Exception;
    List<T> readAll() throws Exception;
    boolean update(T oldT, T newT) throws Exception;
    boolean delete(R r) throws Exception;
}
