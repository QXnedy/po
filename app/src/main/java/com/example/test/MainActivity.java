package com.example.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    DBOpenHelper openHelper;
    String root;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        openHelper = new DBOpenHelper(MainActivity.this);
        root = getIntent().getStringExtra("root");
        password = getIntent().getStringExtra("password");
    }

    /**
     * 跳转修改密码界面
     * @param view
     */
    public void Update(View view) {
        Intent intent = new Intent(MainActivity.this, ChangeActivity.class);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    /**
     * 注销（删）
     * @param view
     */
    public void Logout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("!!!");
        builder.setMessage("确定注销账号吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (openHelper.delete(root, password)) {
                    Toast.makeText(MainActivity.this, "注销成功", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "注销失败", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 退出
     * @param view
     */
    public void Back(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("!!!");
        builder.setMessage("确定要退出登录吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Toast.makeText(MainActivity.this, "退出成功！", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}