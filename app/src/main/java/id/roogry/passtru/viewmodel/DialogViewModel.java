package id.roogry.passtru.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import id.roogry.passtru.models.Sosmed;
import id.roogry.passtru.repository.SosmedRepository;

public class DialogViewModel extends ViewModel {
    private SosmedRepository sosmedRepository;

    public DialogViewModel(Application application) {
        sosmedRepository = new SosmedRepository(application);
    }
    public void insertSosmed(Sosmed sosmed) {
        sosmedRepository.insert(sosmed);
    }
}