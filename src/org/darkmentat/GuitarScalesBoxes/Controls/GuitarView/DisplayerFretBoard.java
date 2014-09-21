package org.darkmentat.GuitarScalesBoxes.Controls.GuitarView;

import android.graphics.Canvas;

interface DisplayerFretBoard
{
    public void draw(Canvas canvas);
    public void setFretBoard(FretBoard fretBoard);
    public void setScreenSize(int width, int height);
    public void setMinFretCountOnScreen(int frets);
    public int getWidth();
    public int getHeight();
}
