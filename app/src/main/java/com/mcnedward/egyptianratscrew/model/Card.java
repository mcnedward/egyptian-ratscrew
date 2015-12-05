package com.mcnedward.egyptianratscrew.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import com.mcnedward.egyptianratscrew.view.GameSurface;

/**
 * Created by Edward on 12/5/2015.
 */
public class Card {
    private static String TAG = "Card";

    private Bitmap mCardBitmap;
    private Point mPosition;
    private String mCardName;
    private Matrix mMatrix;
    private Rect mBounds;

    public Card(Context context, String cardName, int cardId) {
        mCardBitmap = BitmapFactory.decodeResource(context.getResources(), cardId);
        mPosition = new Point(0, 0);
        mCardName = cardName;
        mMatrix = new Matrix();
        mBounds = new Rect(mPosition.x, mPosition.y, mCardBitmap.getWidth() + mPosition.x, mCardBitmap.getHeight() + mPosition.y);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(mCardBitmap, mPosition.x, mPosition.y, null);
    }

    public boolean isTouched(int x, int y) {
        return mBounds.contains(x, y);
    }

    public void setPosition(int x, int y) {
        mPosition.x = x;
        mPosition.y = y;
        mBounds.set(mPosition.x, mPosition.y, mCardBitmap.getWidth() + mPosition.x, mCardBitmap.getHeight() + mPosition.y);
    }

    public void rotateCard(float degrees) {
        mMatrix.reset();
        int centerX = mCardBitmap.getWidth() / 2;
        int centerY = mCardBitmap.getHeight() / 2;
        mMatrix.setRotate(degrees, centerX, centerY);
        mCardBitmap = Bitmap.createBitmap(mCardBitmap, 0, 0, mCardBitmap.getWidth(), mCardBitmap.getHeight(), mMatrix, true);
        // Update new center position using the old center positions
        int newCenterX = centerX - (mCardBitmap.getWidth() / 2) + (GameSurface.WIDTH / 2);
        int newCenterY = centerY - (mCardBitmap.getHeight() / 2) + (GameSurface.HEIGHT / 2);
        setPosition(newCenterX - centerX, newCenterY - centerY);
    }

    public Rect getCardBorder() {
        Rect cardBounds = getBounds();
        cardBounds.set(cardBounds.left - 15, cardBounds.top - 15, cardBounds.right + 15, cardBounds.bottom + 15);
        return cardBounds;
    }

    public int getX() {
        return mPosition.x;
    }

    public int getY() {
        return mPosition.y;
    }

    public int getWidth() {
        return mCardBitmap.getWidth();
    }

    public int getHeight() {
        return mCardBitmap.getHeight();
    }

    public Bitmap getCardBitmap() {
        return mCardBitmap;
    }

    public String getCardName() {
        return mCardName;
    }

    public Rect getBounds() {
        return mBounds;
    }
}
