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
 * description: This GroundObject is a class that creates a ground object for the
 *              game. It uses BitmapFactory to apply the sprites to a Rect object.
 *              It contains a collision detection method, a update and a draw method.
 */
public class GroundObject {
    private Rect rect;
    private int colour;
    private BitmapFactory bf = new BitmapFactory();
    private Bitmap ground = bf.decodeResource(CURRENT_CONTEXT.getResources(), R.drawable.ground);

    public GroundObject(){
        colour = Color.rgb(100, 100, 100);
        rect = new Rect(0, (SCREEN_HEIGHT/15)* 13, SCREEN_WIDTH * 5, SCREEN_HEIGHT + 1);
    }

    // checks if the player Rect has collided with ground Rect
    public boolean playerCollideGround(Rect player){
        return (Rect.intersects(rect, player));
    }

    // updates the ground object according to the dx and dy value
    public void update(){
        if (rect.centerX() > -SCREEN_WIDTH) {
            rect.offset(-10, 0);
        }else{
            rect.offsetTo(-50, (SCREEN_HEIGHT/ 15) * 13);
        }
    }

    // sets the dx, dy to zero
    public void update2(){
        rect.offset(0, 0);
    }

    // draws the ground object to the canvas
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(colour);

        canvas.drawBitmap(ground, null, rect, paint);
    }
}
