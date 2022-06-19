package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity{
    private EditText editName;
    private EditText editPwd;
    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        dbOpenHelper = new DBOpenHelper(LoginActivity.this);
        editName = findViewById(R.id.edit_name);
        editPwd = findViewById(R.id.edit_pwd);
    }

    /**
     * 登录（查询）
     * @param view
     */
    public void Login(View view) {
        String Name = editName.getText().toString().trim();
        String Pad = editPwd.getText().toString().trim();
        if (TextUtils.isEmpty(Name) || TextUtils.isEmpty(Pad)) {
            Toast.makeText(LoginActivity.this, "账号或密码不能为空", Toast.LENGTH_LONG).show();
        } else {
            List<User> list = dbOpenHelper.query();
            boolean isSuccess = false;
            for (User user: list) {
                if(Name.equals(user.getName()) && Pad.equals(user.getPassword())){
                    isSuccess = true;
                }
            }
            if (isSuccess) {
                editName.setText("");
                editPwd.setText("");
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("root", Name);
                intent.putExtra("password", Pad);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 跳转注册界面
     * @param view
     */
    public void Register(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }


}