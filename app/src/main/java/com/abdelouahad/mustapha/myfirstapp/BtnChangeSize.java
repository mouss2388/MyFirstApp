package com.abdelouahad.mustapha.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class BtnChangeSize extends AppCompatActivity {

    private Button btn = null;

    private final String TAG = getClass().getSimpleName();
    private View.OnTouchListener touchBtn = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
      /* Réagir au toucher pour le bouton 1*/
            Log.i(TAG, "TESSST");
            if (v.getId()== R.id.btnSize){
                int height = btn.getHeight();
                int width = btn.getWidth();
                btn.setTextSize(Math.abs(event.getX() - width / 2) + Math.abs(event.getY() - height / 2));
            }

            return true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_change_size);

        btn = (Button) findViewById(R.id.btnSize);
        btn.setOnTouchListener(touchBtn);


    }
}
