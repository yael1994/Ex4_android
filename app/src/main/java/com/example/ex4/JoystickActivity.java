package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class JoystickActivity extends AppCompatActivity {
    private Joystick js;
    private boolean isTouch=false;
    private Client client;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.js=new Joystick(this);
        setContentView(this.js);
        String ip= getIntent().getStringExtra("ip");
        int port= (int) getIntent().getIntExtra("port",5400);
        client = new Client(ip,port);
        client.Connect();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int position_x = (int) event.getRawX();
        int position_y = (int) event.getRawY();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                // if the touch is outside the small circle then ignore it
                if (!this.isInSmallCircle(position_x, position_y-js.getStatusBarHeight())) {
                    return false;
                }
                // otherwise, update the flag for upcoming move actions
                this.isTouch = true;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (!this.isTouch) {
                    return false;
                }
                double distance = this.distance(position_x, position_y, this.js.getCenterX(), this.js.getCenterY());
                double size =  distance/this.js.getOuterRadius();
                if (size >= 1) {size = 1;}
                double angle = this.getAngle(position_x - this.js.getCenterX(), position_y - this.js.getCenterY());

                double elevator = Math.sin(Math.toRadians(angle)) * size * -1;
                double aileron = Math.cos(Math.toRadians(angle)) * size;
                String aileronMsg = "set controls/flight/aileron " + aileron + "\r\n";
                String elevatorMsg = "set controls/flight/elevator " + elevator + "\r\n";
                sendMsg(aileronMsg,elevatorMsg);

                // draw the new position
                this.drawPosition(position_x, position_y, angle, distance);
                break;
            }
            case MotionEvent.ACTION_UP:
                // place the joystick in its original position
                this.drawPosition(this.js.getCenterX(), this.js.getCenterY(),0,0);
                this.isTouch = false;
                break;

        }
        return true;
    }

    private boolean isInSmallCircle(int touchX, int touchY) {
        return this.distance(touchX, touchY, this.js.getCurrX(), this.js.getCurrY()) <=
                this.js.getInnerRadius();
    }

    private double distance(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private double getAngle(float dx, float dy) {
        if (dx >= 0 && dy >= 0) return Math.toDegrees(Math.atan(dy / dx));
        else if (dx < 0 && dy >= 0) return Math.toDegrees(Math.atan(dy / dx)) + 180;
        else if (dx < 0 && dy < 0) return Math.toDegrees(Math.atan(dy / dx)) + 180;
        else if (dx >= 0 && dy < 0) return Math.toDegrees(Math.atan(dy / dx)) + 360;
        return 0;
    }

    private void drawPosition(int touchX, int touchY, double angle, double distanceFromCenter) {
        int outerRadius = this.js.getOuterRadius();
        int newX;
        int newY;
        // if the position isn't outside the joystick, return the original values
        if (distanceFromCenter <= outerRadius) {
            newX=touchX;
            newY=touchY;
        }else {
            // placing the joystick on the edge of the pad according to the relative position to the center
            newX = this.js.getCenterX() + (int) (Math.cos(Math.toRadians(angle)) * outerRadius);
            newY = this.js.getCenterY() + (int) (Math.sin(Math.toRadians(angle)) * outerRadius);
        }

        this.js.setX(newX);
        this.js.setY(newY);
        this.js.postInvalidate();
    }

    /* update the server */
    public void sendMsg(final String msg1, final String msg2){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                client.SendToServer(msg1);
                client.SendToServer(msg2);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
