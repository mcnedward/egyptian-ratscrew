package com.mcnedward.egyptianratscrew.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 12/5/2015.
 */
public class Player {

    private String mPlayerName;
    private List<Card> mHand;
    private boolean mTurn;

    public Player(String playerName) {
        mPlayerName = playerName;
        mHand = new ArrayList<>();
        mTurn = false;
    }

    public void addCards(List<Card> cards) {
        mHand.addAll(cards);
    }

    public String getPlayerName() {
        return mPlayerName;
    }

    public List<Card> getHand() {
        return mHand;
    }

    public boolean isTurn() {
        return mTurn;
    }

    public void setTurn(boolean turn) {
        mTurn = turn;
    }
}
