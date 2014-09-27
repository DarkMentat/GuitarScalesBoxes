package org.darkmentat.GuitarScalesBoxes.Controls.GuitarView;

import android.content.Context;
import android.graphics.*;
import org.darkmentat.GuitarScalesBoxes.R;

import java.util.BitSet;

import static org.darkmentat.GuitarScalesBoxes.Controls.GuitarView.FretBoard.Note;

class StandartDisplayer implements DisplayerFretBoard
{
    private float mScaleCoef = 1.0f;

    private final Bitmap mFretTexture;
    private final Bitmap mPegTexture1;
    private final Bitmap mPegTexture2;
    private final Bitmap mPegTexture3;
    private final Bitmap mPegTexture4;
    private final Bitmap mPegTexture5;
    private final Bitmap mPegTexture6;
    private Bitmap mActualFretTexture;

    private int mActualFretWidth;
    private int mActualFretHeight;

    private BitSet mSelectedFrets;

    private final Paint mPaint = new Paint(){{setFlags(FILTER_BITMAP_FLAG); }};
    private final Paint mCircleOnScaleNote = new Paint(){{setColor(Color.argb(255,87,167,92)); setAntiAlias(true);}};
    private final Paint mCircleOnTonicNote = new Paint(){{setColor(Color.argb(255,12,128,0)); setAntiAlias(true);}};
    private final Paint mCircleOnBoardNote = new Paint(){{setColor(Color.argb(255,255,240,168)); setAntiAlias(true);}};
    private final Paint mTextOnScaleNote = new Paint(){{setColor(Color.argb(255,255,245,194)); setTextAlign(Align.CENTER); setTextSize(14); setAntiAlias(true);}};
    private final Paint mTextOnTonicNote = new Paint(){{setColor(Color.argb(255,255,245,194)); setTextAlign(Align.CENTER); setTextSize(14); setAntiAlias(true);}};
    private final Paint mTextOnBoardNote = new Paint(){{setColor(Color.argb(255,166,145,47)); setTextAlign(Align.CENTER); setTextSize(14); setAntiAlias(true);}};
    private final Paint mTextFretNum = new Paint(){{setColor(Color.argb(255,255,236,145)); setTextAlign(Align.CENTER); setTextSize(20); setAntiAlias(true);}};

    private Bitmap mCachedScreen;
    private boolean mCachedScreenNeedsUpdate = true;

    private FretBoard mFretBoard;
    private int mScreenWidth;
    private int mScreenHeight;

