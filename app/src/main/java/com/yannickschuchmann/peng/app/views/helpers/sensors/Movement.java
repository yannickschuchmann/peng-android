package com.yannickschuchmann.peng.app.views.helpers.sensors;
/**
 * Created by Simon on 13.06.2015.
 */
public class Movement {

    public Movement(){

    }

    public int movementResult(float x, float y, float z, float p){
        /*
            x, y, z are the axis from the Accelerometer
            p is the value from the Proximity sensor

            0 = Defense / Still in wait position
            1 = Attack
            2 = Reload
            3 = Error
         */

        int result = 3; //Standard set


        if(checkIfWaitPosition(x, y, z) == false){
            if(p <= 4){                                                          /*0 = Defense*/
                result = 0;
            }
            else if(x >= -1.9){      /*1 = Attack*/    //Old: x>=4 && y >=4 && z<=3 && p >= 6              //&& (y >= -5 && y <= 7) && (z >= -7 && z <= 8)
                result = 1;
            }
            else if(x <= 2.0){       /*2 = Reload*/    //Old: x<=-7 && y >=-2 && z<=4 && p >= 6           //&& (y >= -7 && y <= 8) && (z >= -6 && z <= 6)
                result = 2;
            }
            else{                                                                /*3 = Error*/
                result = 3;
            }
        }else{
            result = 0;
        }

        return result;
    }

    public boolean checkIfWaitPosition(float x, float y, float z){
        boolean result = false;

        if(x<=4 && y <=2 && z<=4d){
            result = true;
        }
        else{
            result = false;
        }

        return result;
    }
}
