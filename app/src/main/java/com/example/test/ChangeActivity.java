package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeActivity extends AppCompatActivity {
    private EditText old_paw;
    private EditText new_paw1;
    private EditText new_paw2;
    private String password;
    private DBOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_change);

        old_paw = findViewById(R.id.old_paw);
        new_paw1 = findViewById(R.id.new_paw1);
        new_paw2 = findViewById(R.id.new_paw2);
        helper = new DBOpenHelper(ChangeActivity.this);
        password = getIntent().getStringExtra("password");
    }

    /**
     * 修改密码（改）
     * @param view
     */
    public void Confirm(View view) {
        String paw1 = old_paw.getText().toString().trim();
        String paw2 = new_paw1.getText().toString().trim();
        String paw3 = new_paw2.getText().toString().trim();
        if (TextUtils.isEmpty(paw1) || TextUtils.isEmpty(paw2) || TextUtils.isEmpty(paw3)) {
            Toast.makeText(ChangeActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
        } else {
            if (!password.equals(paw1)) {
                Toast.makeText(ChangeActivity.this, "您输入的旧密码错误，请重新输入", Toast.LENGTH_LONG).show();
                old_paw.setText("");
            } else {
                if (!paw2.equals(paw3)) {
                    Toast.makeText(ChangeActivity.this, "您两次输入的新密码不一致，请重新输入", Toast.LENGTH_LONG).show();
                    new_paw1.setText("");
                    new_paw2.setText("");
                } else {
                    if (helper.update(paw2)) {
                        Toast.makeText(ChangeActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(ChangeActivity.this, "修改失败", Toast.LENGTH_LONG).show();
                    }
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