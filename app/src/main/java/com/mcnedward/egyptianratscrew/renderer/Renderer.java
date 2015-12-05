package com.mcnedward.egyptianratscrew.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.mcnedward.egyptianratscrew.R;
import com.mcnedward.egyptianratscrew.model.Card;
import com.mcnedward.egyptianratscrew.model.GameTable;
import com.mcnedward.egyptianratscrew.model.Player;
import com.mcnedward.egyptianratscrew.view.GameSurface;

/**
 * Created by Edward on 12/5/2015.
 */
public class Renderer {

    private Context mContext;
    private GameTable mGameTable;
    private Paint mPaint;

    public Renderer(Context context, GameTable gameTable) {
        mContext = context;
        mGameTable = gameTable;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mContext.getResources().getColor(R.color.Black));
        mPaint.setTextSize(30);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void render(Canvas canvas) {
        drawBackground(canvas);
        drawFaceCards(canvas);
        drawText(canvas);

        if (mGameTable.isCardsDealt()) {
            for (Card card : mGameTable.getCards()) {
                card.draw(canvas);
            }
        }
    }

    private void drawBackground(Canvas canvas) {
        Drawable background = ContextCompat.getDrawable(mContext, R.drawable.border);
        background.setBounds(0, 0, GameSurface.WIDTH, GameSurface.HEIGHT);
        background.draw(canvas);
    }

    private void drawFaceCards(Canvas canvas) {
        if (mGameTable.isCardsDealt()) {
            Card playerCard = mGameTable.getPlayerCard();
            Card otherCard = mGameTable.getOtherCard();
            playerCard.setPosition(GameSurface.WIDTH - playerCard.getWidth() - 30, GameSurface.HEIGHT - playerCard.getHeight() - 30);
            otherCard.setPosition(30, 30);
            if (mGameTable.isPlayer1Turn()) {
                Rect cardBorder = playerCard.getCardBorder();
                mPaint.setColor(mContext.getResources().getColor(R.color.Yellow));
                canvas.drawRect(cardBorder, mPaint);
            }
            playerCard.draw(canvas);
            if (mGameTable.isPlayer2Turn()) {
                Rect cardBorder = otherCard.getCardBorder();
                mPaint.setColor(mContext.getResources().getColor(R.color.Yellow));
                canvas.drawRect(cardBorder, mPaint);
            }
            otherCard.draw(canvas);
        } else {
            Card faceCard = mGameTable.getFaceCard();
            faceCard.setPosition((GameSurface.WIDTH / 2) - (faceCard.getWidth() / 2), (GameSurface.HEIGHT / 2) - (faceCard.getHeight() / 2));
            mGameTable.getFaceCard().draw(canvas);
        }
    }

    private void drawText(Canvas canvas) {
        if (mGameTable.isCardsDealt()) {
            Player player1 = mGameTable.getPlayer1();
            Card player1Card = mGameTable.getPlayerCard();
            mPaint.setColor(mContext.getResources().getColor(R.color.Black));
            String player1Text = "Card Count: " + player1.getHand().size();
            int text1Width = (int) mPaint.measureText(player1Text);
            canvas.drawText(player1Text, (text1Width / 2) + 20, player1Card.getY() + player1Card.getHeight() - 10, mPaint);

            Player player2 = mGameTable.getPlayer2();
            Card player2Card = mGameTable.getOtherCard();
            mPaint.setColor(mContext.getResources().getColor(R.color.Black));
            String player2Text = "Card Count: " + player2.getHand().size();
            int text2Width = (int) mPaint.measureText(player2Text);
            int x = GameSurface.WIDTH - (text2Width / 2) - 20;
            int y = player2Card.getY() + 20;
            canvas.save();
            canvas.rotate(-180, x, y);
            canvas.drawText(player2Text, x, y, mPaint);
            canvas.restore();
        }
    }

}
