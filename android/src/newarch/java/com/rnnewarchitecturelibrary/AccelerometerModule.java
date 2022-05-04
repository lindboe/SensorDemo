package com.rnnewarchitecturelibrary;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.HashMap;

public class AccelerometerModule extends NativeAccelerometerSpec {
    private final ReactApplicationContext reactContext;
    private final AccelerometerModuleImpl impl;

    AccelerometerModule(ReactApplicationContext context) {
        super(context);
        this.reactContext = context;
        this.impl = new AccelerometerModuleImpl(context);
    }

    @Override
    @NonNull
    public String getName() {
        return AccelerometerModuleImpl.NAME;
    }

    @Override
    public void addListener(String eventName) {
      impl.addListener(eventName);
    }

    @Override
    public void removeListeners(double count) {
        impl.removeListeners();
    }
}
