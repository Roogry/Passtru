package id.roogry.passtru.helpers;

import id.roogry.passtru.models.Sosmed;

public interface MoreOptionInterface {
    public void getDataByPos(int position);
    public void copyPassword(int position);
    public void delete(int position);
    public void updateSosmed(int position, String sosmedTitle);
}
