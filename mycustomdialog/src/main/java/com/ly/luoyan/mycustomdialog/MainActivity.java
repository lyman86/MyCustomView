package com.ly.luoyan.mycustomdialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ly.luoyan.mycustomdialog.dialog.CustomDialog;
import com.ly.luoyan.mycustomdialog.utils.MyWindow;
import com.ly.luoyan.mycustomdialog.utils.WindowUtil;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener{
    private CustomDialog.Builder builder;
    private MyWindow window;
    private DialogInterface.OnClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        builder = new CustomDialog.Builder(this);
        window = WindowUtil.getWindow(this);
    }

//    public void showProgressDialog() {
//        MyWindow window = WindowUtil.getWindow(this);
//        builder.setDialogLoading(window.winth * 2 / 3, window.height * 1 / 8);
//    }

    public void showProgressDialog() {
        builder.setDialogLoading(window.winth * 2 / 3, window.height * 1 / 8,"玩命加载中...");
    }

    public void setDialogAlert(){
        builder.setDialogAlert(window.winth * 2 / 3,window.height * 1 / 5,"提示","确定删除吗？");
    }

    public void proClick(View v){
        showProgressDialog();
    }
    public void alertClick(View v){
        setDialogAlert();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Toast.makeText(this,which+"",Toast.LENGTH_SHORT).show();
        builder.dismissDialog();
    }


}
