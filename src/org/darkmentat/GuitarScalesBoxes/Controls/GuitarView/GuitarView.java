package org.darkmentat.GuitarScalesBoxes.Controls.GuitarView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;
import org.darkmentat.GuitarScalesBoxes.R;

import static android.view.GestureDetector.OnGestureListener;

public class GuitarView extends View implements OnGestureListener
{
    private DisplayerFretBoard mDisplayer = new StandartDisplayer(getContext());

    private int mMinFretCountOnScreen;
    private int mOffset = 0;
    private final OverScroller mScroller = new OverScroller(getContext());
    private final GestureDetector mGestureDetector = new GestureDetector(getContext(), this);

    public GuitarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(true);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.GuitarView);
        setMinFretCountOnScreen(a.getInt(R.styleable.GuitarView_minFretsOnScreen, 0));
        initializeScrollbars(a);
        a.recycle();
    }

    public void setFretBoard(FretBoard fretBoard) {
        mDisplayer.setFretBoard(fretBoard);
        invalidate();
    }
    public void setMinFretCountOnScreen(int frets){
        mMinFretCountOnScreen = frets;
        if (getMeasuredWidth() > 0)
            mDisplayer.setMinFretCountOnScreen(mMinFretCountOnScreen);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mDisplayer.setScreenSize(getMeasuredWidth(), getMeasuredHeight());
        mDisplayer.setMinFretCountOnScreen(mMinFretCountOnScreen);
    }

    @Override protected int computeHorizontalScrollRange() {
        return mDisplayer.getWidth();
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
        return false;
    }
    @Override public void onLongPress(MotionEvent e) {

    }
    @Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        mOffset += distanceX;
        awakenScrollBars();
        invalidate();
        return true;
    }
    @Override public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mScroller.fling(mOffset, 0, (int) -velocityX, 0, 0, mDisplayer.getWidth() - getMeasuredWidth(), 0, 0);
        invalidate();
        return true;
    }

    private int getDrawnOffset(){
        if(mOffset < 0)
            return 0;
        if(mOffset > mDisplayer.getWidth() - getMeasuredWidth())
            return mDisplayer.getWidth() - getMeasuredWidth();

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
}
