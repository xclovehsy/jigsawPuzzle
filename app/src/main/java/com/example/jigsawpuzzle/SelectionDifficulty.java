package com.example.jigsawpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class SelectionDifficulty extends AppCompatActivity {

    private Button imgBnt;
    private Button playBnt;
    private Spinner diffSp;
    private String imageName = "";
    private int imageId = 0;
    private int diff = 0; // 0表示2×2 1表示3×3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_difficulty);

        // 初始化组件
        initComponent();

        // 添加响应事件
        addListener();

    }

    /**
     * 添加响应事件
     */
    private void addListener() {

        imgBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        playBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 传递参数
                if (diff == 0) { // 2×2
                    Intent intent = new Intent(SelectionDifficulty.this, Game.class);
                    intent.putExtra("imageName", imageName);
                    intent.putExtra("imageId", imageId);
                    intent.putExtra("diff", diff);
                    startActivity(intent);
                } else {  // 选择3×3
                    Intent intent = new Intent(SelectionDifficulty.this, Game9.class);
                    intent.putExtra("imageName", imageName);
                    intent.putExtra("imageId", imageId);
                    intent.putExtra("diff", diff);
                    startActivity(intent);
                }

            }
        });

        diffSp.setSelection(0);        //初始化，默认选择列表中第0个元素
        diffSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // TODO
                if (pos == 0) {
                    diff = 0;
                } else if (pos == 1) {
                    diff = 1;
                } else {
                    diff = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO
            }
        });

    }

    /**
     * 初始化组件
     */
    private void initComponent() {
        imgBnt = findViewById(R.id.chooseImage);
        playBnt = findViewById(R.id.play_game);
        diffSp = findViewById(R.id.diffSp);


    }
}