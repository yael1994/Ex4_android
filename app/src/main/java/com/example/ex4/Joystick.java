package com.example.ex4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;

public class Joystick extends View {
    private final int SMALL_RADIUS;
    private final int BIG_RADIUS;
    private final int CENTER_X;
    private final int CENTER_Y;

    private Paint outerColor;
    private Paint innerColor;
    private int currX;
    private int currY;
    private DisplayMetrics mMetrics;

    public Joystick(Context context) {
        super(context);

        this.outerColor = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.outerColor.setColor(Color.GRAY);
        this.outerColor.setStyle(Paint.Style.FILL);


        this.innerColor = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.innerColor.setColor(Color.parseColor("#ffcf00"));
        this.innerColor.setStyle(Paint.Style.FILL);

        mMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
        currX = CENTER_X = mMetrics.widthPixels / 2;
        currY = CENTER_Y = mMetrics.heightPixels / 2;
        BIG_RADIUS = mMetrics.widthPixels / 3;
        SMALL_RADIUS = BIG_RADIUS / 3;

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result*(int) mMetrics.density;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#33ACE8"));
        canvas.drawCircle(CENTER_X, CENTER_Y, BIG_RADIUS, outerColor);
        canvas.drawCircle(currX, currY, SMALL_RADIUS, innerColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    public int getOuterRadius() {
        return this.BIG_RADIUS;
    }

    public int getInnerRadius() {
        return this.SMALL_RADIUS;
    }

    public int getCenterX() {
        return CENTER_X;
    }

    public int getCenterY() {
        return CENTER_Y;
    }

    public int getCurrX() {
        return this.currX;
    }

    public int getCurrY() {
        return this.currY;
    }

    public void setX(int x) {
        this.currX = x;
    }

    public void setY(int y) {
        this.currY = y;
    }
}