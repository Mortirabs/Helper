package com.example.helper.presentation;

import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.camera2.CameraCaptureSession;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.helper.model.DayUsageModel;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class StatisticDraw extends View {
    private Paint drawPaint;
    private HashMap<Integer, DayUsageModel> dayUsageHashMap;

    public StatisticDraw(Context context, HashMap<Integer, DayUsageModel> dayUsageModelHashMap) {
        super(context);

        drawPaint = new Paint();

        drawPaint.setColor(Color.WHITE);
        drawPaint.setTextSize(40);
        drawPaint.getFontSpacing();
        drawPaint.setStrokeWidth(12);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setTextAlign(Paint.Align.CENTER);
        drawPaint.setAntiAlias(true);

        dayUsageHashMap = dayUsageModelHashMap;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // Draw height line:
        canvas.drawLine(10,100,10,500,drawPaint);
        // Draw weight line:
        canvas.drawLine(10,500,700,500,drawPaint);

        // Highest hours of the week:
        int highestUsageHours = getHighestUsageHours(dayUsageHashMap);
        // Space interval value:
        int spaceInterval = 0;
        if(highestUsageHours != 0) {
            spaceInterval = 400 / highestUsageHours;
        }


        for(int i=0,b=1;i<= 6;i++,b++) {
            int stopY = 500-dayUsageHashMap.get(i).hours*spaceInterval;
            // Statistic lines:
            canvas.drawLine(b*100,500,b*100,500-dayUsageHashMap.get(i).hours*spaceInterval,drawPaint);
//            // draw statistic hours lines:
//            canvas.drawLine(0,500-dayUsage.get(i)*spaceInterval,b*100,500-dayUsage.get(i)*spaceInterval,drawPaint);
            // Week day text:
            int h = b*100;
            canvas.drawText(Objects.requireNonNull(dayUsageHashMap.get(i)).day,h,550,drawPaint);
            canvas.drawText(""+ Objects.requireNonNull(dayUsageHashMap.get(i)).hours,b*100,stopY - 35,drawPaint);
        }

    }
    int getHighestUsageHours(HashMap<Integer,DayUsageModel> lister) {
        int h = 0;
        for(int z=0;z<=6;z++) {
            if(h < lister.get(z).hours) {
                h=lister.get(z).hours;
            }
        }
        return h;
    }
    int getAverageUsageHours(List<Integer> listWithUsage) {
        int averageHours = 0;
        for(int dayHours:listWithUsage) {
            averageHours = averageHours + dayHours;
        }
        return averageHours/7;
    }
}
