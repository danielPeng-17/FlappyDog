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
 * description: This class creates a simple start menu for the beginning of
 *              the game. It uses BitmapFactory to apply the sprites onto the
 *              canvas.
 */

public class StartMenuObject {
    private Rect playRect;
    private Rect gameTitleRect;
    private int colour;

    private BitmapFactory bf = new BitmapFactory();
    private Bitmap playButton = bf.decodeResource(CURRENT_CONTEXT.getResources(), R.drawable.play_button);
    private Bitmap title = bf.decodeResource(CURRENT_CONTEXT.getResources(), R.drawable.title);

    public StartMenuObject() {
        gameTitleRect = new Rect(100, (SCREEN_HEIGHT/3) - 100, SCREEN_WIDTH - 150, (SCREEN_HEIGHT/3) + 120);
        playRect = new Rect((SCREEN_WIDTH/2) - 100, (SCREEN_HEIGHT/2) + 300,(SCREEN_WIDTH/2) + 100, (SCREEN_HEIGHT/2) + 450);
        colour = Color.rgb(100, 100, 100);
    }

    // checks if the user clicked on the start button
    public boolean playButtonCollided(int mouseX, int mouseY){
        return playRect.contains(mouseX, mouseY);
    }

    // draws the title sprite and start button sprite onto the canvas
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(colour);

        canvas.drawBitmap(playButton, null, playRect, new Paint());
        canvas.drawBitmap(title, null, gameTitleRect, new Paint());

    }

}
