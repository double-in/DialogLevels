package xiaviv.dialoglevels.manger;

import java.io.Serializable;

/**
 * Author: xiaviv on 2017/5/13 11:35
 * <p>
 * Email: xiaviv713@gmail.com
 */

public class DialogTag implements Serializable {

    int level;
    String uuid;

    public DialogTag(@DialogManger.DialogLevel int level, String uuid) {
        this.level = level;
        this.uuid = uuid;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DialogTag) {
            DialogTag anotherDialogTag = (DialogTag) obj;

            if (this.level == anotherDialogTag.level
                    && this.uuid.equals(anotherDialogTag.uuid)) {
                return true;
            }
        }
        return false;
    }


}
