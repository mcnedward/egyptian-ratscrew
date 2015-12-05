package com.mcnedward.egyptianratscrew.view;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.mcnedward.egyptianratscrew.util.GameThread;

/**
 * Created by Edward on 12/5/2015.
 */
public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private static String TAG = "GameSurface";

    public static int WIDTH;
    public static int HEIGHT;

    private Context mContext;
    private GameThread mGameThread;

    public GameSurface(Context context) {
        super(context);
        mContext = context;

        getHolder().addCallback(this);

        WIDTH = getWidth();
        HEIGHT = getHeight();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mGameThread == null) {
            mGameThread = new GameThread(this, mContext);
            mGameThread.startGame();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        WIDTH = width;
        HEIGHT = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mGameThread != null) {
            mGameThread.stopGame();
            boolean retry = false;
            while (retry) {
                try {
                    mGameThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    Log.e(TAG, "Interrupted in GameSurface!\n" + e.getMessage());
                }
            }
            mGameThread = null;
        }
    }
}
