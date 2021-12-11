package id.roogry.passtru.models;

import androidx.room.Embedded;
import androidx.room.Relation;

public class SosmedWithAccount {
    @Embedded
    public Sosmed sosmed;

    @Relation(
            parentColumn = "id",
            entityColumn = "student_id"
    )
    public Account account;
}
