package id.roogry.passtru.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.roogry.passtru.models.Account;
import id.roogry.passtru.repository.AccountRepository;
import id.roogry.passtru.repository.SosmedRepository;

public class ResetPinViewModel extends ViewModel {

    private final AccountRepository accountRepository;

    public ResetPinViewModel(Application application) {
        accountRepository = new AccountRepository(application);
    }

    public  LiveData<List<Account>> getAccounts(String username, String password) {
        return accountRepository.getAccountsByUserPassword(username, password);
    }
}
