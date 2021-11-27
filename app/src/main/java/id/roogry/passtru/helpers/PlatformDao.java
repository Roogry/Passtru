package id.roogry.passtru.helpers;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import id.roogry.passtru.models.Platform;

public interface PlatformDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPlatform(Platform platform);

    @Update
    int updatePlatform(Platform platform);

    @Delete
    int deletePlatform(Platform platform);

    @Query("SELECT * FROM tbPlatform")
    Platform[] selectAllPlatforms();

    @Query("SELECT * FROM tbPlatform WHERE _id = :id LIMIT 1")
    Platform selectPlatformDetail(int id);
}
