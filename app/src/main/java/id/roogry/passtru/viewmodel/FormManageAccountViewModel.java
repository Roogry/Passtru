package id.roogry.passtru.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.roogry.passtru.models.Account;
import id.roogry.passtru.models.Sosmed;
import id.roogry.passtru.repository.AccountRepository;
import id.roogry.passtru.repository.SosmedRepository;

public class FormManageAccountViewModel extends ViewModel {
    private final AccountRepository accountRepository;
    private final SosmedRepository sosmedRepository;

    public FormManageAccountViewModel(Application application) {
        accountRepository = new AccountRepository(application);
        sosmedRepository = new SosmedRepository(application);
    }

    public LiveData<List<Sosmed>> getSosmeds() {
        return sosmedRepository.getAllSosmeds();
    }

    public void insertSosmed(Sosmed sosmed) {
        sosmedRepository.insert(sosmed);
    }

    public void insert(Account account) {
        accountRepository.insert(account);
    }

    public void update(Account account) {
        accountRepository.update(account);
    }

    public void delete(Account account) {
        accountRepository.delete(account);
    }
}
