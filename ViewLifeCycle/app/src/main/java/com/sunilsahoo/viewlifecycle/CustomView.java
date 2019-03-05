package com.sunilsahoo.viewlifecycle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CustomView extends View {

    private static final String TAG = "CustomView";
    private Paint labelPaint;
    private String labelText;


    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        Log.d(TAG, "inside layout -");
        super.layout(l, t, r, b);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG, "inside onLayout - "+changed+" left : "+left);
        if (left == 0) {
            layout(50, 100, right, bottom);
        } else {
            super.onLayout(changed, left, top, right, bottom);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "inside onMeasure -" + widthMeasureSpec + " : " + heightMeasureSpec);
        setMeasuredDimension(800, 300);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "inside onDraw -");
        drawLabel(canvas);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
        labelText = a.getString(R.styleable.CustomView_text);
        labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setTextSize(70);
        labelPaint.setColor(Color.WHITE);
        labelPaint.setTextAlign(Paint.Align.LEFT);
        labelPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        setSaveEnabled(true);
    }

    private void drawLabel(Canvas canvas) {
        float x = getPaddingLeft();
        //the y coordinate marks the bottom of the text, so we need to factor in the height
        Rect bounds = new Rect();
        labelPaint.getTextBounds(labelText, 0, labelText.length(), bounds);
        float y = getPaddingTop() + bounds.height();
        canvas.drawText(labelText, x, y, labelPaint);
    }

    public void setText(String text) {
        labelText = text;
        invalidate();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Log.d(TAG, "inside onSaveInstanceState -");
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.state = labelText;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        Log.d(TAG, "inside onRestoreInstanceState - " + state);
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setCustomState(ss.state);
    }

    public void setCustomState(String customState) {
        this.labelText = customState;
    }

    static class SavedState extends BaseSavedState {
        String state;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            state = in.readString();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(state);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
