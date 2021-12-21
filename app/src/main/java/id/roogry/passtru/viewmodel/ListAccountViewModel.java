package id.roogry.passtru.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.roogry.passtru.models.Account;
import id.roogry.passtru.models.AccountAndSosmed;
import id.roogry.passtru.repository.AccountRepository;

public class ListAccountViewModel extends ViewModel {
    private final AccountRepository accountRepository;

    public ListAccountViewModel(Application application){
        accountRepository = new AccountRepository(application);
    }

    public LiveData<List<AccountAndSosmed>> getAccounts() {
        return accountRepository.getAllAccounts();
    }

    public void delete(Account account) {
        accountRepository.delete(account);
    }
}
