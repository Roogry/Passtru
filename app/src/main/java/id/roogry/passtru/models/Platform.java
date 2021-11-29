package id.roogry.passtru.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbPlatform")
public class Platform {

    @PrimaryKey(autoGenerate = true)
    public int _id;
    @ColumnInfo(name = "title")
    public String title;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
