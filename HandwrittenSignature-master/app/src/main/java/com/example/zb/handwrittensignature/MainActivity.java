package com.example.zb.handwrittensignature;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_img)
    ImageView mIvImg;
    @BindView(R.id.line_view)
    LinePathView mLineView;
    @BindView(R.id.bt_ok)
    Button mBtOk;
    @BindView(R.id.bt_cancel)
    Button mBtCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //初始化
        mLineView.setPenColor(Color.BLACK);
        mLineView.setBackColor(Color.WHITE);

    }

    @OnClick({R.id.bt_ok, R.id.bt_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_ok:
                //确定
                Bitmap bitMap = mLineView.getBitMap();
                /**
                 * 保存文件
                 * 私有目录
                 * @param bm
                 * @param fileName
                 * @throws IOException
                 */
                // 动态申请存储读写权限，否则失败
                // 在使用的地方发起请求，系统会有一个弹窗，提示是否允许读写权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 222);

                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Hanyk";
//                Log.e("------path", path);
                File files = new File(path);
                if (!files.exists()) {
                    files.mkdirs();
                }
                path += "/HandWriting.png";
                if (mLineView.getTouched()) {
                    try {
                        mLineView.save(path, true, 10);
                        Log.i("HandWriting", "保存成功");
                        Toast.makeText(MainActivity.this, "照片保存成功：Picture/Hanyk/HandWriting.png", Toast.LENGTH_SHORT).show();
                        mIvImg.setImageBitmap(BitmapFactory.decodeFile(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "您没有签名~", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.bt_cancel:
                //清除
                mLineView.clear();
                break;
        }
    }
}
