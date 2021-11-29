package id.roogry.passtru.helpers;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import id.roogry.passtru.models.Account;
import id.roogry.passtru.models.Platform;

public interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAccount(Account account);

    @Update
    int updateAccount(Account account);

    @Delete
    int deleteAccount(Account account);

    @Query("SELECT * FROM tbAccount")
    Platform[] selectAllAccounts();

    @Query("SELECT * FROM tbAccount WHERE _id = :id LIMIT 1")
    Platform selectAccountDetail(int id);
}
