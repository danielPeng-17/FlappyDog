package com.example.flappydog;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import static com.example.flappydog.Constants.CURRENT_CONTEXT;

/**
 * @author Daniel Peng
 * description: This DisplayTextObject is an class that contains methods to draws text
 *              onto the canvas. The constructor takes a string message, an (x, y)
 *              coordinates, and text size.
 */

public class DisplayTextObject {
    private int colour;
    private String string;
    private float x;
    private float y;
    private int textSize;
    private Typeface tf;
    private long messageTime;

    public DisplayTextObject(String string, int x, int y, int textSize){
        this.string = string;
        this.x = (float) x;
        this.y = (float) y;
        this.textSize = textSize;
        colour = Color.rgb(204,102,153);

        // set font style
        tf = Typeface.createFromAsset(CURRENT_CONTEXT.getAssets(), "fonts/flappyfont.TTF");
    }

    // start the timer
    public void startTime(){
        messageTime = System.currentTimeMillis();
    }

    // return timer value
    public long get_time(){
        return messageTime;
    }

    // updates string value if needed
    public void update(String string){
        this.string = string;
    }

    // overloaded version of update, this overloaded version
    // takes in a string value, a new (x, y) coordinate and a
    // new text size
    public void update(String string, int x, int y, int textSize){
        this.string = string;
        this.x = x;
        this.y = y;
        this.textSize = textSize;
    }

    // draws text onto the canvas. Takes in
    // a canvas object.
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(colour);
        paint.setTextSize(textSize);
        paint.setTypeface(tf);

        canvas.drawText(string, x, y, paint);
    }
}
