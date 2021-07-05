package com.example.test04;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zhao.uav.control.BottomUIControl;
import com.zhao.uav.control.LeftUIControl;
import com.zhao.uav.control.RightUIControl;
import com.zhao.uav.control.TopUIControl;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        new TopUIControl(MainActivity.this).topUI();
        new BottomUIControl(MainActivity.this).bottomUI();
        new LeftUIControl(MainActivity.this).leftUI();
        new RightUIControl(MainActivity.this).rightUI();
    }




   
}


