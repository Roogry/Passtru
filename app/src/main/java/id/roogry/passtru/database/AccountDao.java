package id.roogry.passtru.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import id.roogry.passtru.models.Account;
import id.roogry.passtru.models.AccountAndSosmed;
import id.roogry.passtru.models.Sosmed;

@Dao
public interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Account account);

    @Update
    void update(Account account);

    @Delete
    void delete(Account account);

    @Transaction
    @Query("SELECT * FROM accounts ORDER BY id DESC")
    LiveData<List<AccountAndSosmed>> getAllAccounts();

    @Query("SELECT * FROM accounts ORDER BY id DESC LIMIT 3")
    LiveData<List<AccountAndSosmed>> getAccountRecents();

    @Query("SELECT * FROM accounts WHERE username = :username AND password = :password")
    LiveData<List<Account>> getAccountsByUsernamePassword(String username, String password);

    @Query("SELECT accounts.*, sosmeds.title AS sosmedTitle FROM accounts INNER JOIN sosmeds ON accounts.id_sosmed=sosmeds.id WHERE  username LIKE :searchQuery OR sosmedTitle LIKE :searchQuery ")
    LiveData<List<AccountAndSosmed>> searchDatabase(String searchQuery);
}
