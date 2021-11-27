package id.roogry.passtru.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "tbAccount")
public class Account {
    @PrimaryKey(autoGenerate = true)
    public int _id;

    @ForeignKey(entity = Platform.class,
            parentColumns = "_id",
            childColumns = "id_platform",
            onDelete = ForeignKey.CASCADE)

    @ColumnInfo(name = "id_platform")
    public int id_platform;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "created_at")
    public Timestamp created_at;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getId_platform() {
        return id_platform;
    }

    public void setId_platform(int id_platform) {
        this.id_platform = id_platform;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
