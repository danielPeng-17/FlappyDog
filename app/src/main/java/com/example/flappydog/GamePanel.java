package com.example.flappydog;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;
import java.util.ArrayList;

import static com.example.flappydog.Constants.SCREEN_HEIGHT;
import static com.example.flappydog.Constants.SCREEN_WIDTH;

/**
 * @author Daniel Peng
 * description: This is the game panel. This is responsible for running the
 *              little details of the game such as checking for collision,
 *              updating and drawing the sprites onto the canvas.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private boolean isGameOver;
    private GameThread thread;
    private PlayerObject player;
    private GroundObject ground;
    private StartMenuObject startMenu;
    private DisplayTextObject scoreText;
    private DisplayTextObject gameOverText;
    private BackgroundClass background;

    private boolean startMenuOver = false;
    private int score = 0;
    private boolean collided = false;
    private boolean actionUp = false;
    private ArrayList<ObstacleObject> pipesList = new ArrayList<>();
    private  int totalPipeListSize = 0;

    public GamePanel(Context context){
        super(context);

        // set the callback to the surfaceView so that events can be triggered from here
        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT = context;
        thread = new GameThread(getHolder(),this);

        reset();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread.setRunning(true);
        thread.start();
    }

    // closes the game loop
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(Exception e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    // get touch motion from user
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case (MotionEvent.ACTION_DOWN):
            case (MotionEvent.ACTION_UP):
                if(!startMenuOver){
                    if (startMenu.playButtonCollided((int)event.getX(), (int)event.getY())){
                        startMenuOver = true;
                        // return false to not detect touch
                        return false;
                    }
                }else {
                    actionUp = true;
                }
        }
        // return true to detect touch
        return true;
    }

    // checks collision
    public void collision(Canvas canvas){
        if (!collided) {
            // checks if player collide with the top of the screen
            if (player.playerCollideTop() || ground.playerCollideGround(player.get_playerRect())) {
                collided = true;
                gameOverText.startTime();
            } else {
                // checks collision between the player and the pipes
                for (int x = 0; x < pipesList.size(); x++) {
                    if (pipesList.get(x).playerCollideObstacle(player.get_playerRect())) {
                        collided = true;
                        gameOverText.startTime();
                    }
                }
            }
        }else{
            // displays game over screen and final score
            isGameOver = true;
            draw(canvas);
            if (System.currentTimeMillis() - gameOverText.get_time() > 3000){
                reset();
            }
        }

    }

    // resets all the variables when called
    private void reset() {
        pipesList = new ArrayList<>();
        score = 0;
        isGameOver = false;
        startMenuOver = false;
        collided = false;
        actionUp = false;
        totalPipeListSize = 0;

        background = new BackgroundClass();
        player = new PlayerObject();
        ground = new GroundObject();
        startMenu = new StartMenuObject();
        scoreText = new DisplayTextObject(String.valueOf(score), SCREEN_WIDTH/2 - 40, SCREEN_HEIGHT/6,120);
        gameOverText = new DisplayTextObject("GAME OVER", SCREEN_WIDTH/2 - 270, (SCREEN_HEIGHT/4) + 200, 100);
        pipesList.add(new ObstacleObject());
    }

    // makes all the pipes
    public void makePipes(){
        if (pipesList.size() < 3) {
            if (pipesList.get(pipesList.size() - 1).get_centerX() < SCREEN_WIDTH / 2) {
                totalPipeListSize++;
                pipesList.add(new ObstacleObject());
            }
        }
    }

    // calls update method from each object
    // and updates all the sprites position
    public void update(){
        if (!isGameOver && startMenuOver) {
            background.update();
            ground.update();
        }else {
            background.update2();
            ground.update2();
        }

        if (!isGameOver && startMenuOver) {
            player.update(actionUp);
            actionUp = false;
        }else {
            player.update2();
        }

        for (int x = 0; x < pipesList.size(); x++){
            if (pipesList.get(x).isLeft()) {
                pipesList.remove(x);
            }

            if (pipesList.get(x).get_centerX() < player.get_playerRect().centerX()){
                score = totalPipeListSize;
            }
            if (!isGameOver && startMenuOver) {
                pipesList.get(x).update();
            }else {
                pipesList.get(x).update2();
            }
        }

        if (!isGameOver && startMenuOver) {
            scoreText.update(String.valueOf(score));
        }else if (isGameOver) {
            scoreText.update("SCORE: " + String.valueOf(score), SCREEN_WIDTH/2 - 180, (SCREEN_HEIGHT/6) * 4 - 100, 80);
        }
    }

    // calls the draw method from each object
    // and draws the sprites onto the canvas
    @Override
    public void draw(Canvas canvas){
        // draws stuff on the canvas
        super.draw(canvas);

        if (!isGameOver || !startMenuOver) {
            background.draw(canvas);
            player.draw(canvas);

            for (int x = 0; x < pipesList.size(); x++) {
                pipesList.get(x).draw(canvas);
            }

            scoreText.draw(canvas);
            ground.draw(canvas);
        }else {
            scoreText.draw(canvas);
            gameOverText.draw(canvas);
        }

        if (!startMenuOver) {
            startMenu.draw(canvas);
        }
    }
}
