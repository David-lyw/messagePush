package translucenceActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import engineering.R;

/**
 * Created by sd on 2015/10/15.
 */
public class translucenceActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //此style配置到manifest中了
        // setTheme(R.style.Transparent);

        Log.i("lyw","半透明窗体加载onCreate");

        setContentView(R.layout.activity_engineering);
    }

    @Override
    public void onClick(View view) {

    }
}
