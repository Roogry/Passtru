package id.roogry.passtru.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import id.roogry.passtru.database.PasstruRoomDatabase;
import id.roogry.passtru.database.SosmedDao;
import id.roogry.passtru.models.AccountAndSosmed;
import id.roogry.passtru.models.Sosmed;

public class SosmedRepository {
    private final SosmedDao sosmedsDao;
    private final ExecutorService executorService;

    public SosmedRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        PasstruRoomDatabase db = PasstruRoomDatabase.getDatabase(application);
        sosmedsDao = db.sosmedDao();
    }

    public LiveData<List<Sosmed>> getAllSosmeds() {
        return sosmedsDao.getAllSosmeds();
    }

    public LiveData<List<Sosmed>> getSosmedBadges() {
        return sosmedsDao.getSosmedBadges();
    }

    public LiveData<List<Sosmed>> searchDatabase(String searchQuery) {
        return sosmedsDao.searchDatabase(searchQuery);
    }

    public void insert(final Sosmed sosmed) {
        executorService.execute(() -> sosmedsDao.insert(sosmed));
    }

    public void delete(final Sosmed sosmed){
        executorService.execute(() -> sosmedsDao.delete(sosmed));
    }

    public void deleteAccountSosmed(final Sosmed sosmed){
        executorService.execute(() -> sosmedsDao.deleteAccountSosmedId(sosmed.id));
    }

    public void update(final Sosmed sosmed){
        executorService.execute(() -> sosmedsDao.update(sosmed));
    }
}