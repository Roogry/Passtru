package id.roogry.passtru.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "accounts")
public class Account {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ForeignKey(entity = Sosmed.class,
            parentColumns = "id",
            childColumns = "id_sosmed",
            onDelete = ForeignKey.CASCADE)

    @ColumnInfo(name = "id_sosmed")
    public int idSosmed;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "created_at")
    public String createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSosmed() {
        return idSosmed;
    }

    public void setIdSosmed(int idSosmed) {
        this.idSosmed = idSosmed;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Account(Integer idSosmed, String username, String password, String createdAt) {
        this.idSosmed = idSosmed;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
    }
}
