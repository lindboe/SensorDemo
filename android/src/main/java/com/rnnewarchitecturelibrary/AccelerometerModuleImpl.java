package com.rnnewarchitecturelibrary;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.rnnewarchitecturelibrary.AccelerometerModuleImpl;

import java.util.Map;
import java.util.HashMap;

public class AccelerometerModuleImpl {
    public static final String NAME = "Accelerometer";

    public static void add(double a, double b, Promise promise) {
        promise.resolve(a + b);
    }

}
