package id.roogry.passtru.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import id.roogry.passtru.models.Account;
import id.roogry.passtru.models.Sosmed;

@Database(entities = {Sosmed.class, Account.class}, version = 1, exportSchema = false)
public abstract class PasstruRoomDatabase extends RoomDatabase {
    public abstract SosmedDao sosmedDao();
    public abstract AccountDao accountDao();

    private static volatile PasstruRoomDatabase INSTANCE;

    public static PasstruRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PasstruRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        PasstruRoomDatabase.class, "passtru_database")
                        .build();
            }
        }
        return INSTANCE;
    }
}
