package com.example.jigsawpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class SelectionDifficulty extends AppCompatActivity {

    private Button diffBnt;
    private Button imgBnt;
    private Button playBnt;
    private final Puzzle puzzle = new Puzzle();

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
        diffBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imgBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        playBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 传递参数
                Intent intent = new Intent(SelectionDifficulty.this, Game.class);
                intent.putExtra("imageName", puzzle.getImageName());
                intent.putExtra("imageId", puzzle.getImageId());
                intent.putExtra("diff", puzzle.getDiff());
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化组件
     */
    private void initComponent() {
        diffBnt = findViewById(R.id.choosediff);
        imgBnt = findViewById(R.id.chooseImage);
        playBnt = findViewById(R.id.play_game);

    }
}