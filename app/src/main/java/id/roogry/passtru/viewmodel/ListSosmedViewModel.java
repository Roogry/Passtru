package id.roogry.passtru.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.roogry.passtru.models.Sosmed;
import id.roogry.passtru.repository.SosmedRepository;

public class ListSosmedViewModel extends ViewModel {
    private final SosmedRepository sosmedRepository;

    public ListSosmedViewModel(Application application){
        sosmedRepository = new SosmedRepository(application);
    }

    public LiveData<List<Sosmed>> getSosmeds() {
        return sosmedRepository.getAllSosmeds();
    }

    public void insert(Sosmed sosmed) {
        sosmedRepository.insert(sosmed);
    }

    public void delete(Sosmed sosmed) {
        sosmedRepository.delete(sosmed);
    }
}
