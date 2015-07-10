package com.yannickschuchmann.peng.app.views.helpers.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.util.ArrayList;

/**
 * Created by Simon on 19.06.2015.
 */
public class AverageMovementCalculation implements Runnable{
    private int averageX, averageY, averageZ;
    private int sumX= 0, sumY= 0, sumZ = 0;
    private float x, y, z;
    private ArrayList<Integer> arrayListX, arrayListY, arrayListZ = new ArrayList<Integer>();;


    //Sensor nochmal aktivieren, damit diese in der Schleife ablaufen
    SensorEventListener accelListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) { }

        public void onSensorChanged(SensorEvent event) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
        }
    };
    @Override
    public void run() {

        for (int i = 0; i < arrayListX.size(); i++){    //Calculate the average vales for x, y and z
            sumX = sumX + arrayListX.get(i);
            sumY = sumY + arrayListY.get(i);
            sumZ = sumZ + arrayListZ.get(i);
        }

        int averageX = sumX/arrayListX.size();
        int averageY = sumY/arrayListY.size();
        int averageZ = sumZ/arrayListZ.size();

    }

    public void setArrayListX(ArrayList<Integer> arrayListX){
        this.arrayListX = arrayListX;
    }

    public void setArrayListY(ArrayList<Integer> arrayListY){
        this.arrayListY = arrayListY;
    }

    public void setArrayListXZ(ArrayList<Integer> arrayListZ){
        this.arrayListZ = arrayListZ;
    }

    public int getAverageValueX(){
        return averageX;
    }

    public int getAverageValueY(){
        return averageY;
    }

    public int getAverageValueZ(){
        return averageZ;
    }
}
