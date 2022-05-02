package com.rnnewarchitecturelibrary;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.HashMap;

public class AccelerometerModule extends ReactContextBaseJavaModule {
    public static final String NAME = "Accelerometer";

    AccelerometerModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void add(int a, int b, Promise promise) {
        promise.resolve(a + b);
    }
}
