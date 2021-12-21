package id.roogry.passtru.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class AccountAndSosmed {
    @Embedded
    public Account account;

    @Relation(
            parentColumn = "id_sosmed",
            entityColumn = "id"
    )
    public Sosmed sosmed;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Sosmed getSosmed() {
        return sosmed;
    }

    public void setSosmed(Sosmed sosmed) {
        this.sosmed = sosmed;
    }
}
