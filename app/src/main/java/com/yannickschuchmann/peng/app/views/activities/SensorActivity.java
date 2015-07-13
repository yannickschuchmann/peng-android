package com.yannickschuchmann.peng.app.views.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.annotation.MainThread;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import android.media.MediaPlayer;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.presenters.SensorPresenter;
import com.yannickschuchmann.peng.app.views.helpers.sensors.AverageMovementCalculation;
import com.yannickschuchmann.peng.app.views.helpers.sensors.Movement;
import com.yannickschuchmann.peng.app.views.views.SensorView;


public class SensorActivity extends TransitionActivity implements SensorView, View.OnClickListener {

    //*****************VARIABLES*****************
    private Button buttonStartClick;
    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor sensor, proximitySensor;
    private TextView textLabelX, textLabelY, textLabelZ, textLabelProximity,textLabelHealthEnemy, textLabelHealthUser, textLabelLoadedMagazineEnemy, textLabelLoadedMagazineUser, textLabelCountdown;
    private Character enemyCharacter, userCharacter;
    private Movement movement;
    private float x, y, z, p, tempX, tempY, tempZ, tempP;
    private ImageView imageUser, imageEnemy, imageUserMag1, imageUserMag2, imageUserMag3, imageEnemyMag1, imageEnemyMag2, imageEnemyMag3, imageResult;
    private ArrayList <Integer> arrayListX = new ArrayList<Integer>();
    private ArrayList <Integer> arrayListY = new ArrayList<Integer>();
    private ArrayList <Integer> arrayListZ = new ArrayList<Integer>();
    private AverageMovementCalculation averageMovementCalculation;
    private Thread averageMovementCalculationThread;
    private LinearLayout mainLinearLayout;

    private SensorPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        mPresenter = new SensorPresenter(this, getIntent().getIntExtra("duelId", 0));

        //*********************CONSTRUCTORS*********************
        vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        buttonStartClick = (Button) findViewById(R.id.buttonStart);
        mainLinearLayout = (LinearLayout) findViewById(R.id.mainLinearLayout);

        textLabelX = (TextView) findViewById(R.id.textLabelX);
        textLabelY = (TextView) findViewById(R.id.textLabelY);
        textLabelZ = (TextView) findViewById(R.id.textLabelZ);
        textLabelProximity = (TextView) findViewById(R.id.textLabelProximity);
        textLabelHealthEnemy = (TextView) findViewById(R.id.textLabelHealthEnemy);
        textLabelHealthUser = (TextView) findViewById(R.id.textLabelHealthUser);
        textLabelLoadedMagazineEnemy = (TextView) findViewById(R.id.textLabelLoadedMagazineEnemy);
        textLabelLoadedMagazineUser = (TextView) findViewById(R.id.textLabelLoadedMagazineUser);
        textLabelCountdown = (TextView) findViewById(R.id.textLabelCountdown);
        imageUser = (ImageView) findViewById(R.id.imageUser);
        imageEnemy = (ImageView) findViewById(R.id.imageEnemy);
        imageEnemyMag1 = (ImageView) findViewById(R.id.imageEnemyMag1);
        imageEnemyMag2 = (ImageView) findViewById(R.id.imageEnemyMag2);
        imageEnemyMag3 = (ImageView) findViewById(R.id.imageEnemyMag3);
        imageUserMag1 = (ImageView) findViewById(R.id.imageUserMag1);
        imageUserMag2 = (ImageView) findViewById(R.id.imageUserMag2);
        imageUserMag3 = (ImageView) findViewById(R.id.imageUserMag3);
        imageResult = (ImageView) findViewById(R.id.imageResult);

        movement = new Movement();
        averageMovementCalculation = new AverageMovementCalculation();
        averageMovementCalculationThread = new Thread(averageMovementCalculation);
//        enemyCharacter = new Character("Enemy Character");
//        userCharacter = new Character("User Character");

        //*********************SET LISTENER*********************
        buttonStartClick.setOnClickListener(this);

        //Set default character Attributes (later with database values)
//        enemyCharacter.setCharacterHealth(3);
//        enemyCharacter.setMaxNumberOfBullets(3);
//        enemyCharacter.setCharacterMagazine(0);
//        enemyCharacter.setWeaponLoaded(false);
//        enemyCharacter.setCharacterMagazine(0);
//
//        userCharacter.setCharacterHealth(3);
//        userCharacter.setMaxNumberOfBullets(3);
//        userCharacter.setCharacterMagazine(0);
//        userCharacter.setWeaponLoaded(false);
//        userCharacter.setCharacterMagazine(0);


