package engineering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.common.ScreenManager;
import com.utils.SPUtils;

/**
 * Created by sd on 2015/11/18.
 */
public class activity_splash extends Activity {
    ScreenManager screenManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        screenManager = ScreenManager.getScreenManager();
        screenManager.pushActivity(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), activity_welcom_gif.class);
                startActivity(intent);
            }
        }, 1000 * 5);

    }


}
