package com.junzaivip.filepersistencedemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取editText实例
        editText = (EditText)findViewById(R.id.edit);
        String inputText = load();
        if(!TextUtils.isEmpty((inputText))){
            editText.setText(inputText);
            //setSelection()表示将光标移动在文本框的末尾位置, 以便继续输入
            editText.setSelection(inputText.length());
            //弹出Toast, 给出一个提示, 表示读取数据成功
            Toast.makeText(this, "读取数据成功!", Toast.LENGTH_SHORT).show();
        }
    }

    // 重写onDestroy(), 可以保证活动销毁之前一定会调用这个方法.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = editText.getText().toString();
        save(inputText);
    }

    public void save (String inputText){
        FileOutputStream out = null;
        BufferedWriter writer = null;

        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                  if(writer!= null) {
                      writer.close();
                  }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    public String load (){
        FileInputStream in = null;
        BufferedReader reader =  null;
        StringBuilder content = new StringBuilder();
        try {
            //获取FileInputStream对象
            in = openFileInput("data");
            //借助FileInputStream对象, 构建出一个BufferedReader对象
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            //通过BufferedReader对象进行一行行的读取, 把文件中的所有内容全部读取出来
            // 并存放在StringBuilder对象中
            while ((line = reader.readLine())!= null){
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //最后将读取到的内容返回
        return  content.toString();
    }
}


