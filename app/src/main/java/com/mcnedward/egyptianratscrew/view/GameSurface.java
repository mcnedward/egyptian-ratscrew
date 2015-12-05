package com.mcnedward.egyptianratscrew.view;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.mcnedward.egyptianratscrew.model.GameTable;
import com.mcnedward.egyptianratscrew.util.GameThread;

/**
 * Created by Edward on 12/5/2015.
 */
public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private static String TAG = "GameSurface";

    public static int WIDTH;
    public static int HEIGHT;

    private Context mContext;
    private GameTable mGameTable;
    private GameThread mGameThread;

    public GameSurface(Context context, GameTable gameTable) {
        super(context);
        mContext = context;
        mGameTable = gameTable;

        getHolder().addCallback(this);

        WIDTH = getWidth();
        HEIGHT = getHeight();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mGameThread == null) {
            mGameThread = new GameThread(this, mContext, mGameTable);
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
