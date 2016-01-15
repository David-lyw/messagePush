package generalActivity;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import engineering.R;

/**
 * Created by sd on 2015/10/19.
 */
public class generalActivity extends Activity implements OnClickListener {

    public static final int DIALOG = 1;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DIALOG:
                    Toast.makeText(getApplicationContext(), "2222222", Toast.LENGTH_LONG).show();
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //横竖屏切换
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void initView() {
    }

    public void initData() {
    }

    public void initListener() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
                break;
        }

    }
}
