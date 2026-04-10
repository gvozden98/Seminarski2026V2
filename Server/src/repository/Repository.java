package repository;

import java.util.List;

public interface Repository<T> {

    List<T> getAll() throws Exception;

    List<T> getAll(T param, String uslov) throws Exception;

    T get(T param, String uslov) throws Exception;

    java.sql.PreparedStatement add(T param) throws Exception;

    void edit(T param) throws Exception;

    void delete(T param) throws Exception;
}
