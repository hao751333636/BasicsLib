package com.base.basemodule.wedget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;

public class DrawableHorizontalButton extends AppCompatButton {

    public DrawableHorizontalButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas = getTopCanvas(canvas);
        super.onDraw(canvas);
    }

    private Canvas getTopCanvas(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables == null) {
            return canvas;
        }
        int type = -1;
        Drawable drawable = null;// 左面的drawable
        if (drawable == null) {
            type = 0;
            drawable = drawables[0];// 左面的drawable
        }
        if (drawable == null) {
            type = 1;
            drawable = drawables[1];// 上面的drawable
        }
        if (drawable == null) {
            type = 2;
            drawable = drawables[2];// 右面的drawable
        }
        if (drawable == null) {
            type = 3;
            drawable = drawables[3];// 下面的drawable
        }
        if(drawable==null){
            return canvas;
        }
        // float textSize = getPaint().getTextSize(); // 使用这个会导致文字竖向排下来
        float textSize = getPaint().measureText(getText().toString());
        int drawWidth = drawable.getIntrinsicWidth();
        int drawPadding = getCompoundDrawablePadding();
        float contentWidth = textSize + drawWidth + drawPadding;
        int leftPadding = (int) (getWidth() - contentWidth);
        setPadding(0, 0, leftPadding, 0); // 直接贴到左边
        float dx = (getWidth() - contentWidth) / 2;
        canvas.translate(dx, 0);// 往右移动
        return canvas;
    }
}