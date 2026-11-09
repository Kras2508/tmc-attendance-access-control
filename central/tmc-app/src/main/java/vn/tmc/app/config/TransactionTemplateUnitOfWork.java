package vn.tmc.app.config;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import vn.tmc.shared.tx.UnitOfWork;

import java.util.function.Supplier;

/**
 * Spring-transaction implementation of UnitOfWork (ADR-02).
 */
@Component
public class TransactionTemplateUnitOfWork implements UnitOfWork {
    private final TransactionTemplate transactionTemplate;

    public TransactionTemplateUnitOfWork(PlatformTransactionManager transactionManager) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    public <T> T execute(Supplier<T> work) {
        return transactionTemplate.execute(status -> work.get());
    }
}