package com.readAndWriteSdCard;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import engineering.R;

/**
 * Created by sd on 2015/10/21.
 */
public class rwSdCard extends Activity implements OnClickListener {
    final String FILE_NAME = "/crazyit.bin";
    TextView tv_filecontent;
    EditText et_inputcontent;
    Button btn_read, btn_write;
    Button btn_cleartv, btn_deletefile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_w_sdcard);
        initView();
        initData();
        initListener();
    }

    public void initView() {
        tv_filecontent = (TextView) findViewById(R.id.tv_filecontent);
        et_inputcontent = (EditText) findViewById(R.id.et_inputcontent);
        btn_read = (Button) findViewById(R.id.btn_read);
        btn_write = (Button) findViewById(R.id.btn_write);
        btn_cleartv = (Button) findViewById(R.id.btn_cleartv);
        btn_deletefile = (Button) findViewById(R.id.btn_deletefile);
    }

    public void initData() {
        //tv_filecontent.setText(read());


    }

    public void initListener() {
        btn_write.setOnClickListener(this);
        btn_read.setOnClickListener(this);
        btn_cleartv.setOnClickListener(this);
        btn_deletefile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_read:
                tv_filecontent.setText(read());

                break;
            case R.id.btn_write:
                String append = et_inputcontent.getText().toString().trim();
                String content = tv_filecontent.getText() + append;
                write(content);//写入到文件中
                et_inputcontent.setText("");
                break;
            case R.id.btn_cleartv:
                tv_filecontent.setText("");
                break;

            case R.id.btn_deletefile:
                Log.i("lyw", "点击事件");

                deletefile(FILE_NAME);

                break;
        }


    }

    private String read() {
        try {
            // 如果手机插入了SD卡，而且应用程序具有访问SD的权限
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                // 获取SD卡对应的存储目录
                File sdCardDir = Environment.getExternalStorageDirectory();
                // 获取指定文件对应的输入流
                FileInputStream fis = new FileInputStream(
                        sdCardDir.getCanonicalPath() + FILE_NAME);
                // 将指定输入流包装成BufferedReader
                BufferedReader br = new BufferedReader(new
                        InputStreamReader(fis));
                StringBuilder sb = new StringBuilder("");
                String line = null;
                // 循环读取文件内容
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                // 关闭资源
                br.close();
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void write(String content) {
        try {
            // 如果手机插入了SD卡，而且应用程序具有访问SD的权限
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                // 获取SD卡的目录
                File sdCardDir = Environment.getExternalStorageDirectory();
                File targetFile = new File(sdCardDir
                        .getCanonicalPath() + FILE_NAME);
                // 以指定文件创建 RandomAccessFile对象
                RandomAccessFile raf = new RandomAccessFile(
                        targetFile, "rw");
                // 将文件记录指针移动到最后
                raf.seek(targetFile.length());
                // 输出文件内容
                raf.write(content.getBytes());
                // 关闭RandomAccessFile
                raf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deletefile(String filename) {
        try {
            //获取sd卡目录
            File sdCardDir = Environment.getExternalStorageDirectory();

            File targetFile = new File(sdCardDir.getCanonicalPath() + filename);
            if (targetFile.exists()) {
                Log.i("lyw", "文件存在。。。。。。。。。。");
                targetFile.delete();//删除该文件
            } else {
                Log.i("lyw", "走else 。。。。。。。。。。");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
