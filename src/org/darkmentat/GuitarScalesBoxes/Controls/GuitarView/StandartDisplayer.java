package org.darkmentat.GuitarScalesBoxes.Controls.GuitarView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import org.darkmentat.GuitarScalesBoxes.R;

class StandartDisplayer implements DisplayerFretBoard
{
    private final Bitmap mFretTexture;
    private final Bitmap mPegTexture1;
    private final Bitmap mPegTexture2;
    private final Bitmap mPegTexture3;
    private final Bitmap mPegTexture4;
    private final Bitmap mPegTexture5;
    private final Bitmap mPegTexture6;

    private final Paint mPaint = new Paint();

    private FretBoard mFretBoard;

    public StandartDisplayer(Context context) {
        mFretTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_fret);
        mPegTexture1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg01);
        mPegTexture2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg02);
        mPegTexture3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg03);
        mPegTexture4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg04);
        mPegTexture5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg05);
        mPegTexture6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg06);
    }

    @Override
    public void setFretBoard(FretBoard fretBoard) {
        mFretBoard = fretBoard;
    }

    @Override
    public int getWidth() {
        return mPegTexture2.getWidth() + mFretBoard.FretCount*mFretTexture.getWidth();
    }

    @Override
    public void draw(Canvas canvas) {
        if(mFretBoard == null) return;

        canvas.drawBitmap(mPegTexture1, 0, 0, mPaint);
        canvas.drawBitmap(mPegTexture2, 0, mPegTexture1.getHeight(), mPaint);
        canvas.drawBitmap(mPegTexture3, 0, mPegTexture1.getHeight() + mPegTexture2.getHeight(), mPaint);
        canvas.drawBitmap(mPegTexture4, 0, mPegTexture1.getHeight() + mPegTexture2.getHeight() + mPegTexture3.getHeight(), mPaint);

        for (int i = 0; i < mFretBoard.StringCount - 3; i++)
            canvas.drawBitmap(mPegTexture5, 0, mPegTexture1.getHeight() + mPegTexture2.getHeight() + mPegTexture3.getHeight() + mPegTexture4.getHeight() + i * mPegTexture5.getHeight(), mPaint);

        canvas.drawBitmap(mPegTexture6, 0, mPegTexture1.getHeight() + mPegTexture2.getHeight() + mPegTexture3.getHeight() + mPegTexture4.getHeight() + (mFretBoard.StringCount - 3) * mPegTexture5.getHeight(), mPaint);

        for (int y = 0; y < mFretBoard.StringCount; y++)
            for (int x = 0; x < mFretBoard.FretCount; x++)
                canvas.drawBitmap(mFretTexture, mPegTexture2.getWidth() + x * mFretTexture.getWidth(), mPegTexture1.getHeight() + y * mFretTexture.getHeight(), mPaint);
    }
}