package com.mcnedward.egyptianratscrew.util;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.mcnedward.egyptianratscrew.model.GameTable;

/**
 * Created by Edward on 12/5/2015.
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener {
    private static String TAG = "GestureListener";

    private static final int SWIPE_MIN_DISTANCE = 150;
    private static final int SWIPE_THRESHOLD_VELOCITY = 250;

    private GameTable mGameTable;

    public GestureListener(GameTable gameTable) {
        mGameTable = gameTable;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return super.onSingleTapConfirmed(e);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            // Swipe Down
            if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Log.i(TAG, "CARD SPACE SWIPED DOWN");
                mGameTable.setFlungDown(true);
                return true;
            }
            // Swipe Up
            else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Log.i(TAG, "CARD SPACE SWIPED UP");
                mGameTable.setFlungUp(true);
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }
}
