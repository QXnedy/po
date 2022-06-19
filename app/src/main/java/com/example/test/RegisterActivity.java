package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class RegisterActivity extends AppCompatActivity{
    private EditText edit_name;
    private EditText edit_pwd;
    private EditText edit_pwd2;
    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        dbOpenHelper = new DBOpenHelper(RegisterActivity.this);
        edit_name = findViewById(R.id.edit_name);
        edit_pwd = findViewById(R.id.edit_pwd);
        edit_pwd2 = findViewById(R.id.edit_pwd2);
    }

    /**
     * 注册（查询、增）
     * @param view
     */
    public void Register(View view) {
        String Name = edit_name.getText().toString().trim();
        String Pwd = edit_pwd.getText().toString().trim();
        String Pwd2 = edit_pwd2.getText().toString().trim();
        if (TextUtils.isEmpty(Name) || TextUtils.isEmpty(Pwd) || TextUtils.isEmpty(Pwd2)) {
            Toast.makeText(RegisterActivity.this, "账号和密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            List<User> list = dbOpenHelper.query();
            boolean isSuccess = false;
            for (User user: list) {
                if(Name.equals(user.getName())){
                    isSuccess = true;
                }
            }
            if (isSuccess) {
                Toast.makeText(RegisterActivity.this, "该用户已存在", Toast.LENGTH_LONG).show();
            } else {
                if (Pwd.equals(Pwd2)) {
                    if (dbOpenHelper.add(Name, Pwd)) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * 返回
     * @param view
     */
    public void Back(View view) {
        finish();
    }
}