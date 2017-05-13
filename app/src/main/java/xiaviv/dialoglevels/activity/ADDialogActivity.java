package xiaviv.dialoglevels.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.TextView;

import java.util.UUID;

import xiaviv.dialoglevels.R;
import xiaviv.dialoglevels.manger.DialogManger;
import xiaviv.dialoglevels.manger.DialogTag;

/**
 * Author: xiaviv on 2017/5/13 12:35
 * <p>
 * Email: xiaviv713@gmail.com
 */

public class ADDialogActivity extends Activity {
    private DialogTag mDialogTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        ((TextView) findViewById(R.id.tv_activity)).setText("This is ADDialogActivity");

        mDialogTag = (DialogTag) getIntent().getSerializableExtra(DialogManger.DIALOG_TAG);

    }

    public static void launch(Activity activity) {
        DialogTag dialogTag = new DialogTag(DialogManger.AD_PRIORITY, UUID.randomUUID().toString());
        if (DialogManger.getInstance(activity).canShow(dialogTag)) {
            Intent intent = new Intent(activity, ADDialogActivity.class);
            intent.putExtra(DialogManger.DIALOG_TAG, dialogTag);
            activity.startActivity(intent);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(new Intent(DialogManger.ACTION_DIALOG_DISMISS)
                        .putExtra(DialogManger.DIALOG_TAG, mDialogTag));
    }
}
