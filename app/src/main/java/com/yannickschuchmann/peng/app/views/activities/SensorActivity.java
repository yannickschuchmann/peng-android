package com.yannickschuchmann.peng.app.views.activities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import java.io.IOException;
import java.util.ArrayList;


public class SensorActivity extends TransitionActivity implements SensorView {

    //*****************VARIABLES*****************
    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor sensor, proximitySensor;
    @Bind(R.id.textLabelHealthEnemy) TextView textLabelHealthEnemy;
    @Bind(R.id.textLabelHealthUser) TextView textLabelHealthUser;

    @Bind(R.id.imageEnemyMag1) ImageView imageEnemyMag1;
    @Bind(R.id.imageEnemyMag2) ImageView imageEnemyMag2;
    @Bind(R.id.imageEnemyMag3) ImageView imageEnemyMag3;

    @Bind(R.id.imageUserMag1) ImageView imageUserMag1;
    @Bind(R.id.imageUserMag2) ImageView imageUserMag2;
    @Bind(R.id.imageUserMag3) ImageView imageUserMag3;

    @Bind(R.id.countDownOverlay) ImageView countDownOverlay;

    @Bind(R.id.sensor_overlay) LinearLayout sensorOverlay;
    @Bind(R.id.round_overlay) LinearLayout roundOverlay;

    @Bind(R.id.roundCount) TextView roundCount;

    @Bind(R.id.imageUser)
    GifImageView imageUser;
    @Bind(R.id.imageEnemy) GifImageView imageEnemy;
    @Bind(R.id.imageResult) ImageView imageResult;

    @Bind(R.id.myActionLabel) TextView myActionLabel;
    @Bind(R.id.opActionLabel) TextView opActionLabel;

    private Actor mMe, mOpponent;
    private Movement movement;
    private float x, y, z, p, tempX, tempY, tempZ, tempP;
    private ArrayList <Integer> arrayListX = new ArrayList<Integer>();
    private ArrayList <Integer> arrayListY = new ArrayList<Integer>();
    private ArrayList <Integer> arrayListZ = new ArrayList<Integer>();
    private AverageMovementCalculation averageMovementCalculation;
    private Thread averageMovementCalculationThread;

    private Drawable blockedDrawable;
    private Drawable loseDrawable;
    private Drawable missedDrawable;
    private Drawable readyDrawable;
    private Drawable reloadDrawable;
    private Drawable versusDrawable;
    private Drawable waitDrawable;
    private Drawable winDrawable;

    private SensorPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_2);

        mPresenter = new SensorPresenter(this, getIntent().getIntExtra("duelId", 0));

        ButterKnife.bind(this);

        sensorOverlay.setVisibility(View.GONE);
        roundOverlay.setVisibility(View.GONE);

        //*********************CONSTRUCTORS*********************
        vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        movement = new Movement();
        averageMovementCalculation = new AverageMovementCalculation();
        averageMovementCalculationThread = new Thread(averageMovementCalculation);

        registerListenerAccelerometer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }
