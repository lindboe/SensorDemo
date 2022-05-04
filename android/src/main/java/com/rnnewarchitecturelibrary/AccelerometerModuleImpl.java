package com.rnnewarchitecturelibrary;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.rnnewarchitecturelibrary.AccelerometerModuleImpl;

import java.util.Map;
import java.util.HashMap;

public class AccelerometerModuleImpl implements SensorEventListener {
    private final ReactApplicationContext reactContext;
    public static final String NAME = "Accelerometer";
    private final SensorManager sensorManager;
    private final Sensor sensor;
    private String sensorName;
    private int interval = 1;
    private double lastReading = (double) System.currentTimeMillis();
    private Arguments arguments;

    public AccelerometerModuleImpl(ReactApplicationContext context) {
        this.reactContext = context;
        this.sensorName = "Accelerometer";
        this.sensorManager = (SensorManager)reactContext.getSystemService(reactContext.SENSOR_SERVICE);
        this.sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

        public void addListener(String eventName) {
            sensorManager.registerListener(this, sensor, 10000);
        }

        public void removeListeners() {
            sensorManager.unregisterListener(this);
        }

        private static double sensorTimestampToEpochMilliseconds(long elapsedTime) {
            // elapsedTime = The time in nanoseconds at which the event happened.
            return System.currentTimeMillis() + ((elapsedTime- SystemClock.elapsedRealtimeNanos())/1000000L);
        }

        // SensorEventListener Interface
        private void sendEvent(String eventName, @Nullable WritableMap params) {
            try {
                this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit(eventName, params);
            } catch (RuntimeException e) {
                Log.e("ERROR", "java.lang.RuntimeException: Trying to invoke Javascript before CatalystInstance has been set!");
            }
        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            double tempMs = (double) System.currentTimeMillis();
            if (tempMs - lastReading >= interval) {
                lastReading = tempMs;
                WritableMap map = this.arguments.createMap();
                map.putDouble("x", sensorEvent.values[0]);
                map.putDouble("y", sensorEvent.values[1]);
                map.putDouble("z", sensorEvent.values[2]);
                // timestamp is added to all events
                map.putDouble("timestamp", this.sensorTimestampToEpochMilliseconds(sensorEvent.timestamp));
                this.sendEvent(this.sensorName, map);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void setUpdateInterval(int newInterval) {
            this.interval = newInterval;
        }
}
