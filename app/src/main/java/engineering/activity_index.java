package engineering;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.SDPathBrowse.SDPathBrowse;
import com.SDPathBrowse.SDPathBrowse1;
import com.SDPathBrowse.searchView;
import com.anyView.activity_dropDownList;
import com.anyView.activity_gridview;
import com.anyView.activity_loadAnimation;
import com.anyView.activity_showGif;
import com.anyView.activity_showView;
import com.anyView.activity_touchImageView_index;
import com.common.ScreenManager;
import com.deviceInfromation.activity_deviceInfo;
import com.dialog.activity_dialog_index;
import com.example.sd.messagepush.MainActivity;

import translucenceActivity.translucenceActivity;

import com.fragmenttabhost.activity_fragmenttabhost;
import com.json.activity_json_index;
import com.listview.activity_listview_index;
import com.listview.activity_sortlistview;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.popwindow.activity_popwindow_index;
import com.quitAPP.activity1;
import com.slidemenu.activity_slideMenu_index;
import com.readAndWriteSdCard.rwSdCard;
import com.timeUtils.activity_timeUtils_index;
import com.webview.activity_webView;
import com.anyView.activity_viewpagefragment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;


/**
 * Created by sd on 2015/10/14.
 */
public class activity_index extends Activity implements OnClickListener {
    Intent intent;
    ScreenManager screenManager;

    Button btn_pushMessage;
    Button btn_pushMessage_sure;
    TextView tv_pushMessage_explain;
    EditText et_pushMessage_etExplain;

    Button btn_translucenceActivity;
    Button btn_translucenceActivity_sure;
    TextView tv_translucence_explain;
    EditText et_translucence_etExplain;

    Button btn_slideMenu;
    Button btn_rwSDCard;//读写sd中的文件
    Button btn_SDPathBrowse;//sd卡文件浏览器
    Button btn_SDPathBrowse1;
    Button btn_searchView;
    Button btn_combSearchView;
    Button btn_listview;
    Button btn_fragmenttabhost;
    Button btn_dialog;
    Button btn_popwindow;
    Button btn_quitApp;
    Button btn_deviceInfo;
    Button btn_anyView_index;
    Button btn_json_index;
    Button btn_timeUtil_index;
    Button btn_spinner;//下拉列表
    Button btn_showGif;
    Button btn_webView_index;
    Button btn_loadAnimation;
    Button btn_girdview;
    Button btn_viewpagerFragment;
    Button btn_touchImageView;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    final String FILE_NAME = "function_explain.txt";//文件名
    FileInputStream fis;//文件输入流
    FileOutputStream fos;//文件输出流


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //tijiaodaima   隐隐约约
        // gggggg       uuuuuuuuuuuuu
        //        tttttttttttttttttggggggggggggggggggg

