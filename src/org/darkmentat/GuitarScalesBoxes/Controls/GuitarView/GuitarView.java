package org.darkmentat.GuitarScalesBoxes.Controls.GuitarView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import static android.view.GestureDetector.OnGestureListener;

public class GuitarView extends View implements OnGestureListener
{
    private DisplayerFretBoard mDisplayer = new StandartDisplayer(getContext());

    private int mOffset = 0;
    private final OverScroller mScroller = new OverScroller(getContext());
    private final GestureDetector mGestureDetector = new GestureDetector(getContext(), this);

    public GuitarView(Context context) {
        super(context);
    }
    public GuitarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFretBoard(FretBoard fretBoard) {
        mDisplayer.setFretBoard(fretBoard);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }
    @Override
    public boolean onDown(MotionEvent e) {
        mScroller.forceFinished(true);
        return true;
    }
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
    @Override
    public void onLongPress(MotionEvent e) {

    }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        mOffset += distanceX;
        if (mOffset < 0)
            mOffset = 0;

        if (mOffset > mDisplayer.getWidth() - getMeasuredWidth())
           mOffset = mDisplayer.getWidth() - getMeasuredWidth();

        invalidate();
        return true;
    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mScroller.fling(mOffset, 0, (int) -velocityX, 0, 0, mDisplayer.getWidth() - getMeasuredWidth(), 0, 0);
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mScroller.computeScrollOffset())
            mOffset = mScroller.getCurrX();

        canvas.save();
        canvas.translate(-mOffset, 0);
        //todo Don't draw anything outside visible part of view

        mDisplayer.draw(canvas);

        canvas.restore();

        if (!mScroller.isFinished())
            invalidate();
    }
}
