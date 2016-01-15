package com.popwindow;

import android.content.Context;
import android.widget.PopupWindow;
import android.view.LayoutInflater;

import engineering.R;

/**
 * Created by sd on 2015/11/2.
 */
public class popWindow_1 extends PopupWindow {
    LayoutInflater layoutInflater;
    Context context;
    String title;
    String content;
    String cancel;
    String confirm;

    public popWindow_1(Context context, String title, String content, String cancel, String confirm) {
        super(context);
        this.context = context;
        this.title = title;
        this.content = content;
        this.cancel = cancel;
        this.confirm = confirm;
    }

    //layoutInflater.inflate(R.layout.dialog)


}