/*
    public void onResume() {
        super.onResume();
        registerListenerAccelerometer();    //Abschalten wenn nicht die ganze Zeit angezeigt werden soll
    }
*/
    @Override
    public void onPause(){
        super.onPause();
        unregisterListenerAccelerometer();
        unregisterAllImageViews();
        this.finish();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    public void receiveAction(Duel duel, boolean update) {
//        movementSound(Movement.RoundResultToResultCode(duel.getResult()));
        this.setupView(duel, update);
    }

    public void setupView(Duel duel, boolean update) {

        mMe = duel.getMe();
        mOpponent = duel.getOpponent();

        roundCount.setText("Round " + Integer.toString(duel.getRoundCount()));

        textLabelHealthUser.setText(Integer.toString(mMe.getHitPoints()));
        textLabelHealthEnemy.setText(Integer.toString(mOpponent.getHitPoints()));

        Drawable resultImage = getResources().getDrawable(
            getResources().getIdentifier(duel.getResult(), "drawable", "com.yannickschuchmann.peng.app")
        );

        String myAction = (!duel.isMyTurn() || !duel.isActive()) ?
                Movement.ActionNameToActionFileName(duel.getMyAction().getType()) : "your turn";
        myActionLabel.setText(myAction);

        String opAction = (duel.isMyTurn() || !duel.isActive()) ?
                Movement.ActionNameToActionFileName(duel.getOpponentAction().getType()) : "waiting";
        opActionLabel.setText(opAction);

        imageResult.setImageDrawable(resultImage);

        setMagazineImageByNumberOfBulletsLoaded(mOpponent.getShots(), "e");
        setMagazineImageByNumberOfBulletsLoaded(mMe.getShots(), "u");

        if (duel.getResult().equals("won")) {
            setActionImageByMovementCode(
                    "loser_1", "e",
                    duel.getOpponent().getCharacterName()
            );

            setActionImageByMovementCode(
                    "winner_1", "u",
                    duel.getMe().getCharacterName()
            );
        } else if (duel.getResult().equals("lost")) {
            setActionImageByMovementCode(
                    "winner_1", "e",
                    duel.getOpponent().getCharacterName()
            );

            setActionImageByMovementCode(
                    "loser_1", "u",
                    duel.getMe().getCharacterName()
            );
        } else {
            setActionImageByMovementCode(
                    duel.getOpponentAction().getType(), "e",
                    duel.getOpponent().getCharacterName()
            );

            setActionImageByMovementCode(
                    duel.getMyAction().getType(), "u",
                    duel.getMe().getCharacterName()
            );
        }
    }

    SensorEventListener accelListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) { }

        public void onSensorChanged(SensorEvent event) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
        }
    };

    SensorEventListener proximityListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor proximitySensor, int acc) { }

        public void onSensorChanged(SensorEvent event) {
            p = event.values[0];
        }
    };


    @OnClick(R.id.mainLinearLayout)
    public void onButtonStart() {
        if (mPresenter.getDuel().isMyTurn()) {

            sensorOverlay.setVisibility(View.VISIBLE);
            roundOverlay.setVisibility(View.VISIBLE);

            vibrator.vibrate(100);

            new CountDownTimer(5000, 1000){             //Countdown before move
                public void onTick(long millisUntilFinished) {
//                    textLabelCountdown.setText("Countdown: " + ((millisUntilFinished / 1000) - 1));
                    int countDownId = getResources()
                            .getIdentifier(
                                    "o_" + String.valueOf(millisUntilFinished / 1000 - 1),
                                    "drawable",
                                    "com.yannickschuchmann.peng.app"
                            );


                    if( (millisUntilFinished / 1000) <= 1 ){
                        countDownOverlay.setImageDrawable(getResources().getDrawable(R.drawable.o_los));
                        vibrator.vibrate(1000);
                    }else{
                        countDownOverlay.setImageDrawable(getResources().getDrawable(countDownId));
                        vibrator.vibrate(100);
                    }
                }

                public void onFinish() {
                    int result;
                    tempX = x;
                    tempY = y;
                    tempZ = z;
                    tempP = p;
                    result = movement.movementResult(tempX, tempY, tempZ, tempP);
//                    textLabelCountdown.setText("Countdown"/*"X : " + (int) tempX + ", Y : " + (int) tempY + ", Z : " + (int) tempZ + ", Type: " + result*/);

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

                    if (mPresenter.setResult(result)) {
                        movementSound(result);
                        imageResult.setImageDrawable(waitDrawable);
                    }

                    sensorOverlay.setVisibility(View.GONE);
                    roundOverlay.setVisibility(View.GONE);


                    // for now movement is set for own character,
                    // if this is not the desired behaviour we should remove this line
                    // setActionImageByMovementCode(result, "u");
                }
            }.start();
        }
    }

    public void setActionImageByMovementCode(String actionName, String userType, String character){
        GifImageView imageView;
        String color;
        if (userType == "e") {
            imageView = imageEnemy;
            color = "red";
        } else {
            imageView = imageUser;
            color = "blu";
        }

        String action = Movement.ActionNameToActionFileName(actionName);

        int imageID = getResources()
                .getIdentifier(character + "_" + color + "_" + action, "drawable", "com.yannickschuchmann.peng.app");

        GifDrawable characterImage;
        try {
            characterImage = new GifDrawable(getResources(), imageID);
            imageView.setImageDrawable(characterImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                SensorManager.SENSOR_DELAY_NORMAL);
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

    public void unregisterAllImageViews(){
        imageUser.setImageDrawable(null);
        imageEnemy.setImageDrawable(null);
        imageEnemyMag1.setImageDrawable(null);
        imageEnemyMag2.setImageDrawable(null);
        imageEnemyMag3.setImageDrawable(null);
        imageUserMag1.setImageDrawable(null);
        imageUserMag2.setImageDrawable(null);
        imageUserMag3.setImageDrawable(null);
        countDownOverlay.setImageDrawable(null);
        imageResult.setImageDrawable(null);
    }
}