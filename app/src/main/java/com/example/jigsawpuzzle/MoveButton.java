package com.example.jigsawpuzzle;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class MoveButton extends AppCompatButton {

    private int lastX;
    private int lastY;
    private int screenWidth, screenHeight;
    private OnClickListener listener;

    public MoveButton(Context context) {
        super(context);
    }

    public MoveButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    private int firstX;
    private int firstY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 按下
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                firstX = (int) event.getRawX();
                firstY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (event.getRawX() - lastX);
                int dy = (int) (event.getRawY() - lastY);
                int left = getLeft() + dx;
                int top = getTop() + dy;
                int right = getRight() + dx;
                int bottom = getBottom() + dy;
                if (left < 0) {
                    left = 0;
                    right = left + getWidth();
                }
                if (right > screenWidth) {
                    right = screenWidth;
                    left = right - getWidth();
                }
                if (top < 0) {
                    top = 0;
                    bottom = top + getHeight();
                }
                if (bottom > screenHeight) {
                    bottom = screenHeight;
                    top = bottom - getHeight();
                }
                layout(left, top, right, bottom);
                Log.v("TAG", "left=" + left + "top" + top + "right" + right + "bottom" + bottom);
                Log.v("TAG", "screenWidth=" + screenWidth + "screenHeight=" + screenHeight);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                return true;
            case MotionEvent.ACTION_UP:
                int moveX = (int) (event.getRawX() - firstX);
                int moveY = (int) (event.getRawY() - firstY);
                if (moveX > 4 || moveX < -4 || moveY > 4 || moveY < -4) {
                    return true;
                }
                if (listener != null) {
                    listener.onClick(this);
                }
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * 点击事件监听，解决滑动与点击冲突
     *
     * @param listener
     */
    public void setMoveOnClickListener(OnClickListener listener) {
        this.listener = listener;
        setOnClickListener(listener);
    }

    /**
     * 屏蔽点击事件
     *
     * @param l
     */
    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("MoveButton", "不准用这个方法!!!");
            }
        });
    }
}
