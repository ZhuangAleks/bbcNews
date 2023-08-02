package com.example.bbcnews;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.bbcnews.bean.LoginTime;
import com.example.bbcnews.databinding.ActivityLoginBinding;
import com.example.bbcnews.db.UserDBUtils;

import org.xutils.ex.DbException;

import java.text.SimpleDateFormat;
import java.util.Date;


/**

 * @description:登陆界面
 **/
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        initListener();
    }


    protected void initListener() {
        makerEditText();
        progressBar = new ProgressBar(this);

        //注册
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this,RegisterActivity.class),101);
            }
        });
        //

        binding.help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalDialog();
            }
        });

    }

    public static String getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }
    //android  dialog 使用
    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setIcon(R.drawable.ic_launcher_background);
        normalDialog.setTitle(getString(R.string.hint));
        normalDialog.setMessage(getString(R.string.login_message));
        normalDialog.setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton(getString(R.string.close),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }


    /**
     * 判断登录输入信息
     */
    private void makerEditText(){
        binding.account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tlAccount.setErrorEnabled(false);
                binding.tlPassword.setErrorEnabled(false);
            }
        });
        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tlAccount.setErrorEnabled(false);
                binding.tlPassword.setErrorEnabled(false);
            }
        });

        //点击登录
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = binding.account.getText().toString();
                String password = binding.password.getText().toString();
                if (TextUtils.isEmpty(account)){
                    binding.tlAccount.setError(getString(R.string.no_account));
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    binding.tlPassword.setError(getString(R.string.no_password));
                    return;
                }
                //数据库查询用户信息
                int i = UserDBUtils.getInstance(getApplicationContext()).Quer(password,account);
                if (i ==1){
                    LoginTime loginTime = new LoginTime();
                    loginTime.setTime(getTime());
                    loginTime.setName(account);
                    loginTime.setId(System.currentTimeMillis()+"");
                    try {
                        App.dbManager.save(loginTime);
                    } catch (DbException e) {
                        throw new RuntimeException(e);
                    }
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    return;
                }
                if (i == -1){
                    Toast.makeText(getApplicationContext(),getString(R.string.error),Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(),getString(R.string.error),Toast.LENGTH_SHORT).show();
            }
        });
    }





}
