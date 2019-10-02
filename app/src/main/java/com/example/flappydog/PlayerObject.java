package com.example.flappydog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import static com.example.flappydog.Constants.CURRENT_CONTEXT;
import static com.example.flappydog.Constants.SCREEN_HEIGHT;

/**
 * @author Daniel Peng
 * description: This player class uses a Rect object for the player and uses
 *              BitmapFactory to apply the sprites onto the Rect object. It
 *              also uses a list to do animations for the player. This class
 *              contains a method to check collision.
 */

public class PlayerObject {
    private Rect rect;
    private int colour;
    private int offsetCounter = 0;

    private Bitmap[] frames;
    private int framesIndex;
    private long lastFrame;
    private float frameTime;

    private BitmapFactory bf = new BitmapFactory();
    private Bitmap dogUp = bf.decodeResource(CURRENT_CONTEXT.getResources(), R.drawable.dog_tail_up);
    private Bitmap dogMid = bf.decodeResource(CURRENT_CONTEXT.getResources(), R.drawable.dog_tail_mid);
    private Bitmap dogDown = bf.decodeResource(CURRENT_CONTEXT.getResources(), R.drawable.dog_tail_down);

    public PlayerObject(){
        rect = new Rect(150, (SCREEN_HEIGHT/2) - 50, 250, (SCREEN_HEIGHT/2) + 50);
        colour = Color.rgb(200,200,100);
        frames = new Bitmap[]{dogUp, dogMid , dogDown};
        framesIndex = 0;
        frameTime = (0.5f / frames.length);
        lastFrame = System.currentTimeMillis();
    }

    // checks collision with the player Rect with
    // the top of the canvas
    public boolean playerCollideTop(){
        return (rect.top < 0);
    }

    // returns the player Rect object
    public Rect get_playerRect(){
        return rect;
    }

    // this updates the player position based on the
    // the dx and dy value and does animations
    public void update(boolean actionUp){
        // does the player movements
        if (actionUp || offsetCounter > 0) {
            offsetCounter++;
            rect.offset(0, -14);

            if (offsetCounter == 10) {
                offsetCounter = 0;
            }
        }else if (offsetCounter == 0) {
            rect.offset(0, +10);
        }

        // keeps track of which frame the animation is on
        if (System.currentTimeMillis() - lastFrame > frameTime * 1000){
            framesIndex ++;
            if (framesIndex >= frames.length){
                framesIndex = 0;
            }
            lastFrame = System.currentTimeMillis();
        }
    }

    // sets the dx and dy values to and does
    // animations for the player
    public void update2(){
        rect.offset(0, 0);

        if (System.currentTimeMillis() - lastFrame > frameTime * 1000){
            framesIndex ++;
            if (framesIndex >= frames.length){
                framesIndex = 0;
            }
            lastFrame = System.currentTimeMillis();
        }
    }

    // draws the player sprite onto the canvas
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(colour);

        canvas.drawBitmap(frames[framesIndex], null, rect, new Paint());
    }
}

