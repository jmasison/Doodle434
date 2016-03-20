package com.example.josephmasison.doodle_434;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/*
 * Created by josephmasison on 3/7/16.
 *
 */

public class DoodleView extends View {

    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private ArrayList<Path> arrPath = new ArrayList<>();
    private ArrayList<Paint> arrPaint = new ArrayList<>();


    int _SIZE = 5;
    int _ALPHA = 255;

    private ArrayList<Path> arrPathDeleted = new ArrayList<>();
    private ArrayList<Paint> arrPaintDeleted = new ArrayList<>();


    public void undo(){
        Log.i("TAG", "undoA" + arrPathDeleted.size() + " " + arrPath.size());
        arrPathDeleted.add(mPath);
        arrPaintDeleted.add(mPaint);

        arrPaint.remove(arrPaint.size() - 1);
        arrPath.remove(arrPath.size() - 1);

        Log.i("TAG", "undoB" + arrPaintDeleted.size() + " " + arrPaint.size());

        invalidate();
    }
    public void redo(){
        Paint toAddPaint = arrPaintDeleted.get(arrPaintDeleted.size() - 1);
        Path toAddPath = arrPathDeleted.get(arrPathDeleted.size() - 1);

        arrPaintDeleted.remove(arrPaintDeleted.size() - 1);
        arrPathDeleted.remove(arrPathDeleted.size() - 1);

        arrPaint.add(toAddPaint);
        arrPath.add(toAddPath);

        invalidate();
    }


    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    public void clearPath(){
        arrPath = new ArrayList<>();
        mPath = new Path();
        arrPath.add(mPath);

        arrPaint = new ArrayList<>();
        arrPaint.add(mPaint);
        invalidate();
    }

    public void setPaintColor(int color){
        Log.i("TAG", "setColor");

        mPath = new Path();
        arrPath.add(mPath);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(getResources().getColor(color));
        mPaint.setStrokeWidth(_SIZE);
        mPaint.setAlpha(_ALPHA);
        arrPaint.add(mPaint);

        invalidate();
    }

    public void setPaintSize(int size){
        Log.i("TAG", "setSize");
        _SIZE = size;

        mPath = new Path();
        arrPath.add(mPath);

        Paint temp = mPaint;
        mPaint = new Paint(temp);
        mPaint.setStrokeWidth(size);
        arrPaint.add(mPaint);

        invalidate();
    }

    public void setPaintOp(int alph){
        if (alph != 100)
            alph = alph / 4;

        Log.i("TAG", "setAlpha," + Integer.toString(alph));
        _ALPHA = alph;

        mPath = new Path();
        arrPath.add(mPath);

        Paint temp = mPaint;
        mPaint = new Paint(temp);
        mPaint.setAlpha(alph);
        arrPaint.add(mPaint);

        invalidate();
    }

    public void red(){
        setPaintColor(R.color.Red);
    }
    public void orange(){
        setPaintColor(R.color.Orange);
    }
    public void yellow(){
        setPaintColor(R.color.Yellow);
    }
    public void green() {
        setPaintColor(R.color.Green);
    }
    public void blue(){
        setPaintColor(R.color.Blue);
    }
    public void purple(){
        setPaintColor(R.color.Purple);
    }

    public DoodleView(Context context){
        super(context);
        init(null, 0);
    }

    public DoodleView(Context context, AttributeSet attributes){
        super(context, attributes);
        init(attributes, 0);
    }

    public DoodleView(Context context, AttributeSet attributes, int defStyle){
        super(context, attributes, defStyle);
        init(attributes, defStyle);
    }

    private void init(AttributeSet attributeSet, int i){
        setPaintColor(R.color.Green);

    }

    @Override
    public void onDraw(Canvas canvas){
        Log.i("TAG", "onDraw-"+arrPath.size()+","+arrPaint.size());
        super.onDraw(canvas);
        for (int i = 1; i < arrPath.size(); i++){
            canvas.drawPath(arrPath.get(i), arrPaint.get(i));
        }
    }
    /*@Override
    public void onDraw(Canvas canvas){
        mCanvas = canvas;
        super.onDraw(mCanvas);

        mCanvas.drawPath(mPath, mPaint);
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x,y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        arrPath.add(mPath);
        arrPaint.add(mPaint);

        invalidate();
        return true;
    }

/*private void touchEnded(int lineID)
    {
        Path path = pathMap.get(lineID); // get the corresponding Path
        bitmapCanvas.drawPath(path, paintLine); // draw to bitmapCanvas
        path.reset(); // reset the Path
        rememberLineId = lineID;
    } // end method touch_ended

    //undo
    private void undo()
    {
        Path path = pathMap.get(rememberLineId); // get the corresponding Path
        pathMap.remove(rememberLineId);
        bitmapCanvas.clearPath(path, paintLine);
        path.reset(); // reset the Path
    }*/

}
