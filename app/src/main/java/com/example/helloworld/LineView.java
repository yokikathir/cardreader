package com.example.helloworld;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class LineView extends androidx.appcompat.widget.AppCompatImageView
{
    private Paint paint = new Paint();

    private Paint paint_outer = new Paint();

    private PointF pointA_left, pointB_left;
    private PointF pointA_right, pointB_right;

    private PointF pointA_left_outer, pointB_left_outer;
    private PointF pointA_right_outer, pointB_right_outer;

    private PointF pointA_top, pointB_top;
    private PointF pointA_bottom, pointB_bottom;

    private PointF pointA_top_outer, pointB_top_outer;
    private PointF pointA_bottom_outer, pointB_bottom_outer;


    private Bitmap bitmap;

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        //canvas = new Canvas(bitmap);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);

        paint_outer.setColor(Color.GREEN);
        paint_outer.setStrokeWidth(5);

        canvas.drawLine(pointA_left.x,pointA_left.y,pointB_left.x,pointB_left.y,paint);
        canvas.drawLine(pointA_right.x,pointA_right.y,pointB_right.x,pointB_right.y,paint);

        canvas.drawLine(pointA_top.x,pointA_top.y,pointB_top.x,pointB_top.y,paint);
        canvas.drawLine(pointA_bottom.x,pointA_bottom.y,pointB_bottom.x,pointB_bottom.y,paint);

        canvas.drawLine(pointA_top_outer.x,pointA_top_outer.y,pointB_top_outer.x,pointB_top_outer.y,paint_outer);
        canvas.drawLine(pointA_bottom_outer.x,pointA_bottom_outer.y,pointB_bottom_outer.x,pointB_bottom_outer.y,paint_outer);

        canvas.drawLine(pointA_left_outer.x,pointA_left_outer.y,pointB_left_outer.x,pointB_left_outer.y,paint_outer);
        canvas.drawLine(pointA_right_outer.x,pointA_right_outer.y,pointB_right_outer.x,pointB_right_outer.y,paint_outer);

        super.onDraw(canvas);
    }
//    public void setPointA(PointF point){
//        pointA = point;
//    }
//    public void setPointB(PointF point){
//        pointB = point;
//    }
    public void setLeftLine(PointF point_a, PointF point_b){
        pointA_left = point_a;
        pointB_left = point_b;
    }
    public void setRightLine(PointF point_a, PointF point_b){
        pointA_right = point_a;
        pointB_right = point_b;
    }

    public void setLeftOuterLine(PointF point_a, PointF point_b){
        pointA_left_outer = point_a;
        pointB_left_outer = point_b;
    }
    public void setRightOuterLine(PointF point_a, PointF point_b){
        pointA_right_outer = point_a;
        pointB_right_outer = point_b;
    }
    public void setTopLine(PointF point_a, PointF point_b){
        pointA_top = point_a;
        pointB_top = point_b;
    }
    public void setBottomLine(PointF point_a, PointF point_b){
        pointA_bottom = point_a;
        pointB_bottom = point_b;
    }
    public void setTopOuterLine(PointF point_a, PointF point_b){
        pointA_top_outer = point_a;
        pointB_top_outer = point_b;
    }
    public void setBottomOuterLine(PointF point_a, PointF point_b){
        pointA_bottom_outer = point_a;
        pointB_bottom_outer = point_b;
    }


    public void setImage(Bitmap bitmap_)
    {
        bitmap = bitmap_;
        setImageBitmap(bitmap);
    }
    public void draw()
    {
        invalidate();
        requestLayout();
    }

}