        //*****************SET LABELS*****************
//        textLabelHealthEnemy.setText(Integer.toString(enemyCharacter.getHealth()));
//        textLabelHealthUser.setText(Integer.toString(userCharacter.getHealth()));
//        textLabelLoadedMagazineEnemy.setText(Boolean.toString(enemyCharacter.getWeaponLoaded()));
//        textLabelLoadedMagazineUser.setText(Boolean.toString(userCharacter.getWeaponLoaded()));

        Drawable user = getResources().getDrawable(R.drawable.blue_waits);
        imageUser.setImageDrawable(user);
        Drawable ready = getResources().getDrawable(R.drawable.bereit);
        imageResult.setImageDrawable(ready);
        Drawable enemy = getResources().getDrawable(R.drawable.red_waits);
        imageEnemy.setImageDrawable(enemy);

        setMagazineImageByNumberOfBulletsLoaded(0, "e");
        setMagazineImageByNumberOfBulletsLoaded(0, "u");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    public void onResume() {
        super.onResume();
        registerListenerAccelerometer();    //Abschalten wenn nicht die ganze Zeit angezeigt werden soll
    }

    public void onStop() {
        super.onStop();
        unregisterListenerAccelerometer();
    }

    SensorEventListener accelListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) { }

        public void onSensorChanged(SensorEvent event) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            textLabelX.setText("X: " + (int) x);
            textLabelY.setText("Y: " + (int) y);
            textLabelZ.setText("Z: " + (int) z);
        }
    };

    SensorEventListener proximityListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor proximitySensor, int acc) { }

        public void onSensorChanged(SensorEvent event) {
            p = event.values[0];
            textLabelProximity.setText("P: " + (int) p);
        }
    };

    @Override
    public void onClick(View v) {
        //Switch for different buttons
        switch (v.getId()){
            case R.id.buttonStart:
                if(true /*movement.checkIfWaitPosition(x, y, z)*/){     //Ausgangslage ist jetzt erstmal beliebig!
                    textLabelCountdown.setText("Countdown start");
                    vibrator.vibrate(100);

                    new CountDownTimer(5000, 1000){             //Countdown before move
                        public void onTick(long millisUntilFinished) {
                            textLabelCountdown.setText("Countdown: " + ((millisUntilFinished / 1000) - 1));
                            if( (millisUntilFinished / 1000) <= 1 ){
                                textLabelCountdown.setText("Bewegen");
                                vibrator.vibrate(1000);
                            }else{
                                vibrator.vibrate(100);
                            }
                        }

                        public void onFinish() {

                            if(true /*y>-6*/) {//Ausgangslage ist jetzt erstmal beliebig!
                                int result;
                                tempX = x;
                                tempY = y;
                                tempZ = z;
                                tempP = p;
                                result = movement.movementResult(tempX, tempY, tempZ, tempP);
                                textLabelCountdown.setText("Countdown"/*"X : " + (int) tempX + ", Y : " + (int) tempY + ", Z : " + (int) tempZ + ", Type: " + result*/);

                                movementSound(result);

                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                vibrator.vibrate(50);
                                try {
                                    Thread.sleep(150);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                vibrator.vibrate(50);

                                setActionImageByMovementCode(result, "e");
                                setActionImageByMovementCode(result, "u");

                            }else{
                                textLabelCountdown.setText("Keine Bewegung erkannt");
                            }
                        }
                    }.start();
                }
                else{
                    textLabelCountdown.setText("Startposition einnehmen");
                }
                break;
        }
    }

    public void setActionImageByMovementCode(int movementCode, String userType){
        //This Method change the image to the given movement code
        //User types: e = enemy, u = user
        if(userType == "e"){
            switch (movementCode){
                case 0:
                    Drawable defense = getResources().getDrawable(R.drawable.red_protects);
                    imageEnemy.setImageDrawable(defense);
                    break;
                case 1:
                    Drawable attack = getResources().getDrawable(R.drawable.red_shoots);
                    imageEnemy.setImageDrawable(attack);
                    break;
                case 2:
                    Drawable reload = getResources().getDrawable(R.drawable.red_reloads);
                    imageEnemy.setImageDrawable(reload);
                    break;
                case 3:
                    Drawable wait = getResources().getDrawable(R.drawable.red_waits);
                    imageEnemy.setImageDrawable(wait);
                    break;
            }
        }else {
            switch (movementCode) {
                case 0:
                    Drawable defense = getResources().getDrawable(R.drawable.blue_protects);
                    imageUser.setImageDrawable(defense);
                    Drawable defenseView = getResources().getDrawable(R.drawable.verteidigt);
                    imageResult.setImageDrawable(defenseView);

                    break;
                case 1:
                    Drawable attack = getResources().getDrawable(R.drawable.blue_shoots);
                    imageUser.setImageDrawable(attack);
                    Drawable attackView = getResources().getDrawable(R.drawable.schuss);
                    imageResult.setImageDrawable(attackView);

//                    userCharacter.setCharacterMagazine(userCharacter.getCharacterMagazine() - 1);            //MAGAZIN WIRD HIER GESETZT ZUR DEMONSTRATION
//                    setMagazineImageByNumberOfBulletsLoaded(userCharacter.getCharacterMagazine(), "u");
                    break;
                case 2:
                    Drawable reload = getResources().getDrawable(R.drawable.blue_reloads);
                    imageUser.setImageDrawable(reload);
                    Drawable reloadView = getResources().getDrawable(R.drawable.nachladen);
                    imageResult.setImageDrawable(reloadView);

//                    userCharacter.setCharacterMagazine(userCharacter.getCharacterMagazine() + 1);            //MAGAZIN WIRD HIER GESETZT ZUR DEMONSTRATION
//                    setMagazineImageByNumberOfBulletsLoaded(userCharacter.getCharacterMagazine(), "u");
                    break;
                case 3:
                    Drawable wait = getResources().getDrawable(R.drawable.blue_waits);
                    imageUser.setImageDrawable(wait);
                    Drawable ready = getResources().getDrawable(R.drawable.bereit);
                    imageResult.setImageDrawable(ready);
                    break;
            }
        }
    }

    public void setMagazineImageByNumberOfBulletsLoaded(int numberOfBulletsLoaded, String userType){
        //User types: e = enemy, u = user
        Drawable magazineUnloadedImage = getResources().getDrawable(R.drawable.shoot_unloaded);
        Drawable magazineLoadedImage = getResources().getDrawable(R.drawable.shoot_loaded);

        if(userType == "e"){
            switch (numberOfBulletsLoaded){

                case 0 :
                    imageEnemyMag1.setImageDrawable(magazineUnloadedImage);
                    imageEnemyMag2.setImageDrawable(magazineUnloadedImage);
                    imageEnemyMag3.setImageDrawable(magazineUnloadedImage);
                    break;

                case 1 :
                    imageEnemyMag1.setImageDrawable(magazineLoadedImage);
                    imageEnemyMag2.setImageDrawable(magazineUnloadedImage);
                    imageEnemyMag3.setImageDrawable(magazineUnloadedImage);
                    break;

                case 2 :
                    imageEnemyMag1.setImageDrawable(magazineLoadedImage);
                    imageEnemyMag2.setImageDrawable(magazineLoadedImage);
                    imageEnemyMag3.setImageDrawable(magazineUnloadedImage);
                    break;
                case 3 :
                    imageEnemyMag1.setImageDrawable(magazineLoadedImage);
                    imageEnemyMag2.setImageDrawable(magazineLoadedImage);
                    imageEnemyMag3.setImageDrawable(magazineLoadedImage);
                    break;
            }
        }else{
            switch (numberOfBulletsLoaded) {
                case 0:
                    imageUserMag1.setImageDrawable(magazineUnloadedImage);
                    imageUserMag2.setImageDrawable(magazineUnloadedImage);
                    imageUserMag3.setImageDrawable(magazineUnloadedImage);
                    break;

                case 1:
                    imageUserMag1.setImageDrawable(magazineLoadedImage);
                    imageUserMag2.setImageDrawable(magazineUnloadedImage);
                    imageUserMag3.setImageDrawable(magazineUnloadedImage);
                    break;

                case 2:
                    imageUserMag1.setImageDrawable(magazineLoadedImage);
                    imageUserMag2.setImageDrawable(magazineLoadedImage);
                    imageUserMag3.setImageDrawable(magazineUnloadedImage);
                    break;

                case 3:
                    imageUserMag1.setImageDrawable(magazineLoadedImage);
                    imageUserMag2.setImageDrawable(magazineLoadedImage);
                    imageUserMag3.setImageDrawable(magazineLoadedImage);
                    break;
            }
        }
    }

    public void registerListenerAccelerometer(){
        //Method to register the sensor
        sensorManager.registerListener(accelListener, sensor,
                SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(proximityListener, proximitySensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterListenerAccelerometer(){
        //Method to unregister the sensor
        sensorManager.unregisterListener(accelListener);
        sensorManager.unregisterListener(proximityListener);
    }

    public void movementSound(int soundType){
        MediaPlayer mediaPlayer = new MediaPlayer();
        /*
        0 = Defense / Still in wait position
        1 = Attack
        2 = Reload
        */
        switch (soundType) {
            case 0:
                mediaPlayer=MediaPlayer.create(SensorActivity.this, R.raw.parry_shot );
                mediaPlayer.start();
                break;

            case 1:
                mediaPlayer=MediaPlayer.create(SensorActivity.this, R.raw.revolver_shot );
                mediaPlayer.start();
                break;

            case 2:
                mediaPlayer=MediaPlayer.create(SensorActivity.this, R.raw.revolver_reload );
                mediaPlayer.start();
                break;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}