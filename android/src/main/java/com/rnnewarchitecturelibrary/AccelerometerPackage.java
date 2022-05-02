package com.rnnewarchitecturelibrary;

import androidx.annotation.Nullable;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.TurboReactPackage;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class AccelerometerPackage extends TurboReactPackage {

    @Nullable
    @Override
    public NativeModule getModule(String name, ReactApplicationContext reactContext) {
        if (name.equals(AccelerometerModule.NAME)) {
            return new AccelerometerModule(reactContext);
        } else {
            return null;
        }
    }

    @Override
    public ReactModuleInfoProvider getReactModuleInfoProvider() {
        return () -> {
            final Map<String, ReactModuleInfo> moduleInfos = new HashMap<>();
            boolean isTurboModule = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED;
            moduleInfos.put(
                    AccelerometerModule.NAME,
                    new ReactModuleInfo(
                            AccelerometerModule.NAME,
                            AccelerometerModule.NAME,
                            false, // canOverrideExistingModule
                            false, // needsEagerInit
                            true, // hasConstants
                            false, // isCxxModule
                            isTurboModule // isTurboModule
            ));
            return moduleInfos;
        };
    }
}

// package com.rnnewarchitecturelibrary;
// 
// import com.facebook.react.ReactPackage;
// import com.facebook.react.bridge.NativeModule;
// import com.facebook.react.bridge.ReactApplicationContext;
// import com.facebook.react.uimanager.ViewManager;
// 
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.List;
// 
// public class AccelerometerPackage implements ReactPackage {
// 
//   @Override
//   public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
//       return Collections.emptyList();
//   }
// 
//   @Override
//   public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
//       List<NativeModule> modules = new ArrayList<>();
//       modules.add(new AccelerometerModule(reactContext));
//       return modules;
//   }
// 
// }
// 