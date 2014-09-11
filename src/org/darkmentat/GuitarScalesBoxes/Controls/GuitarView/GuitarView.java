package org.darkmentat.GuitarScalesBoxes.Controls.GuitarView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import org.darkmentat.GuitarScalesBoxes.R;

public class GuitarView extends View
{
    private final Bitmap mFretTexture = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.gv_fret);
    private final Bitmap mPegTexture1 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.gv_peg01);
    private final Bitmap mPegTexture2 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.gv_peg02);
    private final Bitmap mPegTexture3 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.gv_peg03);
    private final Bitmap mPegTexture4 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.gv_peg04);
    private final Bitmap mPegTexture5 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.gv_peg05);
    private final Bitmap mPegTexture6 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.gv_peg06);

    public GuitarView(Context context) {
        super(context);
    }
    public GuitarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mPegTexture1, 0, 0, new Paint());
        canvas.drawBitmap(mPegTexture2, 0, mPegTexture1.getHeight(), new Paint());
        canvas.drawBitmap(mPegTexture3, 0, mPegTexture1.getHeight()+mPegTexture2.getHeight(), new Paint());
        canvas.drawBitmap(mPegTexture4, 0, mPegTexture1.getHeight()+mPegTexture2.getHeight()+mPegTexture3.getHeight(), new Paint());
        canvas.drawBitmap(mPegTexture5, 0, mPegTexture1.getHeight()+mPegTexture2.getHeight()+mPegTexture3.getHeight()+mPegTexture4.getHeight(), new Paint());
        canvas.drawBitmap(mPegTexture6, 0, mPegTexture1.getHeight()+mPegTexture2.getHeight()+mPegTexture3.getHeight()+mPegTexture4.getHeight()+mPegTexture5.getHeight(), new Paint());

        canvas.drawBitmap(mFretTexture, mPegTexture2.getWidth(), mPegTexture1.getHeight(), new Paint());
        canvas.drawBitmap(mFretTexture, mPegTexture2.getWidth(), mPegTexture1.getHeight()+mPegTexture2.getHeight(), new Paint());
        canvas.drawBitmap(mFretTexture, mPegTexture2.getWidth(), mPegTexture1.getHeight()+mPegTexture2.getHeight()+mPegTexture3.getHeight(), new Paint());
        canvas.drawBitmap(mFretTexture, mPegTexture2.getWidth(), mPegTexture1.getHeight()+mPegTexture2.getHeight()+mPegTexture3.getHeight()+mPegTexture4.getHeight(), new Paint());

        canvas.drawBitmap(mFretTexture, mPegTexture2.getWidth()+mFretTexture.getWidth(), mPegTexture1.getHeight(), new Paint());
        canvas.drawBitmap(mFretTexture, mPegTexture2.getWidth()+mFretTexture.getWidth(), mPegTexture1.getHeight()+mPegTexture2.getHeight(), new Paint());
        canvas.drawBitmap(mFretTexture, mPegTexture2.getWidth()+mFretTexture.getWidth(), mPegTexture1.getHeight()+mPegTexture2.getHeight()+mPegTexture3.getHeight(), new Paint());
        canvas.drawBitmap(mFretTexture, mPegTexture2.getWidth()+mFretTexture.getWidth(), mPegTexture1.getHeight()+mPegTexture2.getHeight()+mPegTexture3.getHeight()+mPegTexture4.getHeight(), new Paint());

        canvas.drawBitmap(mFretTexture, mPegTexture2.getWidth()+2*mFretTexture.getWidth(), mPegTexture1.getHeight(), new Paint());
        canvas.drawBitmap(mFretTexture, mPegTexture2.getWidth()+2*mFretTexture.getWidth(), mPegTexture1.getHeight()+mPegTexture2.getHeight(), new Paint());
        canvas.drawBitmap(mFretTexture, mPegTexture2.getWidth()+2*mFretTexture.getWidth(), mPegTexture1.getHeight()+mPegTexture2.getHeight()+mPegTexture3.getHeight(), new Paint());
        canvas.drawBitmap(mFretTexture, mPegTexture2.getWidth()+2*mFretTexture.getWidth(), mPegTexture1.getHeight()+mPegTexture2.getHeight()+mPegTexture3.getHeight()+mPegTexture4.getHeight(), new Paint());


        super.onDraw(canvas);
    }
}