    public StandartDisplayer(Context context) {
        mFretTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_fret);
        mPegTexture1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg01);
        mPegTexture2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg02);
        mPegTexture3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg03);
        mPegTexture4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg04);
        mPegTexture5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg05);
        mPegTexture6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gv_peg06);

        mActualFretTexture = mFretTexture;
        mActualFretWidth = mActualFretTexture.getWidth();
        mActualFretHeight = mActualFretTexture.getHeight();
    }

    @Override public void update() {
        mCachedScreenNeedsUpdate = true;
    }
    @Override public void setFretBoard(FretBoard fretBoard) {
        mFretBoard = fretBoard;
        mSelectedFrets = new BitSet(fretBoard.FretCount);
    }

    private void updateScaleCoef() {
        mScaleCoef = 1.0f;
        mScaleCoef = mScreenHeight / (float) getHeight();
    }
    @Override public void setScreenSize(int width, int height) {
        mScreenWidth = width;
        mScreenHeight = height;
        updateScaleCoef();
    }
    @Override public void setMinFretCountOnScreen(int frets){
        int width = (int) (mScreenWidth/(mScaleCoef*frets));
        int crop = mFretTexture.getWidth()-width;

        if(width > mFretTexture.getWidth())
        {
            width = mFretTexture.getWidth();
            crop = 0;
        }

        mActualFretTexture = Bitmap.createBitmap(mFretTexture,crop,0, width,mFretTexture.getHeight());

        mActualFretWidth = mActualFretTexture.getWidth();
        mActualFretHeight = mActualFretTexture.getHeight();
    }

    @Override public void selectFret(int fret) {
        mSelectedFrets.set(fret);
    }
    @Override public void selectFretAtPoint(float point) {
        selectFret(getFretAtPoint(point));
    }
    @Override public void unSelectAll() {
        mSelectedFrets.clear();
    }

    @Override public int getFretAtPoint(float point) {
        float fret = mActualFretWidth*mScaleCoef;
        int res = (int) (point/fret - (mPegTexture2.getWidth()-mActualFretWidth)*mScaleCoef/fret);
        return res < 0 ? 0 : res;
    }
    @Override public float getPointOfFret(int fret) {
        return (mPegTexture2.getWidth() + (fret-1)*mActualFretWidth)*mScaleCoef;
    }

    @Override public int getWidth() {
        return (int) (mScaleCoef *(mPegTexture2.getWidth() + (mFretBoard.FretCount-1) * mActualFretWidth));
    }
    @Override public int getHeight() {
        return (int) (mScaleCoef *(mPegTexture1.getHeight() + mFretBoard.StringCount * mActualFretHeight + 4));
    }

    private void updateCachedScreen() {
        updateScaleCoef();
        mCachedScreen = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(mCachedScreen);
        drawStatic(c);
        mCachedScreenNeedsUpdate = false;
    }
    private void drawStatic(Canvas canvas){
        canvas.save();
        if(mScaleCoef > 1.75f)
            mScaleCoef = 1.75f;

        canvas.scale(mScaleCoef,mScaleCoef);

        canvas.drawBitmap(mPegTexture1, 0, 0, mPaint);
        canvas.drawBitmap(mPegTexture2, 0, mPegTexture1.getHeight(), mPaint);
        canvas.drawBitmap(mPegTexture3, 0, mPegTexture1.getHeight() + mPegTexture2.getHeight(), mPaint);
        canvas.drawBitmap(mPegTexture4, 0, mPegTexture1.getHeight() + mPegTexture2.getHeight() + mPegTexture3.getHeight(), mPaint);

        for (int i = 0; i < mFretBoard.StringCount - 3; i++)
            canvas.drawBitmap(mPegTexture5, 0, mPegTexture1.getHeight() + mPegTexture2.getHeight() + mPegTexture3.getHeight() + mPegTexture4.getHeight() + i * mPegTexture5.getHeight(), mPaint);

        canvas.drawBitmap(mPegTexture6, 0, mPegTexture1.getHeight() + mPegTexture2.getHeight() + mPegTexture3.getHeight() + mPegTexture4.getHeight() + (mFretBoard.StringCount - 3) * mPegTexture5.getHeight(), mPaint);

        for (int x = 0; x < mFretBoard.FretCount; x++)
        {
            if(x == 1)
                canvas.drawText("I",mPegTexture2.getWidth() + (x-0.5f) * mActualFretWidth, mPegTexture1.getHeight()-2, mTextFretNum);
            if(x == 3)
                canvas.drawText("III",mPegTexture2.getWidth() + (x-0.5f) * mActualFretWidth, mPegTexture1.getHeight()-2, mTextFretNum);
            if(x == 5)
                canvas.drawText("V",mPegTexture2.getWidth() + (x-0.5f) * mActualFretWidth, mPegTexture1.getHeight()-2, mTextFretNum);
            if(x == 7)
                canvas.drawText("VII",mPegTexture2.getWidth() + (x-0.5f) * mActualFretWidth, mPegTexture1.getHeight()-2, mTextFretNum);
            if(x == 9)
                canvas.drawText("IX",mPegTexture2.getWidth() + (x-0.5f) * mActualFretWidth, mPegTexture1.getHeight()-2, mTextFretNum);
            if(x == 12)
                canvas.drawText("XII",mPegTexture2.getWidth() + (x-0.5f) * mActualFretWidth, mPegTexture1.getHeight()-2, mTextFretNum);
            if(x == 15)
                canvas.drawText("XV",mPegTexture2.getWidth() + (x-0.5f) * mActualFretWidth, mPegTexture1.getHeight()-2, mTextFretNum);
            if(x == 17)
                canvas.drawText("XVII",mPegTexture2.getWidth() + (x-0.5f) * mActualFretWidth, mPegTexture1.getHeight()-2, mTextFretNum);
            if(x == 19)
                canvas.drawText("XIX",mPegTexture2.getWidth() + (x-0.5f) * mActualFretWidth, mPegTexture1.getHeight()-2, mTextFretNum);
            if(x == 22)
                canvas.drawText("XXII",mPegTexture2.getWidth() + (x-0.5f) * mActualFretWidth, mPegTexture1.getHeight()-2, mTextFretNum);

            if(mSelectedFrets.get(x))
                canvas.drawText("S",mPegTexture2.getWidth() + (x-0.5f) * mActualFretWidth, mPegTexture1.getHeight()-18, mTextFretNum);

            for (int y = 0; y < mFretBoard.StringCount; y++)
            {
                if(x != mFretBoard.FretCount-1)
                    canvas.drawBitmap(mActualFretTexture, mPegTexture2.getWidth() + x * mActualFretWidth, mPegTexture1.getHeight() + y * mActualFretHeight, mPaint);
                drawNote(canvas, mFretBoard.Tab[x][y], mPegTexture2.getWidth() + (x-1) * mActualFretWidth, mPegTexture1.getHeight() + y * mActualFretHeight);
            }
        }
        canvas.restore();
    }
    @Override public void draw(Canvas canvas) {
        if(mFretBoard == null) return;

        if(mCachedScreen != null && mCachedScreen.getWidth() > 4096) //fuck
            drawStatic(canvas);
        else
        {
            if (mCachedScreenNeedsUpdate)
                updateCachedScreen();
            canvas.drawBitmap(mCachedScreen, 0, 0, mPaint);
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
        canvas.drawCircle(x+ mActualFretWidth /2,y+ mActualFretHeight /2, mActualFretHeight /2.75f,mCircleOnBoardNote);
        canvas.drawText(note.DisplayName, x+ mActualFretWidth /2, y+ mActualFretHeight /2+ mActualFretHeight /5.5f, mTextOnBoardNote);
    }
    private void drawOnScaleNote(Canvas canvas, Note note, float x, float y){
        canvas.drawCircle(x+ mActualFretWidth /2,y+ mActualFretHeight /2, mActualFretHeight /2.75f, mCircleOnScaleNote);
        canvas.drawText(note.DisplayName, x+ mActualFretWidth /2, y+ mActualFretHeight /2+ mActualFretHeight /5.5f, mTextOnScaleNote);
    }
    private void drawOnTonicNote(Canvas canvas, Note note, float x, float y){
        canvas.drawCircle(x+ mActualFretWidth /2,y+ mActualFretHeight /2, mActualFretHeight /2.75f,mCircleOnTonicNote);
        canvas.drawText(note.DisplayName, x+ mActualFretWidth /2, y+ mActualFretHeight /2+ mActualFretHeight /5.5f, mTextOnTonicNote);
    }
    private void drawOnScaleOnBoxNote(Canvas canvas, Note note, float x, float y){
        //todo
    }
    private void drawOnTonicOnBoxNote(Canvas canvas, Note note, float x, float y){
        //todo
    }
}