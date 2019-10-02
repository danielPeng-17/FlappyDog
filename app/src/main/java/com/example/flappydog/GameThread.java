package com.example.flappydog;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * @author Daniel Peng
 * description: This is the game thread (main game loop). It keeps track of fps and runs the important
 *              functions such as update and draw from GamePanel. GameThread takes in
 *              a SurfaceHolder and a GamePanel.
 */
public class GameThread extends Thread {
    private final int fps = 40;
    private double avgFps;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    public static Canvas canvas;
    public boolean running;

    public GameThread(SurfaceHolder holder, GamePanel panel){
        super();
        surfaceHolder = holder;
        gamePanel = panel;
    }

    @Override
    public void run() {
        long startTime;
        long timeMills;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / fps;
        while (running) {
            startTime = System.nanoTime();
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    // runs main components of the game
                    this.gamePanel.makePipes();
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                    this.gamePanel.collision(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // checks fps
            timeMills = ((System.nanoTime() - startTime) / 1000000);
            waitTime = targetTime - timeMills;

            try {
                if (waitTime > 0) {
                    this.sleep(waitTime);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if (frameCount == fps) {
                avgFps = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println("AvgFps: " + avgFps);
            }
        }

    }

    // sets the game loop
    public void setRunning(boolean isRunning){
        running = isRunning;
        System.out.println("Running is " + running);
    }
}