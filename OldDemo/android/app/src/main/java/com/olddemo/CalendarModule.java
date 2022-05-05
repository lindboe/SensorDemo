package com.olddemo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class CalendarModule extends ReactContextBaseJavaModule implements SensorEventListener {
    private final ReactApplicationContext reactContext;
    private final SensorManager sensorManager;
    private final Sensor sensor;
    private String sensorName;
    private int interval;
    private double lastReading = (double) System.currentTimeMillis();
    private Arguments arguments;

    public CalendarModule(ReactApplicationContext context) {
        super(context);
        this.reactContext = context;
        this.sensorName = "Accelerometer";
        this.sensorManager = (SensorManager)reactContext.getSystemService(reactContext.SENSOR_SERVICE);
        this.sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @ReactMethod
    public void addListener(String eventName) {
        // Milliseconds to Microseconds conversion
        sensorManager.registerListener(this, sensor, this.interval * 1000);
    }

    @ReactMethod
    public void removeListeners(Integer count) {
        sensorManager.unregisterListener(this);
    }

    private static double sensorTimestampToEpochMilliseconds(long elapsedTime) {
        // elapsedTime = The time in nanoseconds at which the event happened.
        return System.currentTimeMillis() + ((elapsedTime-SystemClock.elapsedRealtimeNanos())/1000000L);
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

    @ReactMethod
    public void setUpdateInterval(int newInterval) {
        this.interval = newInterval;
    }

    @Override
    public String getName() {
        return "CalendarModule";
    }

    @ReactMethod
    public void createCalendarEvent(String name, String location, Callback callback) {
        Integer eventId = 1;
        callback.invoke(name, location, eventId);
    }

    @ReactMethod
    public void createCalendarEventWithPromise(String name, String location, Promise promise) {
        try {
            Integer eventId = 2;
            promise.resolve(eventId);
        } catch(Exception e) {
            promise.reject("Create Event Error", e);
        }
    }




    // example Date conversion code from docs
    //     String dateFormat = "yyyy-MM-dd";
    //    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    //    Calendar eStartDate = Calendar.getInstance();
    //    try {
    //        eStartDate.setTime(sdf.parse(startDate));
    //    }

    // don't use getConstants directly from "native module object", unsupported by TM
    // TM makes getConstants a regular module method, each invocation hits native code

    // Callbacks
    //  Callbacks are used to:
    //   - pass data from Java/Kotlin to JavaScript for async methods
    //   - asynchronously execute JavaScript from the native side
    // A native module method can only invoke one callback, one time
    // - why?
    // Callback can be stored and invoked later
    // for error handling, either:
    // - invoke callback w/ an optional error argument, or
    // - provide both A native module can, however, store the callback and invoke it later. (How is the "one time" esuccess and failure callbacks

    // Promises
    // Alternative to callbacks
    // also can only either reject or resolve, not both, at most once
    // can be stored and invoked later
    // https://github.com/facebook/react-native/blob/main/ReactAndroid/src/main/java/com/facebook/react/bridge/Promise.java#L1

    // Sending events
    // ...
    //import com.facebook.react.modules.core.DeviceEventManagerModule;
    //import com.facebook.react.bridge.WritableMap;
    //import com.facebook.react.bridge.Arguments;
    //...
    //private void sendEvent(ReactContext reactContext,
    //                      String eventName,
    //                      @Nullable WritableMap params) {
    // reactContext
    //     .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
    //     .emit(eventName, params);
    //}
    //@ReactMethod
    //public void addListener(String eventName) {
    //  // Set up any upstream listeners or background tasks as necessary
    //}
    //
    //@ReactMethod
    //public void removeListeners(Integer count) {
    //  // Remove upstream listeners, stop unnecessary background tasks
    //}
    //...
    //WritableMap params = Arguments.createMap();
    //params.putString("eventProperty", "someValue");
    //...
    //sendEvent(reactContext, "EventReminder", params);

    // then in JS
    //  const eventEmitter = new NativeEventEmitter(NativeModules.ToastExample);
    //   this.eventListener = eventEmitter.addListener('EventReminder', (event) => {

    // Activities
    // You'll need to listen to onActivityResult if you want to get results from an activity you started with startActivityForResult.
    // - Why would I want to do this?
    // difference w/ `reactContext.addLifecycleEventListener`? still relates to `resume`, `pause`, `destroy`, not React lifecycle

    // Threading
    // All async methods on one thread
    // should not assume what thread
    // If a blocking call is required, heavy work should be dispatched to an internally managed worker thread, and callbacks distributed from there

    // Other
    // ...how does overloading work?

    // Todo try image picker example
    // todo try toast example
}
