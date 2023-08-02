package com.example.bbcnews;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.bbcnews.databinding.ActivityRegisterBinding;
import com.example.bbcnews.db.UserDBUtils;
import com.example.redbook.entity.User;




/**

 * @description:
 **/
public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        makerEditText();
        binding.help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalDialog();
            }
        });
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
        normalDialog.setMessage(getString(R.string.register_message));
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
                binding.tlPasswordAgain.setErrorEnabled(false);
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
                binding.tlPasswordAgain.setErrorEnabled(false);

            }
        });

        binding.passwordAgain.addTextChangedListener(new TextWatcher() {
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
                binding.tlPasswordAgain.setErrorEnabled(false);

            }
        });

        //点击注册、
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = binding.account.getText().toString();
                String password = binding.password.getText().toString();
                String passwordAgagin = binding.passwordAgain.getText().toString();
                if (TextUtils.isEmpty(account)){
                    binding.tlAccount.setError(getString(R.string.no_account));
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    binding.tlPassword.setError(getString(R.string.no_password));
                    return;
                }
                if (TextUtils.isEmpty(passwordAgagin)){
                    binding.tlPasswordAgain.setError(getString(R.string.no_password));
                    return;
                }
                if (!password.equals(passwordAgagin)){
                    binding.tlPassword.setError(getString(R.string.pwd));
                    binding.tlPasswordAgain.setError(getString(R.string.pwd));
                    return;
                }

                User user = new User();
                user.setName(account);
                user.setPassword(password);
                user.setHead_url("");
                user.setUser_id(System.currentTimeMillis()+"");
                int i = UserDBUtils.getInstance(getApplicationContext()).insert(user);
                if (i==1){
                    Toast.makeText(getApplicationContext(),getString(R.string.register_state),Toast.LENGTH_SHORT).show();
                    return;
                }
                if (i==-1){
                    Toast.makeText(getApplicationContext(),getString(R.string.has_register),Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }


}
