package com.iot.barcode.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class IotOverlayView extends View {

    private Rect mRect;
    private String mText;

    public IotOverlayView(Context c) {
        super(c);
    }

    public void setOverlay(Rect rect, String text){
        mRect = rect;
        mText = text;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mRect != null) {
            Paint p = new Paint();
            p.setColor(Color.RED);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(4.5f);
            canvas.drawRect(mRect, p);

            if(mText != null) {
                p.setTextSize(80);
                canvas.drawText(mText, mRect.left, mRect.bottom+90, p);
            }
        }
    }
}