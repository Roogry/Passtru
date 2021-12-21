package id.roogry.passtru.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.roogry.passtru.models.Sosmed;

@Dao
public interface SosmedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Sosmed sosmed);

    @Update
    void update(Sosmed sosmed);

    @Delete
    void delete(Sosmed sosmed);

    @Query("SELECT * FROM sosmeds ORDER BY id DESC LIMIT 5")
    LiveData<List<Sosmed>> getSosmedBadges();

    @Query("SELECT * FROM sosmeds ORDER BY id DESC")
    LiveData<List<Sosmed>> getAllSosmeds();

    @Query("SELECT * FROM sosmeds WHERE id = :id LIMIT 1")
    Sosmed getSosmedById(int id);
}
