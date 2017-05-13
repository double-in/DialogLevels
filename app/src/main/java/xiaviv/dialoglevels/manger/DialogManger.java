package xiaviv.dialoglevels.manger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.IntDef;
import android.support.annotation.RestrictTo;
import android.support.v4.content.LocalBroadcastManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Stack;

import static android.support.annotation.RestrictTo.Scope.GROUP_ID;

/**
 * Author: xiaviv on 2017/5/13 11:36
 * <p>
 * Email: xiaviv713@gmail.com
 */

public class DialogManger {
    private static final String TAG = "DialogManger";
    private static DialogManger mInstance;
    public static final String ACTION_DIALOG_DISMISS = "action_dialog_dismiss";

    public static final String DIALOG_TAG = "dialog_tag";

    private Stack<DialogTag> mDialogLevels = new Stack<>();
    private DialogDismissReceiver mDialogDismissReceiver = new DialogDismissReceiver();

    private DialogManger(Context context) {
        IntentFilter filter = new IntentFilter(ACTION_DIALOG_DISMISS);
        LocalBroadcastManager.getInstance(context).registerReceiver(mDialogDismissReceiver, filter);
    }

    public static DialogManger getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DialogManger.class) {
                if (mInstance == null) {
                    mInstance = new DialogManger(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    public class DialogDismissReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                DialogTag tag = (DialogTag) intent.getSerializableExtra(DIALOG_TAG);
                dismiss(tag);
            }
        }
    }


    /**
     * 活动弹窗的优先级
     */
    public final static int AD_PRIORITY = 1;
    /**
     * 更新弹窗的优先级
     */
    public final static int UPDATE_PRIORITY = 2;
    /**
     * alert弹窗的优先级
     */
    public final static int ALERT_PRIORITY = 3;
    /**
     * 登录弹窗的优先级
     */
    public final static int LOGIN_PRIORITY = 4;
    /**
     * other弹窗的优先级
     */
    public final static int OTHER_PRIORITY = 5;


    @RestrictTo(GROUP_ID)
    @IntDef({AD_PRIORITY, UPDATE_PRIORITY, ALERT_PRIORITY, LOGIN_PRIORITY, OTHER_PRIORITY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DialogLevel {
    }


    public boolean canShow(DialogTag tag) {
        if (mDialogLevels.size() > 0) {

            DialogTag topTag = mDialogLevels.peek();
            if (tag.level >= topTag.level) {
                mDialogLevels.push(tag);
                return true;
            } else {
                return false;
            }
        } else {
            mDialogLevels.push(tag);
            return true;
        }
    }

    private void dismiss(DialogTag tag) {
        int i = mDialogLevels.lastIndexOf(tag);
        if (i >= 0) {
            mDialogLevels.removeElementAt(i);
        }
    }
}
