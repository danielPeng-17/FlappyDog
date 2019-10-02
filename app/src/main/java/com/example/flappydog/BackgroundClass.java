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
 * description: This background class just applies a background image to the canvas
 *              using BitmapFactory.
 */

public class BackgroundClass {
    private Rect rect;
    private int colour;

    private BitmapFactory bf = new BitmapFactory();
    private Bitmap background;

    public BackgroundClass(){
        colour = Color.rgb(100, 100, 100);
        rect = new Rect(0, 0, SCREEN_WIDTH*5, SCREEN_HEIGHT - 1);

        // random picks which background to use
        if ((int)(Math.random() * 2 + 1) == 1) {
            background = bf.decodeResource(CURRENT_CONTEXT.getResources(), R.drawable.background_two);
        }else{
            background = bf.decodeResource(CURRENT_CONTEXT.getResources(), R.drawable.background);
        }
    }

    // updates the dx and dy position of the background
    public void update(){
        if (rect.right - SCREEN_WIDTH > SCREEN_WIDTH) {
            rect.offset(-8, 0);
        }else{
            rect.offsetTo(0, 0);
        }
    }

    // sets the dx and dy value of the
    public void update2(){
        rect.offset(0, 0);
    }

    // draws background onto the canvas
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(colour);

        canvas.drawBitmap(background, null, rect, paint);
    }
}
