package com.example.jigsawpuzzle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Game extends AppCompatActivity {
    private static final String TAG = "leo" + Game.class.getSimpleName();
    private Puzzle puzzle = new Puzzle();
    private MoveButton card;
    //    private List<ImageView> backList = new ArrayList<>();
    private List<Space> spaceList = new ArrayList<>();
    private Map<Integer, Chip> chipMap;
    int [] imgIds = {R.drawable.dog411, R.drawable.dog412, R.drawable.dog421, R.drawable.dog422};
    int[] chipIds = {R.id.chip1, R.id.chip2, R.id.chip3, R.id.chip4};
    int imgId = -1;
    private int chipCnt = 4;
    private Button beginBnt;
    private TextView tv;
    private float x1, x2, x3, x4, y1, y2, y3, y4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // 接受参数
        getMessage();

        // 保存chip的原始位置
//        saveOriginChipPosition();  // have a bug temporarily

        // 初始化组件
        initComponent();

        // 添加响应事件
        addListener();

    }


    /**
     * 获取chip的初始位置
     */
    @SuppressLint("CutPasteId")
    private void saveOriginChipPosition(){
        x1 = findViewById(R.id.chip1).getX();
        x2 = findViewById(R.id.chip2).getX();
        x3 = findViewById(R.id.chip3).getX();
        x4 = findViewById(R.id.chip4).getX();

        y1 = findViewById(R.id.chip1).getY();
        y2 = findViewById(R.id.chip2).getY();
        y3 = findViewById(R.id.chip3).getY();
        y4 = findViewById(R.id.chip4).getY();
    }

    /**
     * 获取正确拼图数量
     *
     * @return
     */
    private int getBINGOChipCnt() {
        int cnt = 0;
        for (Chip chip : chipMap.values()) {
            if (chip.isRightPosition()) cnt++;
        }
        return cnt;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addListener() {

        // 开始按钮

        beginBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Chip chip: chipMap.values()){
                    chip.getView().setVisibility(View.VISIBLE);
                }

                // ====================添加时钟========================
//                cb.start();
            }
        });

        // 给chip添加响应事件
        for (Chip chip : chipMap.values()) {
            chip.getView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    Log.d(TAG, "onTouch: " +motionEvent.getAction());

                    Chip curChip = chipMap.get(view.getId());

                    // 表示当前放下卡片
                    if (motionEvent.getAction() == 1) {

                        for (Space space : spaceList) {
                            if (inPosition(view, space.getView()) && !space.isOccupy()) {
                                view.setX(space.getView().getX());
                                view.setY(space.getView().getY());

                                curChip.setCurX(space.getX());
                                curChip.setCurY(space.getY());

                                space.setOccupy(true);
                                Log.d(TAG, "onTouch: chipId=" + curChip.getId() + " chipCurX=" + curChip.getCurX() + " chipCurY=" + curChip.getCurY() + " chipRightX=" + curChip.getRightX() + " chipRightY=" + curChip.getRightY());


                                // 判断是否成功完成拼图
                                isBINGO();


                                break;
                            }
                        }
                    } else if (motionEvent.getAction() == 0) {  // 点击卡片
                        // 判断chip是否在space上
                        if (curChip.isInSpace()) {
//                            Log.d(TAG, "onTouch->"+chip.getCurX()+" "+chip.getCurY());
                            for (Space space : spaceList) {
                                if (space.getX() == curChip.getCurX() && space.getY() == curChip.getCurY()) {
                                    space.setOccupy(false);
                                    break;
                                }
                            }
                            curChip.outOfSpace();
                        }
                        Log.d(TAG, "onTouch->" + "chip is in space:" + curChip.isInSpace() + ", chip x:" + curChip.getCurX() + " " + curChip.getCurY());
                    }
                    return false;


                }
            });

        }

    }

    /**
     * 判断是否成功完成拼图 并完成成功响应
     */
    private boolean isBINGO() {
        Log.d(TAG, "onTouch: BINGOcnt = " + getBINGOChipCnt());

        if (getBINGOChipCnt() == chipCnt) {
//            cb.stop();
//            tv.setText("BINGO");
            // 添加弹窗
            Drawable icon = getResources().getDrawable(R.drawable.ok);

            // 创建弹窗
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(icon)
                    .setTitle("拼图完成")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setNegativeButton("重新挑战", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            rePlay();
                        }
                    })
                    .setMessage("恭喜！时间:[..]")
                    .create()
                    .show();
            return true;
        }
        return false;
    }

    /**
     * 重新挑战游戏
     */
    private void rePlay() {
        for (Space s : spaceList) {
            s.setOccupy(false);
        }
        // 重新设置chip
        setChip();
    }

    /**
     * 判断chip是否在合适的位置
     *
     * @param view
     * @param spaceView
     * @return
     */
    private boolean inPosition(View view, ImageView spaceView) {

        int len = 100;
        return view.getY() <= spaceView.getY() + len && view.getY() >= spaceView.getY() - len && view.getX() <= spaceView.getX() + len && view.getX() >= spaceView.getX() - len;
//        return false;
    }

    /**
     * 接受上一个界面传递的参数
     */
    private void getMessage() {
        Intent intent = getIntent();
        puzzle.setImageName(intent.getStringExtra("imageName"));
        puzzle.setDiff(intent.getIntExtra("diff", 4));
        puzzle.setImageId(intent.getIntExtra("imageId", 1));
    }

    /**
     * 初始化组件
     */
    private void initComponent() {
//        card = findViewById(R.id.chip1);
        tv = findViewById(R.id.tv);
        beginBnt = findViewById(R.id.beginBnt);

        // 初始化空白
        spaceList.add(new Space(R.id.space1, findViewById(R.id.space1), 1, 1));
        spaceList.add(new Space(R.id.space2, findViewById(R.id.space2), 1, 2));
        spaceList.add(new Space(R.id.space3, findViewById(R.id.space3), 2, 1));
        spaceList.add(new Space(R.id.space4, findViewById(R.id.space4), 2, 2));

        // 初始化chip
        switch ("dog") {
            case "dog":

                imgIds[0] = R.drawable.dog411;
                imgIds[1] = R.drawable.dog412;
                imgIds[2] = R.drawable.dog421;
                imgIds[3] = R.drawable.dog422;
                imgId = R.drawable.dog;
                setChip();
                break;
            default:
                break;
        }


    }

    /**
     * 设置chip的图片以及x,y
     */
    private void setChip() {
//        relocateChipPosition();
        // 设置参考图片
        findViewById(R.id.img).setBackground(getResources().getDrawable(imgId));

        // 打乱chipView顺序
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        for (int i = 0; i < 5; i++) {
            int a = Math.abs(random.nextInt()) % chipCnt;
            int b = Math.abs(random.nextInt()) % chipCnt;
            int temp = chipIds[a];
            chipIds[a] = chipIds[b];
            chipIds[b] = temp;
        }

        int k = 0;
        chipMap = new HashMap<>();
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 2; j++, k++) {
//                int index = i*(chipCnt/2)+j;
//                Log.d(TAG, "setChip-> chipid:" + chipIds[index]);
                // 设置chip背景图片
                Log.d(TAG, "setChip: imgId=" + imgIds[k] + " i=" + i + " j=" + j + " k=" + k + " viewId="+chipIds[k]);
                MoveButton view = findViewById(chipIds[k]);

                view.setBackground(getResources().getDrawable(imgIds[k]));

                chipMap.put(chipIds[k], new Chip(i, j, view, chipIds[k]));
            }
        }

        // 隐藏chip
        for(Chip chip : chipMap.values()){
            chip.getView().setVisibility(View.INVISIBLE);
        }


        Log.d(TAG, "setChip: chipMapSize=" + chipMap.size());
    }

    /**
     * 重新定位chip的位置
     */
    private void relocateChipPosition() {
        findViewById(R.id.chip1).setX(x1);
        findViewById(R.id.chip2).setX(x2);
        findViewById(R.id.chip3).setX(x3);
        findViewById(R.id.chip4).setX(x4);

        findViewById(R.id.chip1).setY(y1);
        findViewById(R.id.chip2).setY(y2);
        findViewById(R.id.chip3).setY(y3);
        findViewById(R.id.chip4).setY(y4);

    }
}