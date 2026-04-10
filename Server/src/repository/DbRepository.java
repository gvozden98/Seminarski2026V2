package repository;

public interface DbRepository<T> extends Repository<T> {

    default void connect() throws Exception {
        java.sql.Connection connection = DbConnectionFactory.getInstance().getConnection();
        if (connection == null || connection.isClosed()) {
            throw new Exception("Konekcija sa bazom nije uspostavljena.");
        }
    }

    default void commit() throws Exception {
        DbConnectionFactory.getInstance().getConnection().commit();
    }

    default void rollback() throws Exception {
        DbConnectionFactory.getInstance().getConnection().rollback();
    }
}
