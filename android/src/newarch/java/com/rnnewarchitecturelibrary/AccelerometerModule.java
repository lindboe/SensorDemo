package com.rnnewarchitecturelibrary;

import androidx.annotation.NonNull;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.HashMap;

public class AccelerometerModule extends NativeAccelerometerSpec {

    AccelerometerModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    @NonNull
    public String getName() {
        return AccelerometerModuleImpl.NAME;
    }

    @Override
    public void add(double a, double b, Promise promise) {
      AccelerometerModuleImpl.add(a, b, promise);
    }
}
