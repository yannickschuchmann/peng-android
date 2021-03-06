package com.yannickschuchmann.peng.app.views.helpers.sensors;
/**
 * Created by Simon on 13.06.2015.
 */
public class Movement {

    public Movement(){

    }

    public static String ResultCodeToString(int result) {
        String action_type;
        switch (result) {
            case 0:
                action_type = "defensive";
                break;
            case 1:
                action_type = "offensive";
                break;
            case 2:
                action_type = "neutral";
                break;
            default:
                action_type = "defensive";
                break;
        }
        return action_type;
    }

    public static String ActionNameToActionFileName(String string) {
        String action_type;
        if (string.equals("defensive")) {
            action_type = "block";
        } else if (string.equals("offensive")) {
            action_type = "shoot";
        } else if (string.equals("neutral")) {
            action_type = "reload";
        } else {
            action_type = string;
        }
        return action_type;
    }

    public static int StringToResultCode(String string) {
        int result;
        // no string switch statements in Java 6
        if (string.equals("defensive")) {
            result = 0;
        } else if (string.equals("offensive")) {
            result = 1;
        } else if (string.equals("neutral")) {
            result = 2;
        } else {
            result = 0;
        }
        return result;
    }

    public static int RoundResultToResultCode(String string) {
        int result;
        // no string switch statements in Java 6
        if (string.equals("blocked")) {
            result = 0;
        } else if (string.equals("won")) {
            result = 1;
        } else if (string.equals("neutral")) {
            result = 2;
        } else {
            result = 0;
        }
        return result;
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


        if(true /*checkIfWaitPosition(x, y, z) == false*/){      //checkIfWaitPosition is deactivated!!
            if(p <= 6 && x >= -1.9){             /*0 = Defense*/
                result = 0;
            }
            else if(x >= -1.9 ){                /*1 = Attack*/    //Old: x>=4 && y >=4 && z<=3 && p >= 6              //&& (y >= -5 && y <= 7) && (z >= -7 && z <= 8)
                result = 1;
            }
            else if(x <= -2.0){                  /*2 = Reload*/    //Old: x<=-7 && y >=-2 && z<=4 && p >= 6           //&& (y >= -7 && y <= 8) && (z >= -6 && z <= 6)
                result = 2;
            }
            else{                               /*3 = Error*/
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
