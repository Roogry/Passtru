package id.roogry.passtru.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import id.roogry.passtru.database.PasstruRoomDatabase;
import id.roogry.passtru.database.AccountDao;
import id.roogry.passtru.models.Account;
import id.roogry.passtru.models.AccountAndSosmed;

public class AccountRepository {
    private final AccountDao accountsDao;
    private final ExecutorService executorService;

    public AccountRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        PasstruRoomDatabase db = PasstruRoomDatabase.getDatabase(application);
        accountsDao = db.accountDao();
    }

    public LiveData<List<AccountAndSosmed>> getAllAccounts() {
        return accountsDao.getAllAccounts();
    }

    public void insert(final Account account) {
        executorService.execute(() -> accountsDao.insert(account));
    }

    public void delete(final Account account){
        executorService.execute(() -> accountsDao.delete(account));
    }

    public void update(final Account account){
        executorService.execute(() -> accountsDao.update(account));
    }
}
