package com.example.flappydog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import static com.example.flappydog.Constants.CURRENT_CONTEXT;
import static com.example.flappydog.Constants.SCREEN_HEIGHT;
import static com.example.flappydog.Constants.SCREEN_WIDTH;

/**
 * @author Daniel Peng
 * description: ObstacleObject creates the obstacles (pipes) for the game.
 *              It uses BitmapFactory to apply the sprites onto the Rect objects.
 *              This class creates two Rect objects b/c the game requires a top
 *              and bottom pipe.
 */

public class ObstacleObject {
    private Rect obstacleRectTop;
    private Rect obstacleRectBottom;
    private int colour;

    private BitmapFactory bf = new BitmapFactory();
    private Bitmap bottomPipe = bf.decodeResource(CURRENT_CONTEXT.getResources(), R.drawable.bottom_pipe);
    private Bitmap topPipe = bf.decodeResource(CURRENT_CONTEXT.getResources(), R.drawable.top_pipe);

    public ObstacleObject(){
        colour = Color.rgb(200,200,200);

        int lengthTopPipe = (int)(Math.random() * SCREEN_HEIGHT);
        while (lengthTopPipe > ((SCREEN_HEIGHT / 4)* 3 - 300) || lengthTopPipe < (SCREEN_HEIGHT / 6) ){
            lengthTopPipe = (int)(Math.random() * SCREEN_HEIGHT);
        }
        int lengthBottomPipe = lengthTopPipe + 320;

        obstacleRectTop = new Rect((int)(SCREEN_WIDTH * 1.2),  0 , (int)(SCREEN_WIDTH * 1.2) + 160,lengthTopPipe);
        obstacleRectBottom = new Rect((int)(SCREEN_WIDTH * 1.2),  lengthBottomPipe, (int)(SCREEN_WIDTH * 1.2) + 160, SCREEN_HEIGHT);
    }

    // returns true if the obstacle x coordinate is less than -300
    public boolean isLeft(){
        return (obstacleRectTop.left < -300 && obstacleRectBottom.left < -300);
    }

    // returns the center X value
    public int get_centerX(){
        return obstacleRectTop.centerX();
    }

    // returns true if player Rect collide with obstacle object
    public boolean playerCollideObstacle(Rect player){
        return (Rect.intersects(obstacleRectTop, player) || Rect.intersects(obstacleRectBottom, player));
    }

    // updates the position of the obstacles according
    // to the dx and dy value
    public void update() {
        if (obstacleRectBottom.left == - 100){
            obstacleRectBottom.offset(0,0);
        }else{
            obstacleRectBottom.offset(-6, 0);
        }

        if (obstacleRectTop.left == -100){
            obstacleRectTop.offset(0,0);
        }else {
            obstacleRectTop.offset(-6, 0);
        }
    }

    // stops the dx and dy values of the obstacles
    public void update2(){
        obstacleRectBottom.offset(0, 0);
        obstacleRectTop.offset(0, 0);
    }

    // draws the objects onto the canvas
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(colour);
        canvas.drawBitmap(topPipe, null, obstacleRectTop, paint);
        canvas.drawBitmap(bottomPipe, null, obstacleRectBottom, paint);
    }
}