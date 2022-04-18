package com.olddemo;
import android.util.Log;
import com.facebook.react.bridge.Promise;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class CalendarModule extends ReactContextBaseJavaModule {
    CalendarModule(ReactApplicationContext context) {
        super(context);
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
