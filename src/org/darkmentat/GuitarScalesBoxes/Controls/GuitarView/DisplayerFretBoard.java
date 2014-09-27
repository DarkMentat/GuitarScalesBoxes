package org.darkmentat.GuitarScalesBoxes.Controls.GuitarView;

import android.graphics.Canvas;

interface DisplayerFretBoard
{
    public void draw(Canvas canvas);

    public int getWidth();
    public int getHeight();

    public void setFretBoard(FretBoard fretBoard);
    public void setScreenSize(int width, int height);
    public void setMinFretCountOnScreen(int frets);

    public void unSelectAll();
    public void selectFret(int fret);
    public void selectFretAtPoint(float point);
    public int getFretAtPoint(float point);
    public float getPointOfFret(int fret);

    public void update();
}
