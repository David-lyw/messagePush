package engineering;

import android.app.Application;

import com.pgyersdk.crash.PgyCrashManager;


/**
 * Created by sd on 2015/11/18.
 */
public class PgyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        PgyCrashManager.register(this);
    }
}
