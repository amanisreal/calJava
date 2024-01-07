package com.example.caljava;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    //private CsvWriter csvWriter;
    private static final String FILE_NAME = "example.csv";
    private static final long INTERVAL = 5 * 60 * 1000; // 5 minutes in milliseconds

    private Handler handler;
    private Timer timer;

    private String name1 = "John Doe";
    private int age1 = 30;
    private String occupation1 = "Engineer";

    private String name2 = "Jane Smith";
    private int age2 = 25;
    private String occupation2 = "Teacher";

    Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine, btnZero;
    Button btnAdd, btnSub, btnMulti, btnDivi, btnEqual;
    String firstNumber ="";
    String forShowing ="";
    long ans = 0;
    TextView textView, textViewLight;

    String sensorHum = "";

    Queue<String> operations = new LinkedList<>();
   // private CsvWriter csvWriter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //csvWriter = new CsvWriter(this);
        //csvReader = new CsvReader(this);

        // Call this method whenever you want to add data to the CSV file
        //addDataToCsv();

        // SENSOR

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        Sensor sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor sensorMagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        Sensor sensorPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        Sensor sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        //SENSOR



        //-----------------------------------------------

        btnZero = findViewById(R.id.btnZero);
        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);
        btnThree = findViewById(R.id.btnThree);
        btnFour = findViewById(R.id.btnFour);
        btnFive = findViewById(R.id.btnFive);
        btnSix = findViewById(R.id.btnSix);
        btnSeven = findViewById(R.id.btnSeven);
        btnEight = findViewById(R.id.btnEight);
        btnNine = findViewById(R.id.btnNine);
        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSubtract);
        btnMulti = findViewById(R.id.btnMultiply);
        btnDivi = findViewById(R.id.btnDivide);
        btnEqual = findViewById(R.id.btnEqual);
        textView = findViewById(R.id.textview);
        textViewLight = findViewById(R.id.textviewLight);

        //-----------------------------------------------

        btnZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumbers(0);
            }
        });

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumbers(1);
            }
        });

        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumbers(2);
            }
        });

        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumbers(3);
            }
        });

        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumbers(4);
            }
        });

        btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumbers(5);
            }
        });

        btnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumbers(6);
            }
        });

        btnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumbers(7);
            }
        });

        btnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumbers(8);
            }
        });

        btnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumbers(9);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBODMAS("+");
                SensorEventListener sensorEventListenerHumidity = new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        sensorHum = "Humidity: " +  String.valueOf(event.values[0]);
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {

                    }
                };

                sensorManager.registerListener(sensorEventListenerHumidity, sensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);

                List<String[]> data = new ArrayList<>();
                //data.add(new String[]{"Name", "Age", "City"});
                data.add(new String[]{sensorHum});
                //data.add(new String[]{"Jane Smith", "30", "San Francisco"});

                // Write data to the CSV file

                // Write data to the CSV file and get the file path
                String csvFilePath = CsvFileWriter.writeDataToCSV(MainActivity.this, data);

                if (csvFilePath != null) {
                    // File write successful

                    // Send the CSV file in the background
                    AsyncTask<String, Void, Boolean> ans;
                    ans = new SendCsvTask().execute(csvFilePath);
                    Log.i("ms", ans.toString());
                } else {
                    // File write failed
                }

            }
        });


        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBODMAS("-");
            }
        });

        btnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBODMAS("*");
            }
        });

        btnDivi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBODMAS("/");
            }
        });

    }



    public void onNumbers(long num){
        firstNumber+=String.valueOf(num);
        forShowing+=String.valueOf(num);
        textView.setText(forShowing);
        //= forShowing;
//        if(operations.size>=3)cal();
    }

    public void onBODMAS(String r){
        operations.add(firstNumber);
        operations.add(r);
        firstNumber = "";
        forShowing+=r;
        textView.setText(forShowing);
        if(operations.size()>=3)cal();
    }

    public void cal(){
        if(Long.toString(ans).equals("0")) {
            ans = Long.parseLong(operations.remove());
        }
        String sign = operations.remove();

        long num2 = Long.parseLong(operations.remove());

        if(Objects.equals(sign, "+")){
            ans+=num2;
        }
        else if(Objects.equals(sign, "-")){
            ans-=num2;
        }
        else if(Objects.equals(sign, "*")){
            ans*=num2;
        }
        else{
            ans/=num2;
        }
        textViewLight.setText(String.valueOf(ans));
        String sign2 = operations.remove();
        operations.add(Long.toString(ans));
        operations.add(sign2);
        ans = (long) 0;
    }
}