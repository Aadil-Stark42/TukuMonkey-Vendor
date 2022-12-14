package com.mdgiitr.suyash.graphkit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by karthik on 20/7/18.
 */

public class BarGraph extends View {

    public int MARKING_COLOR = Color.BLACK;
    public int thickness = 6;
    ArrayList<DataPoint> pointList;
    boolean pointSetflg = false, initflg = false;
    int numberOfFields = 0;
    int vH = 0, vW = 0;
    Bitmap mBitmap;
    Paint mPaint;
    Canvas mCanvas;
    float maxY;
    private int barWidth = 0;
    private int originShift = 50;
    private float scaleY = 1;
    private int topScaleMargin = 10;
    private int space = 10;
    private int LABEL_SIZE = 20;

    public BarGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i("TESTBITMAP","TESTBITMAP"+"11");
        if (!pointSetflg) {
            pointList = new ArrayList<>();
        }
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BarGraph, 0, 0);
        LABEL_SIZE = typedArray.getInteger(R.styleable.BarGraph_label_text_size, 20);
        space = typedArray.getInteger(R.styleable.BarGraph_bar_space,10);
    }

    public BarGraph(Context context, int vW, int vH) {
        super(context);
        Log.i("TESTBITMAP","TESTBITMAP"+"4");
        if (!pointSetflg) {
            pointList = new ArrayList<>();
        }
        this.vH = vH;
        this.vW = vW;
    }

    public void setPoints(ArrayList<DataPoint> pointList) {
        Log.i("TESTBITMAP","TESTBITMAP"+"11");
        this.pointList = pointList;
        pointSetflg = true;
        numberOfFields = pointList.size();
        maxY = getMaxY();
        invalidate();
    }

    public  void setfalse(){

        MARKING_COLOR = Color.BLACK;
       thickness = 6;
         pointList=null;
        pointSetflg = false;
        initflg = false;
         numberOfFields = 0;
         vH = 0;
         vW = 0;
         mBitmap=null;
         mPaint=null;
        mCanvas=null;
        maxY=0;
         barWidth = 0;
        originShift = 50;
        scaleY = 1;
        topScaleMargin = 10;
        space = 10;
        LABEL_SIZE = 20;
        pointSetflg = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if (vH == 0 && vW == 0) {
            vW = this.getMeasuredWidth();
            vH = this.getMeasuredHeight();
            Log.d("vH = ", vH + "");
            Log.d("vW = ", vW + "");
            boolean widthMatchParent = (ViewGroup.LayoutParams.MATCH_PARENT == getLayoutParams().width || ViewGroup.LayoutParams.WRAP_CONTENT == getLayoutParams().width);
            if (!widthMatchParent) {
                vW = vW / 2;
            }
            boolean heightMatchParent = (ViewGroup.LayoutParams.MATCH_PARENT == getLayoutParams().height || ViewGroup.LayoutParams.WRAP_CONTENT == getLayoutParams().height);
            if (!heightMatchParent) {
                vH = vH / 2;
            }
        }
        if (pointSetflg)
            if (!initflg) {
                mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setDither(true);
                mPaint.setColor(MARKING_COLOR);
                mBitmap = Bitmap.createBitmap(vW, vH, Bitmap.Config.ARGB_8888);
                mCanvas = new Canvas(mBitmap);
                mCanvas.translate(0, vH);

                String mark = Float.toString(getMaxY());
                mPaint.setTextSize(LABEL_SIZE);
                Rect bounds = new Rect();
                mPaint.getTextBounds(mark, 0, mark.length(), bounds);
                originShift = bounds.width() + 10;
                Log.d("TAG", originShift + "");

                mCanvas.translate(originShift, -originShift);
                drawGraph();
                initflg = true;
            }

        Log.i("TESTBITMAP","TESTBITMAP"+"1");
            if (mBitmap!=null)
        canvas.drawBitmap(mBitmap, 0, 0, new Paint(Paint.DITHER_FLAG));

    }

    public void drawGraph() {
        Log.i("TESTBITMAP","TESTBITMAP"+"5");
        mCanvas.drawColor(Color.WHITE);
        barWidth = (int) Math.round((float) (vW - originShift) / (float) numberOfFields);

        mCanvas.scale(1, -1);
        drawBars();
        mCanvas.scale(1, -1);

        drawMarkings();
        drawAxes();

    }

    private void drawAxes() {

        mPaint.setStrokeWidth(thickness);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mCanvas.drawLine(0, 0, 0, -(vH - originShift), mPaint);
        mCanvas.drawLine(0, 0, vW - originShift, 0, mPaint);

    }

    private void drawMarkings() {

        mPaint.setTextSize(LABEL_SIZE);

        for (int i = barWidth, j = 0; j < numberOfFields; i += barWidth, j++) {
            Rect bounds = new Rect();
            mPaint.getTextBounds(pointList.get(j).getName(), 0, pointList.get(j).getName().length(), bounds);
            mCanvas.drawText(pointList.get(j).getName(), i - bounds.width() / 2 - barWidth / 2, -2 * (mPaint.ascent()), mPaint);
        }

        int nD = getNumberOfDigits(maxY);
        float v;
        if (nD > 1) {
            v = (float) Math.pow(10, nD - 1);
        } else {
            v = (float) Math.pow(10, 0);
        }

        for (float i = v / scaleY; i < vH; i += (v / scaleY)) {

            mPaint.setStrokeWidth(thickness / 2);
            mPaint.setColor(MARKING_COLOR);
            mCanvas.drawLine(-5, -i, 5, -i, mPaint);
            String mark = Math.round(scaleY * i) + "";
            Rect bounds = new Rect();
            mPaint.getTextBounds(mark, 0, mark.length(), bounds);
            mCanvas.drawText(mark, -bounds.width() - 15, -(i + mPaint.ascent() / 2), mPaint);

        }

    }

    public void drawBars() {

        scaleY = maxY / (vH - originShift - topScaleMargin);

        for (int i = 0, j = 0; j < numberOfFields; i += barWidth, j++) {
            Log.d("TAG--", j + "");
            Log.d("TAG", i + "");
            Rect rect = new Rect(i + space, (int) (pointList.get(j).getData() / scaleY), i + barWidth - space, 0);
            Paint rPaint = new Paint();
            rPaint.setColor(pointList.get(j).getColor());
            rPaint.setStyle(Paint.Style.FILL);
            mCanvas.drawRect(rect, rPaint);
        }

    }

    private float getMaxY() {
        float maxY = pointList.get(0).getData();
        for (int i = 0; i < pointList.size(); i++) {
            if (pointList.get(i).getData() > maxY) {
                maxY = pointList.get(i).getData();
            }
        }
        return maxY;
    }

    private int getNumberOfDigits(float n) {
        int x = (int) n;
        int count = 0;
        while (x != 0) {
            x /= 10;
            count++;
        }
        return count;
    }

    public void setLabelTextSize(int size) {

        LABEL_SIZE = size;

    }

    public void setSpace(int s){

        space = s;

    }

    public Bitmap getBitmap() {

        Log.i("TESTBITMAP","TESTBITMAP"+"11");
        return mBitmap;
    }
}