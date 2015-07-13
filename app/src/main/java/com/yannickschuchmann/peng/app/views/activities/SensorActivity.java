package com.yannickschuchmann.peng.app.views.activities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import android.media.MediaPlayer;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.presenters.SensorPresenter;
import com.yannickschuchmann.peng.app.views.helpers.sensors.AverageMovementCalculation;
import com.yannickschuchmann.peng.app.views.helpers.sensors.Movement;
import com.yannickschuchmann.peng.app.views.views.SensorView;
import com.yannickschuchmann.peng.model.entities.Actor;
import com.yannickschuchmann.peng.model.entities.Duel;


public class SensorActivity extends TransitionActivity implements SensorView {

    //*****************VARIABLES*****************
    @Bind(R.id.buttonStart) Button buttonStartClick;
    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor sensor, proximitySensor;
    @Bind(R.id.textLabelX) TextView textLabelX;
    @Bind(R.id.textLabelY) TextView textLabelY;
    @Bind(R.id.textLabelZ) TextView textLabelZ;
    @Bind(R.id.textLabelProximity) TextView textLabelProximity;
    @Bind(R.id.textLabelHealthEnemy) TextView textLabelHealthEnemy;
    @Bind(R.id.textLabelHealthUser) TextView textLabelHealthUser;
    @Bind(R.id.textLabelLoadedMagazineEnemy) TextView textLabelLoadedMagazineEnemy;
    @Bind(R.id.textLabelLoadedMagazineUser) TextView textLabelLoadedMagazineUser;
    @Bind(R.id.textLabelCountdown) TextView textLabelCountdown;

    @Bind(R.id.imageUser) ImageView imageUser;
    @Bind(R.id.imageEnemy) ImageView imageEnemy;
    @Bind(R.id.imageUserMag1) ImageView imageUserMag1;
    @Bind(R.id.imageUserMag2) ImageView imageUserMag2;
    @Bind(R.id.imageUserMag3) ImageView imageUserMag3;
    @Bind(R.id.imageEnemyMag1) ImageView imageEnemyMag1;
    @Bind(R.id.imageEnemyMag2) ImageView imageEnemyMag2;
    @Bind(R.id.imageEnemyMag3) ImageView imageEnemyMag3;
    @Bind(R.id.imageResult) ImageView imageResult;
    @Bind(R.id.mainLinearLayout) LinearLayout mainLinearLayout;

    private Actor mMe, mOpponent;
    private Movement movement;
    private float x, y, z, p, tempX, tempY, tempZ, tempP;
    private ArrayList <Integer> arrayListX = new ArrayList<Integer>();
    private ArrayList <Integer> arrayListY = new ArrayList<Integer>();
    private ArrayList <Integer> arrayListZ = new ArrayList<Integer>();
    private AverageMovementCalculation averageMovementCalculation;
    private Thread averageMovementCalculationThread;

    private SensorPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        mPresenter = new SensorPresenter(this, getIntent().getIntExtra("duelId", 0));

        ButterKnife.bind(this);

        //*********************CONSTRUCTORS*********************
        vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        movement = new Movement();
        averageMovementCalculation = new AverageMovementCalculation();
        averageMovementCalculationThread = new Thread(averageMovementCalculation);
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

    public void setupView(Duel duel) {

        mMe = duel.getMe();
        mOpponent = duel.getOpponent();

        textLabelHealthUser.setText(Integer.toString(mMe.getHitPoints()));
        textLabelHealthEnemy.setText(Integer.toString(mOpponent.getHitPoints()));
//        textLabelLoadedMagazineUser.setText(Boolean.toString(userCharacter.getWeaponLoaded()));
//        textLabelLoadedMagazineEnemy.setText(Boolean.toString(enemyCharacter.getWeaponLoaded()));

        Drawable user = getResources().getDrawable(R.drawable.blue_waits);
        imageUser.setImageDrawable(user);
        Drawable ready = getResources().getDrawable(R.drawable.bereit);
        imageResult.setImageDrawable(ready);
        Drawable enemy = getResources().getDrawable(R.drawable.red_waits);
        imageEnemy.setImageDrawable(enemy);

        setMagazineImageByNumberOfBulletsLoaded(mMe.getShots(), "u");
        setMagazineImageByNumberOfBulletsLoaded(mOpponent.getShots(), "e");

        setActionImageByMovementCode(Movement.StringToResultCode(duel.getMyAction().getType()), "u");
        setActionImageByMovementCode(Movement.StringToResultCode(duel.getOpponentAction().getType()), "e");
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


    @OnClick(R.id.buttonStart)
    public void onButtonStart() {
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

                        mPresenter.setResult(result);

                        setActionImageByMovementCode(result, "e");
                        setActionImageByMovementCode(result, "u");

                    }else{
                        textLabelCountdown.setText("Keine Bewegung erkannt");
                    }
                }
            }.start();
        } else {
            textLabelCountdown.setText("Startposition einnehmen");
        }
    }

    public void setActionImageByMovementCode(int movementCode, String userType){
        //This Method change the image to the given movement code
        //User types: e = enemy, u = user
        if (userType == "e"){
            switch (movementCode) {
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
        } else {
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