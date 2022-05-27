package by.lev.interfaces;

import java.util.List;

public interface DaoOperation<T,R>{
    boolean create(T t) throws Exception;
    T read(R r) throws Exception;
    List<T> readAll() throws Exception;

    boolean delete(R r) throws Exception;
}
