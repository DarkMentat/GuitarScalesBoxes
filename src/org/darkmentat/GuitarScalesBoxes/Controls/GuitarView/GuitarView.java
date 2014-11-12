package org.darkmentat.GuitarScalesBoxes.Controls.GuitarView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;
import org.darkmentat.GuitarScalesBoxes.R;

import java.util.Observable;
import java.util.Observer;

import static android.view.GestureDetector.OnGestureListener;

public class GuitarView extends View implements OnGestureListener, Observer
{
    private DisplayerFretBoard mDisplayer = new StandartDisplayer(getContext());

    private int mMinFretCountOnScreen;
    private int mOffset = 0;
    private final OverScroller mScroller = new OverScroller(getContext());
    private final GestureDetector mGestureDetector = new GestureDetector(getContext(), this);
    private boolean mFirstSelection = true;
    private boolean mFretSelected = false;
    private int mSelectedFret;
    private OnFretIntervalSelectedListener mFretIntervalSelectedListener;

    public GuitarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(true);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.GuitarView);
        setMinFretCountOnScreen(a.getInt(R.styleable.GuitarView_minFretsOnScreen, 0));
        a.recycle();
    }

    @Override public void update(Observable observable, Object data) {
        mDisplayer.update();
        invalidate();
    }
    public void setFretBoard(FretBoard fretBoard) {
        fretBoard.addObserver(this);
        mDisplayer.setFretBoard(fretBoard);
        invalidate();
    }
    public void setMinFretCountOnScreen(int frets){
        mMinFretCountOnScreen = frets;
        if (getMeasuredWidth() > 0)
            mDisplayer.setMinFretCountOnScreen(mMinFretCountOnScreen);
        invalidate();
    }
    public void setOffsetFret(int fret){
        if(fret < 0)
            mOffset = 0;
        else
            mOffset = (int) mDisplayer.getPointOfFret(fret);
    }
    public void setOffsetFret(int startFret, int endFret){
        mOffset = (int) ((mDisplayer.getPointOfFret(startFret) + mDisplayer.getPointOfFret(endFret+1) - getMeasuredWidth()) * 0.5f);
        if(mOffset < 0)
            mOffset = 0;
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mDisplayer.setScreenSize(getMeasuredWidth(), getMeasuredHeight());
        mDisplayer.setMinFretCountOnScreen(mMinFretCountOnScreen);
    }

    @Override protected int computeHorizontalScrollRange() {
        return (int) mDisplayer.getWidth();
    }
    @Override protected int computeHorizontalScrollOffset() {
        return mOffset;
    }
    @Override protected int computeHorizontalScrollExtent() {
        return getMeasuredWidth();
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override public void onShowPress(MotionEvent e) {

    }
    @Override public boolean onDown(MotionEvent e) {
        mScroller.forceFinished(true);
        return true;
    }
    @Override public boolean onSingleTapUp(MotionEvent e) {
        if(mFirstSelection)
        {
            if(mFretSelected)
            {
                mDisplayer.unSelectAll();
                mFretSelected = false;
            }
            else
            {
                mDisplayer.unSelectAll();
                mSelectedFret = mDisplayer.getFretAtPoint(e.getX() + mOffset);
                mDisplayer.selectFret(mSelectedFret);
                mFretSelected = true;
                mFirstSelection = false;
            }
        }
        else
        {
            int selectedFret = mDisplayer.getFretAtPoint(e.getX() + mOffset);
            int startFret = mSelectedFret < selectedFret ? mSelectedFret : selectedFret;
            int endFret =   mSelectedFret > selectedFret ? mSelectedFret : selectedFret;

            if(startFret == endFret)
            {
                mDisplayer.unSelectAll();
                mFretSelected = false;
                mFirstSelection = true;
                mDisplayer.update();
                invalidate();
                return true;
            }

            for(int i = startFret; i <= endFret; i++)
                mDisplayer.selectFret(i);

            if(mFretIntervalSelectedListener != null)
                mFretIntervalSelectedListener.OnIntervalSelected(startFret,endFret);
            mFretSelected = true;
            mFirstSelection = true;
        }

        mDisplayer.update();
        invalidate();
        return true;
    }
    @Override public void onLongPress(MotionEvent e) {
        ((Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE)).vibrate(100);
    }
    @Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        mOffset += distanceX;
        awakenScrollBars();
        invalidate();
        return true;
    }
    @Override public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mScroller.fling(mOffset, 0, (int) -velocityX, 0, 0, (int) (mDisplayer.getWidth() - getMeasuredWidth()), 0, 0);
        invalidate();
        return true;
    }

    private int getDrawnOffset(){
        if(mOffset < 0)
            return 0;
        if(mOffset > mDisplayer.getWidth() - getMeasuredWidth())
            return (int) (mDisplayer.getWidth() - getMeasuredWidth());

        return mOffset;
    }
    @Override protected void onDraw(Canvas canvas) {
        if (mScroller.computeScrollOffset())
            mOffset = mScroller.getCurrX();

        long a = System.currentTimeMillis();

        canvas.save();
        canvas.translate(-getDrawnOffset(), 0);

        mDisplayer.draw(canvas);

        canvas.restore();

        long b = System.currentTimeMillis() - a;
        Log.d("Graphics Benchmark", "Draw time: " + b + "ms");

        if (!mScroller.isFinished())
            invalidate();
    }

    public void setOnFretIntervalSelectedListener(OnFretIntervalSelectedListener l){
        mFretIntervalSelectedListener = l;
    }
}
