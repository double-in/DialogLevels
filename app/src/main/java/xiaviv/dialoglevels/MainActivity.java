package xiaviv.dialoglevels;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import xiaviv.dialoglevels.activity.ADDialogActivity;
import xiaviv.dialoglevels.activity.AlertDialogActivity;
import xiaviv.dialoglevels.activity.LoginDialogActivity;
import xiaviv.dialoglevels.activity.OtherDialogActivity;
import xiaviv.dialoglevels.activity.UpdateDialogActivity;
import xiaviv.dialoglevels.manger.DialogManger;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private int[] count;
    private CountDownTimer mCountDownTimer;
    private int index = 0;
    private TextView tv_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_level = (TextView) findViewById(R.id.tv_level);

    }

    private StringBuilder sb = new StringBuilder();

    public void onClick(View view) {
        count = randomCommon(0, 6, 5);
        assert count != null;
        for (int level : count) {
            sb.append(level);
            if (level != count[count.length - 1]) {
                sb.append(",");
            }
        }
        tv_level.setText(String.valueOf("startDialogActivity level : " + sb.toString()));
        sb.setLength(0);
        startCountdown();
    }

    private synchronized void startCountdown() {

        mCountDownTimer = new CountDownTimer(7 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (index < count.length) {
                    startDialogActivity(count[index]);
                    index++;
                } else {
                    index = 0;
                    endCountdown();
                }

                Log.e(TAG, "onTick:  index :" + index);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void startDialogActivity(int levels) {
        switch (levels) {
            case DialogManger.AD_PRIORITY:
                ADDialogActivity.launch(MainActivity.this);
                break;
            case DialogManger.UPDATE_PRIORITY:
                UpdateDialogActivity.launch(MainActivity.this);
                break;
            case DialogManger.ALERT_PRIORITY:
                AlertDialogActivity.launch(MainActivity.this);
                break;
            case DialogManger.LOGIN_PRIORITY:
                LoginDialogActivity.launch(MainActivity.this);
                break;
            case DialogManger.OTHER_PRIORITY:
                OtherDialogActivity.launch(MainActivity.this);
                break;
        }

    }

    public void endCountdown() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        endCountdown();
    }

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     *
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n   随机数个数
     */
    public static int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }


}
