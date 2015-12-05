package com.mcnedward.egyptianratscrew.model;

import android.content.Context;
import android.util.Log;

import com.mcnedward.egyptianratscrew.R;
import com.mcnedward.egyptianratscrew.view.GameSurface;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Edward on 12/5/2015.
 */
public class GameTable {
    private static String TAG = "GameTable";

    private List<Card> mDeck;
    private List<Card> mCards;
    private Card mFaceCard;
    private Card mPlayerCard;
    private Card mOtherCard;

    private Player mPlayer1;
    private Player mPlayer2;

    private Random mRandom;
    private int cardIndex;
    private static int rotationDegrees;
    private boolean mCardsDealt = false;
    private boolean mFlungDown;
    private boolean mFlungUp;

    public GameTable(Context context) {
        mDeck = new ArrayList<>();
        mCards = new ArrayList<>();
        mRandom = new Random();

        mPlayer1 = new Player("Player 1");
        mPlayer2 = new Player("Player 2");

        initializeDeck(context);
    }

    public void dealCards() {
        shuffle();
        mCardsDealt = true;
        cardIndex = 0;
        mPlayer1.setTurn(true);
        dealCard(false);
    }

    public void dealCard() {
        dealCard(true);
    }

    public void dealCard(boolean switchTurns) {
        Card card = mDeck.get(cardIndex++);
        card.setPosition((GameSurface.WIDTH / 2) - (mFaceCard.getWidth() / 2), (GameSurface.HEIGHT / 2) - (mFaceCard.getHeight() / 2));
        card.rotateCard(rotationDegrees);
        rotationDegrees += 15;
        mCards.add(card);
        if (switchTurns)
            switchTurns();
    }

    public void addCardsToPlayer1() {
        addCardsToPlayer(mPlayer1);
    }

    public void addCardsToPlayer2() {
        addCardsToPlayer(mPlayer2);
    }

    /**
     * Add the cards in the middle pile to a player.
     *
     * @param player The player to add the middle pile to.
     */
    private void addCardsToPlayer(Player player) {
        player.addCards(mCards);
        mCards = new ArrayList<>();
        // Reset the rotation degree
        rotationDegrees = 0;
    }

    public void reset() {
        mPlayer1 = new Player("Player 1");
        mPlayer2 = new Player("Player 2");
        mCards = new ArrayList<>();
        mCardsDealt = false;
        rotationDegrees = 0;
    }

    private void switchTurns() {
        mPlayer1.setTurn(!mPlayer1.isTurn());
        mPlayer2.setTurn(!mPlayer2.isTurn());
    }

    private void shuffle() {
        int arrayLength = mDeck.size();
        while (arrayLength > 1) {
            int nextRandom = mRandom.nextInt(arrayLength--);
            Card card = mDeck.get(nextRandom);
            mDeck.set(nextRandom, mDeck.get(arrayLength));
            mDeck.set(arrayLength, card);
        }
    }

    private void initializeDeck(Context context) {
        // TODO This should be changed later to sprite sheet
        final R.drawable drawableResources = new R.drawable();
        Field[] drawables = R.drawable.class.getFields();
        for (Field f : drawables) {
            try {
                if (f.getName().equals("b2fv")) {
                    mFaceCard = new Card(context, f.getName(), f.getInt(drawableResources));
                    mPlayerCard = new Card(context, f.getName(), f.getInt(drawableResources));
                    mOtherCard = new Card(context, f.getName(), f.getInt(drawableResources));
                }
                if (f.getName().length() <= 3) {
                    mDeck.add(new Card(context, f.getName(), f.getInt(drawableResources)));
                }
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public Card getCardByName(String cardName) {
        for (Card card : mDeck) {
            if (card.getCardName().equals(cardName))
                return card;
        }
        return null;
    }

    public List<Card> getCards() {
        return mCards;
    }

    public Card getFaceCard() {
        return mFaceCard;
    }

    public Card getPlayerCard() {
        return mPlayerCard;
    }

    public Card getOtherCard() {
        return mOtherCard;
    }

    public Player getPlayer1() {
        return mPlayer1;
    }

    public Player getPlayer2() {
        return mPlayer2;
    }

    public boolean isPlayer1Turn() {
        return mPlayer1.isTurn();
    }

    public boolean isPlayer2Turn() {
        return mPlayer2.isTurn();
    }

    public boolean isCardsDealt() {
        return mCardsDealt;
    }

    public void setCardsDealt(boolean cardsDealt) {
        this.mCardsDealt = cardsDealt;
    }

    public boolean isFlungUp() {
        return mFlungUp;
    }

    public void setFlungUp(boolean flungUp) {
        this.mFlungUp = flungUp;
    }

    public boolean isFlungDown() {
        return mFlungDown;
    }

    public void setFlungDown(boolean flungDown) {
        this.mFlungDown = flungDown;
    }
}
