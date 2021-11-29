package id.roogry.passtru.helpers;

import androidx.room.RoomDatabase;

public abstract class DbRoomHelper extends RoomDatabase {
    public abstract PlatformDao platformDao();
}
