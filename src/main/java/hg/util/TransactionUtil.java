package hg.util;

import hg.party.dao.org.MemberDao;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


@Component(immediate = true,service = TransactionUtil.class)
public class TransactionUtil {
    private PlatformTransactionManager platformTransactionManager;
    private ThreadLocal<TransactionStatus> transactionStatus = new ThreadLocal<>();
    private DefaultTransactionDefinition transactionDefinition;

    public TransactionUtil(){
        platformTransactionManager = new DataSourceTransactionManager(new MemberDao().getJdbcTemplate().getDataSource());
        transactionStatus = new ThreadLocal<>();
        transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    public void startTransaction() {
        TransactionStatus tmp = platformTransactionManager.getTransaction(transactionDefinition);
        transactionStatus.set(tmp);
    }
    public void commit() {
        TransactionStatus tmp = transactionStatus.get();
        if (tmp == null) {
            throw new RuntimeException("no transcation");
        }
        platformTransactionManager.commit(tmp);
        transactionStatus.remove();
    }

    public void rollback() {
        TransactionStatus tmp = transactionStatus.get();
        if (tmp == null) {
            throw new RuntimeException("no transcation");
        }
        platformTransactionManager.rollback(tmp);
        transactionStatus.remove();
    }
}
