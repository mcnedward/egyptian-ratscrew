package com.mcnedward.egyptianratscrew.util;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import com.mcnedward.egyptianratscrew.controller.Controller;
import com.mcnedward.egyptianratscrew.model.GameTable;
import com.mcnedward.egyptianratscrew.renderer.Renderer;
import com.mcnedward.egyptianratscrew.view.GameSurface;

/**
 * Created by Edward on 12/5/2015.
 */
public class GameThread extends Thread {
    private static String TAG = "GameThread";

    private final static int SLEEP_TIME = 40;

    private GameSurface mGameSurface;
    private SurfaceHolder mSurfaceHolder;

    private Controller controller;
    private Renderer renderer;

    private boolean running = false;

    public GameThread(GameSurface gameSurface, Context context, GameTable gameTable) {
        super();
        mGameSurface = gameSurface;
        mSurfaceHolder = gameSurface.getHolder();

        controller = new Controller(context, gameTable);
        renderer = new Renderer(context, gameTable);

        final GestureDetector gestureDetector = new GestureDetector(context, new GestureListener(gameTable));
        gameSurface.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return controller.handleTouch(v, event);
            }
        });
    }

    public void startGame() {
        running = true;
        super.start();
    }

    public void stopGame() {
        running = false;
    }

    @Override
    public void run() {
        Canvas canvas;
        while (running) {
            canvas = null;
            try {
                canvas = mSurfaceHolder.lockCanvas();
                synchronized (mSurfaceHolder) {
                    if (canvas != null) {
                        // Update and render
                        controller.update();
                        renderer.render(canvas);
                    }
                }
                sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                Log.e(TAG, "Interruption!\n" + e.getMessage());
            } finally {
                if (canvas != null) {
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

}
