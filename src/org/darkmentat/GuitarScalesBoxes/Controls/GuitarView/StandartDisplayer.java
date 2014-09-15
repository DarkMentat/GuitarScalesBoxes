package org.darkmentat.GuitarScalesBoxes.Controls.GuitarView;

import android.content.Context;
import android.graphics.*;
import org.darkmentat.GuitarScalesBoxes.R;

import static org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.FretBoard.Note;

class StandartDisplayer implements DisplayerFretBoard
{
    private final Bitmap mFretTexture;
    private final Bitmap mPegTexture1;
    private final Bitmap mPegTexture2;
    private final Bitmap mPegTexture3;
    private final Bitmap mPegTexture4;
    private final Bitmap mPegTexture5;
    private final Bitmap mPegTexture6;

    private final int mFretWidth;
    private final int mFretHeight;

    private final Paint mPaint = new Paint();
    private final Paint mCircleOnScaleNote = new Paint(){{setColor(Color.argb(125,200,0,0));}};
    private final Paint mCircleOnTonicNote = new Paint(){{setColor(Color.argb(125,0,0,200));}};
    private final Paint mCircleOnBoardNote = new Paint(){{setColor(Color.argb(50,255,255,0));}};
    private final Paint mTextOnScaleNote = new Paint(){{setColor(Color.argb(200,255,255,255)); setTextAlign(Align.CENTER); setTextSize(14);}};
    private final Paint mTextOnTonicNote = new Paint(){{setColor(Color.argb(200,255,255,100)); setTextAlign(Align.CENTER); setTextSize(14);}};
    private final Paint mTextOnBoardNote = new Paint(){{setColor(Color.argb(100,0,0,0)); setTextAlign(Align.CENTER); setTextSize(14);}};

    private FretBoard mFretBoard;

    public StandartDisplayer(Context context) {
        mFretTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_fret);
        mPegTexture1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg01);
        mPegTexture2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg02);
        mPegTexture3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg03);
        mPegTexture4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg04);
        mPegTexture5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg05);
        mPegTexture6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg06);

        mFretWidth = mFretTexture.getWidth();
        mFretHeight = mFretTexture.getHeight();
    }

    @Override
    public void setFretBoard(FretBoard fretBoard) {
        mFretBoard = fretBoard;
    }

    @Override
    public int getWidth() {
        return mPegTexture2.getWidth() + mFretBoard.FretCount * mFretWidth;
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

        //todo draw 0-fret on peg
        for (int x = 0; x < mFretBoard.FretCount; x++)
            for (int y = 0; y < mFretBoard.StringCount; y++)
            {
                canvas.drawBitmap(mFretTexture, mPegTexture2.getWidth() + x * mFretWidth, mPegTexture1.getHeight() + y * mFretHeight, mPaint);
                drawNote(canvas, mFretBoard.Tab[x][y], mPegTexture2.getWidth() + x * mFretWidth, mPegTexture1.getHeight() + y * mFretHeight);
            }
    }

    private void drawNote(Canvas canvas, Note note, float x, float y){
        switch (note.Quality)
        {
            case OnBoard:
                drawOnBoardNote(canvas, note, x, y);
                break;
            case OnScale:
                drawOnScaleNote(canvas, note, x, y);
                break;
            case OnTonic:
                drawOnTonicNote(canvas, note, x, y);
                break;
            case OnScaleOnBox:
                drawOnScaleOnBoxNote(canvas, note, x, y);
                break;
            case OnTonicOnBox:
                drawOnTonicOnBoxNote(canvas, note, x, y);
                break;
        }

    }
    private void drawOnBoardNote(Canvas canvas, Note note, float x, float y){
        canvas.drawCircle(x+mFretWidth/2,y+mFretHeight/2,mFretHeight/2.75f,mCircleOnBoardNote);
        canvas.drawText(note.DisplayName, x+mFretWidth/2, y+mFretHeight/2+mFretHeight/5.5f, mTextOnBoardNote);
    }
    private void drawOnScaleNote(Canvas canvas, Note note, float x, float y){
        canvas.drawCircle(x+mFretWidth/2,y+mFretHeight/2,mFretHeight/2.75f, mCircleOnScaleNote);
        canvas.drawText(note.DisplayName, x+mFretWidth/2, y+mFretHeight/2+mFretHeight/5.5f, mTextOnScaleNote);
    }
    private void drawOnTonicNote(Canvas canvas, Note note, float x, float y){
        canvas.drawCircle(x+mFretWidth/2,y+mFretHeight/2,mFretHeight/2.75f,mCircleOnTonicNote);
        canvas.drawText(note.DisplayName, x+mFretWidth/2, y+mFretHeight/2+mFretHeight/5.5f, mTextOnTonicNote);
    }
    private void drawOnScaleOnBoxNote(Canvas canvas, Note note, float x, float y){
        //todo
    }
    private void drawOnTonicOnBoxNote(Canvas canvas, Note note, float x, float y){
        //todo
    }
}