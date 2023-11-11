package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.EditText;


import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.List;

public class HomePage extends AppCompatActivity implements SensorEventListener {

    private  EditText editgoal;
    private  int progress;
    private SensorManager sensorManager;
    private TextView stepCountTextView;
    private TextView goalTextView;
    private ProgressBar progressBar;
    private int stepCount = 0;
    private int goal;
    private EditText goalEditText;


    private static final int REQUEST_SENSOR_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        stepCountTextView = findViewById(R.id.stepCountTextView1);
        goalTextView = findViewById(R.id.goalTextView);
        progressBar = findViewById(R.id.progressBar);
        goalEditText = findViewById(R.id.goalEditText);
        // Initialize the sensor manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, REQUEST_SENSOR_PERMISSION);
        } else {
            registerStepCounterSensor();
        }

        // Set up listener for goal input
        goalEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Update goal when the user finishes entering a new goal
                updateGoalFromEditText();
                return true;
            }
            return false;
        });



        // Check for permission to use the step counter sensor
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, REQUEST_SENSOR_PERMISSION);
        } else {
            registerStepCounterSensor();
        }
    }
    private void updateGoalFromEditText() {
        String goalString = goalEditText.getText().toString();
        if (!TextUtils.isEmpty(goalString)) {
            goal = Integer.parseInt(goalString);
            updateGoalTextView();
        }
    }

    private void updateGoalTextView() {
        goalTextView.setText("Goal: " + goal);
        updateProgressBar();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_SENSOR_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            registerStepCounterSensor();
        }
    }

    private void registerStepCounterSensor() {
        Sensor stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            stepCount = (int) event.values[0];
            updateStepCountTextView();
            updateProgressBar();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for step counter sensor
    }

    private void updateStepCountTextView() {
        stepCountTextView.setText("Step Count: " + stepCount);
    }

    private void updateProgressBar() {
        progress = (int) ((stepCount * 100.0) / goal);
        progressBar.setProgress(progress);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerStepCounterSensor();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
public int setStepCount(int step)
{
    return (this.stepCount = stepCount);

}


    public void reset(View view) {
        progress = 0;
        stepCount = 0;
        setStepCount(stepCount);
        stepCountTextView.setText("Step Count: " + stepCount);
        progressBar.setProgress(progress);

    }



}


