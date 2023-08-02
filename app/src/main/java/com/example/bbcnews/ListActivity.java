package com.example.bbcnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bbcnews.bean.LoginTime;
import com.example.bbcnews.databinding.ActivityListBinding;

import org.xutils.ex.DbException;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    ActivityListBinding binding;
    HistoryAdapter historyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_list);
        historyAdapter = new HistoryAdapter(ListActivity.this,getList());
        binding.lvData.setAdapter(historyAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.help:
                showNormalDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<LoginTime> getList(){
        try {
            List<LoginTime> loginTimeList = App.dbManager.findAll(LoginTime.class);
            if (loginTimeList ==null){
                return null;
            }
            return loginTimeList;
        } catch (DbException e) {
            throw new RuntimeException(e);
        }

    }

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
        normalDialog.setMessage(getString(R.string.history));
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
    class HistoryAdapter extends BaseAdapter{
        private Context context;
        private List<LoginTime> loginTimeList;

        public HistoryAdapter(Context context,List<LoginTime> loginTimeList){
            this.context = context;
            this.loginTimeList = loginTimeList;
        }

        @Override
        public int getCount() {
            return loginTimeList.size();
        }

        @Override
        public Object getItem(int position) {
            return loginTimeList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder =null;
            if (convertView==null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item,null);
                holder.tv_name = convertView.findViewById(R.id.tv_name);
                holder.tv_time = convertView.findViewById(R.id.tv_time);
                holder.ll = convertView.findViewById(R.id.ll);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_name.setText(loginTimeList.get(position).getName());
            holder.tv_time.setText(loginTimeList.get(position).getTime());
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        App.dbManager.delete(loginTimeList.get(position));
                        finish();
                    } catch (DbException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return convertView;
        }
        class ViewHolder{
            TextView tv_name,tv_time;
            LinearLayout ll;
        }
    }

}