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
    private final ReactApplicationContext reactContext;
    private final AccelerometerModuleImpl impl;

    AccelerometerModule(ReactApplicationContext context) {
        super(context);
        this.reactContext = context;
        this.impl = new AccelerometerModuleImpl(context);
    }

    @Override
    public String getName() {
        return AccelerometerModuleImpl.NAME;
    }

    @ReactMethod
    public void addListener(String eventName) {
        impl.addListener(eventName);
    }

    @ReactMethod
    public void removeListeners(double count) {
        impl.removeListeners();
    }
}
