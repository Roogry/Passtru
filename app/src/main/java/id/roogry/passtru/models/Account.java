package id.roogry.passtru.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "accounts")
public class Account implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeInt(this.idSosmed);
        parcel.writeString(this.username);
        parcel.writeString(this.password);
        parcel.writeString(this.createdAt);
    }
    @Ignore
    public Account(){
    }

    public Account(int id, int idSosmed, String username, String password, String createdAt) {
        this.id = id;
        this.idSosmed = idSosmed;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
    }

    private Account(Parcel in) {
        this.id = in.readInt();
        this.idSosmed = in.readInt();
        this.username = in.readString();
        this.password = in.readString();
        this.createdAt = in.readString();
    }

    public static final Parcelable.Creator<Account> CREATOR = new Parcelable.Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel source) {
            return new Account(source);
        }
        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

}
