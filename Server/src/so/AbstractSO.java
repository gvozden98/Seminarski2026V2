package so;

import domain.AbstractDomainObject;
import repository.DbRepository;
import repository.DbRepositoryGeneric;
import repository.Repository;

public abstract class AbstractSO {

    protected final Repository<AbstractDomainObject> broker;

    public AbstractSO() {
        broker = new DbRepositoryGeneric();
    }

    public void execute(Object object) throws Exception {
        try {
            startTransaction();
            validate(object);
            executeOperation(object);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            System.out.println("Rollback izvrsen zbog greske: " + e.getMessage());
            throw e;
        }
    }

    protected abstract void validate(Object object) throws Exception;

    protected abstract void executeOperation(Object object) throws Exception;

    private void startTransaction() throws Exception {
        ((DbRepository<AbstractDomainObject>) broker).connect();
    }

    private void commitTransaction() throws Exception {
        ((DbRepository<AbstractDomainObject>) broker).commit();
    }

    private void rollbackTransaction() throws Exception {
        ((DbRepository<AbstractDomainObject>) broker).rollback();
    }
}
