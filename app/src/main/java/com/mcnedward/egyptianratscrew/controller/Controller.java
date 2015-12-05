package com.mcnedward.egyptianratscrew.controller;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import com.mcnedward.egyptianratscrew.model.GameTable;

/**
 * Created by Edward on 12/5/2015.
 */
public class Controller {

    private Context mContext;
    private GameTable mGameTable;

    private boolean touched;
    private Point touchPosition;

    public Controller(Context context, GameTable gameTable) {
        mContext = context;
        mGameTable = gameTable;
        touched = false;
        touchPosition = new Point();
    }

    public void update() {
        // TODO This needs to be changed
        if (touched) {
            if (mGameTable.isCardsDealt()) {
                // If game is started
                if (mGameTable.isPlayer1Turn() && mGameTable.getPlayerCard().isTouched(touchPosition.x, touchPosition.y))
                    mGameTable.dealCard();
                if (mGameTable.isPlayer2Turn() && mGameTable.getOtherCard().isTouched(touchPosition.x, touchPosition.y))
                    mGameTable.dealCard();
            } else {
                // If game is not started yet, deal cards when player taps middle card
                if (mGameTable.getFaceCard().isTouched(touchPosition.x, touchPosition.y)) {
                    mGameTable.dealCards();
                }
            }
            touched = false;
        }
    }

    public boolean handleTouch(View view, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touched = false;
            touchPosition.x = x;
            touchPosition.y = y;
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            touchPosition.x = x;
            touchPosition.y = y;
            touched = true;
            return true;
        }
        return false;
    }

}
