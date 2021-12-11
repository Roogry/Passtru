package id.roogry.passtru.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.roogry.passtru.models.Account;
import id.roogry.passtru.models.Sosmed;
import id.roogry.passtru.repository.AccountRepository;
import id.roogry.passtru.repository.SosmedRepository;

public class HomeViewModel extends ViewModel {
    private final AccountRepository accountRepository;
    private final SosmedRepository sosmedRepository;

    public HomeViewModel(Application application) {
        accountRepository = new AccountRepository(application);
        sosmedRepository = new SosmedRepository(application);
    }

    public LiveData<List<Account>> getAccounts() {
        return accountRepository.getAllAccounts();
    }

    public LiveData<List<Sosmed>> getSosmeds() {
        return sosmedRepository.getAllSosmeds();
    }
}
