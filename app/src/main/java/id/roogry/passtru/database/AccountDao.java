package id.roogry.passtru.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.roogry.passtru.models.Account;

@Dao
public interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Account account);

    @Update
    void update(Account account);

    @Delete
    void delete(Account account);

    @Query("SELECT accounts.*, sosmeds.title FROM accounts INNER JOIN sosmeds ON accounts.id_sosmed=sosmeds.id ORDER BY id DESC")
    LiveData<List<Account>> getAllAccounts();

    @Query("SELECT * FROM accounts WHERE id = :id LIMIT 1")
    Account getAccountById(int id);
}