        super.onCreate(savedInstanceState);
        //隐藏标题---功能不能实现，代码报错???
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_index);
        //蒲公英crash捕获
        PgyCrashManager.register(this);

        //蒲公英版本更新
        PgyUpdateManager.register(this, new UpdateManagerListener() {
            @Override
            public void onNoUpdateAvailable() {
            }

            @Override
            public void onUpdateAvailable(String result) {
                // 将新版本信息封装到AppBean中
                final AppBean appBean = getAppBeanFromString(result);
                new AlertDialog.Builder(activity_index.this)
                        .setTitle("更新")
                        .setMessage("是否更新程序？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startDownloadTask(activity_index.this, appBean.getDownloadURL());
                            }
                        }).show();
            }
        });

        initView();
        initData();
        initListener();
    }

    //@Override 横竖屏幕切换。
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("lyw", "屏幕横竖屏切换，执行重写的onConfigurationChanged，不再执行onCreat");
    }

    public void initView() {
        //SharedPreferences的四种操作模式:
        // Context.MODE_PRIVATE：为默认操作模式, 代表该文件是私有数据, 只能被应用本身访问, 在该模式下, 写入的内容会覆盖原文件的内容
        // Context.MODE_APPEND：模式会检查文件是否存在, 存在就往文件追加内容, 否则就创建新文件.............
        // Context.MODE_WORLD_READABLE和Context.MODE_WORLD_WRITEABLE用来控制其他应用是否有权限读写该文件.
        // MODE_WORLD_READABLE：表示当前文件可以被其他应用读取.
        // MODE_WORLD_WRITEABLE：表示当前文件可以被其他应用写入.
        sharedPreferences = getApplicationContext().getSharedPreferences("function_Explain", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        screenManager = ScreenManager.getScreenManager();

        btn_pushMessage = (Button) findViewById(R.id.btn_pushMessage);
        btn_pushMessage_sure = (Button) findViewById(R.id.btn_pushMessage_sure);
        tv_pushMessage_explain = (TextView) findViewById(R.id.tv_pushMessage_explain);
        et_pushMessage_etExplain = (EditText) findViewById(R.id.et_pushMessage_etExplain);

        btn_translucenceActivity = (Button) findViewById(R.id.btn_translucenceActivity);
        btn_translucenceActivity_sure = (Button) findViewById(R.id.btn_translucenceActivity_sure);
        tv_translucence_explain = (TextView) findViewById(R.id.tv_translucence_explain);
        et_translucence_etExplain = (EditText) findViewById(R.id.et_translucence_etExplain);

        btn_slideMenu = (Button) findViewById(R.id.btn_slideMenu);
        btn_rwSDCard = (Button) findViewById(R.id.btn_rwSDCard);
        btn_SDPathBrowse = (Button) findViewById(R.id.btn_SDPathBrowse);//sd卡文件浏览器
        btn_SDPathBrowse1 = (Button) findViewById(R.id.btn_SDPathBrowse1);
        btn_searchView = (Button) findViewById(R.id.btn_searchView);
        btn_combSearchView = (Button) findViewById(R.id.btn_combSearchView);
        btn_listview = (Button) findViewById(R.id.btn_listview);
        btn_fragmenttabhost = (Button) findViewById(R.id.btn_fragmenttabhost);
        btn_dialog = (Button) findViewById(R.id.btn_dialog);
        btn_popwindow = (Button) findViewById(R.id.btn_popwindow);
        btn_quitApp = (Button) findViewById(R.id.btn_quitApp);
        btn_deviceInfo = (Button) findViewById(R.id.btn_deviceInfo_index);
        btn_anyView_index = (Button) findViewById(R.id.btn_anyView_index);
        btn_json_index = (Button) findViewById(R.id.btn_json_index);
        btn_timeUtil_index = (Button) findViewById(R.id.btn_timeUtil_index);
        btn_spinner = (Button) findViewById(R.id.btn_spinner);
        btn_showGif = (Button) findViewById(R.id.btn_showGif);
        btn_webView_index = (Button) findViewById(R.id.btn_webView_index);
        btn_loadAnimation = (Button) findViewById(R.id.btn_loadAnimation);
        btn_girdview = (Button) findViewById(R.id.btn_girdview);
        btn_viewpagerFragment = (Button) findViewById(R.id.btn_viewpagerFragment);
        btn_touchImageView = (Button) findViewById(R.id.btn_touchImageView);


    }

    public void initData() {
        //为该textview设置sharedPreference中存储的内容。
        // tv_pushMessage_explain.setText(sharedPreferences.getString("消息推送", ""));

        //为该textview设置文件function_explain.txt中存储的内容。
        //tv_translucence_explain.setText(read());


    }

    public void initListener() {
        btn_pushMessage.setOnClickListener(this);
        btn_pushMessage_sure.setOnClickListener(this);


        btn_translucenceActivity.setOnClickListener(this);
        btn_translucenceActivity_sure.setOnClickListener(this);

        btn_slideMenu.setOnClickListener(this);
        btn_rwSDCard.setOnClickListener(this);
        btn_SDPathBrowse.setOnClickListener(this);
        btn_SDPathBrowse1.setOnClickListener(this);
        btn_searchView.setOnClickListener(this);
        btn_combSearchView.setOnClickListener(this);
        btn_listview.setOnClickListener(this);
        btn_fragmenttabhost.setOnClickListener(this);
        btn_dialog.setOnClickListener(this);
        btn_popwindow.setOnClickListener(this);
        btn_quitApp.setOnClickListener(this);//退出app.
        btn_deviceInfo.setOnClickListener(this);
        btn_anyView_index.setOnClickListener(this);
        btn_json_index.setOnClickListener(this);
        btn_timeUtil_index.setOnClickListener(this);
        btn_spinner.setOnClickListener(this);
        btn_showGif.setOnClickListener(this);
        btn_webView_index.setOnClickListener(this);
        btn_loadAnimation.setOnClickListener(this);
        btn_girdview.setOnClickListener(this);
        btn_viewpagerFragment.setOnClickListener(this);
        btn_touchImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pushMessage://开启消息推送Activity
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_pushMessage_sure://开启消息推送Activity:确定按钮
                String newAppendContent = et_pushMessage_etExplain.getText().toString().trim();
                editor.putString("消息推送", tv_pushMessage_explain.getText() + newAppendContent);
                editor.commit();
                et_pushMessage_etExplain.setText("");
                tv_pushMessage_explain.setText(sharedPreferences.getString("消息推送", ""));
                break;

            case R.id.btn_translucenceActivity://开启半透明Activity
                intent = new Intent(this, translucenceActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_translucenceActivity_sure://开启半透明Activity:确定按钮
                String appendContent_translucenceActivity = et_translucence_etExplain.getText().toString().trim();
                String save_All = tv_translucence_explain.getText() + appendContent_translucenceActivity;
                write(save_All);//写入到文件
                et_translucence_etExplain.setText("");
                tv_translucence_explain.setText(read());
                break;

            case R.id.btn_slideMenu://开启左右滑动界面Activity
                intent = new Intent(this, activity_slideMenu_index.class);
                startActivity(intent);
                break;

            case R.id.btn_rwSDCard:
                intent = new Intent(this, rwSdCard.class);
                startActivity(intent);
                break;

            case R.id.btn_SDPathBrowse:
                intent = new Intent(this, SDPathBrowse.class);
                startActivity(intent);
                break;
            case R.id.btn_SDPathBrowse1:
                intent = new Intent(this, SDPathBrowse1.class);
                startActivity(intent);
                break;
            case R.id.btn_searchView:
                intent = new Intent(this, searchView.class);
                startActivity(intent);

                break;
            case R.id.btn_combSearchView:
                intent = new Intent(this, activity_sortlistview.class);
                startActivity(intent);
                break;
            case R.id.btn_listview:
                intent = new Intent(this, activity_listview_index.class);
                startActivity(intent);
                break;
            case R.id.btn_fragmenttabhost:
                intent = new Intent(this, activity_fragmenttabhost.class);
                startActivity(intent);
                break;
            case R.id.btn_dialog:
                intent = new Intent(this, activity_dialog_index.class);
                startActivity(intent);
                break;
            case R.id.btn_popwindow:
                intent = new Intent(this, activity_popwindow_index.class);
                startActivity(intent);
                break;
            case R.id.btn_quitApp:
                screenManager.pushActivity(activity_index.this);
                intent = new Intent(this, activity1.class);
                startActivity(intent);
                break;
            case R.id.btn_deviceInfo_index:
                intent = new Intent(this, activity_deviceInfo.class);
                startActivity(intent);
                break;
            case R.id.btn_anyView_index:
                intent = new Intent(this, activity_showView.class);
                startActivity(intent);
                break;
            case R.id.btn_json_index:
                intent = new Intent(this, activity_json_index.class);
                startActivity(intent);
                break;
            case R.id.btn_timeUtil_index:
                intent = new Intent(this, activity_timeUtils_index.class);
                startActivity(intent);
                break;
            case R.id.btn_spinner:
                intent = new Intent(this, activity_dropDownList.class);
                startActivity(intent);
                break;
            case R.id.btn_showGif:
                intent = new Intent(this, activity_showGif.class);
                startActivity(intent);
                break;
            case R.id.btn_webView_index:
                intent = new Intent(this, activity_webView.class);
                startActivity(intent);
                break;
            case R.id.btn_loadAnimation:
                intent = new Intent(this, activity_loadAnimation.class);
                startActivity(intent);
                break;
            case R.id.btn_girdview:
                intent = new Intent(this, activity_gridview.class);
                startActivity(intent);
                break;
            case R.id.btn_viewpagerFragment:
                intent = new Intent(this, activity_viewpagefragment.class);
                startActivity(intent);
                break;
            case R.id.btn_touchImageView://ImageTouchView
                intent = new Intent(this, activity_touchImageView_index.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 从文件中读取字符串
     *
     * @return
     */
    private String read() {
        try {
            fis = openFileInput(FILE_NAME);
            byte[] buff = new byte[1024];
            int hasRead = 0;
            StringBuilder sb = new StringBuilder("");
            //读取文件内容
            while ((hasRead = fis.read(buff)) > 0) {
                sb.append(new String(buff, 0, hasRead));
            }
            //关闭文件输入流
            fis.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 写字符串到文件
     *
     * @param content
     */
    private void write(String content) {
        try {
            //以追加的模式打开文件输出流-->输出，即写数据到文件中
            fos = openFileOutput(FILE_NAME, MODE_APPEND);
            //将FileOutputStream包装成PrintStream
            PrintStream ps = new PrintStream(fos);
            //输出文件内容
            ps.println(content);
            //关闭文件输出流
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //手机返回键功能
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        screenManager.popAllActivityExceptOne(activity_index.class);
    }
}
