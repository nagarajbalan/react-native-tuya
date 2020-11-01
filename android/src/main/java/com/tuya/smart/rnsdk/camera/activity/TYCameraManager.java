package com.tuya.smart.rnsdk.camera.activity;

import android.app.Activity;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

import androidx.annotation.NonNull;

public class TYCameraManager extends ViewGroupManager<CustomCameraView> {

    public static final String REACT_CLASS = "TYCamera";
    ReactApplicationContext mCallerContext;
    Activity mActivity;

    public TYCameraManager(Activity activity , ReactApplicationContext context) {
       // mActivity = activity;
        mCallerContext = context;
        mActivity = context.getCurrentActivity();
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactProp(name = "devId")
    public void setDevId(CustomCameraView view, String devId) {
        view.setDevId(devId);
        view.init(mActivity, mCallerContext);
    }



    @NonNull
    @Override
    protected CustomCameraView createViewInstance(@NonNull ThemedReactContext reactContext) {
        //return new CustomCameraView(reactContext, Fresco.newDraweeControllerBuilder(), null, mCallerContext);
        return new CustomCameraView(reactContext);
    }

    public Map getExportedCustomBubblingEventTypeConstants() {
        return MapBuilder.builder()
                .put(
                 "onSettingsClick",
                MapBuilder.of("phasedRegistrationNames",MapBuilder.of("bubbled", "onSettingsBtnClick")))
                .build();
    }

//    @ReactMethod
//    public void onSettingsBtnClick(String devId) {
//        // Create map for params
//        WritableMap payload = Arguments.createMap();
//        // Put data to map
//        payload.putString("devId", devId);
//        // Get EventEmitter from context and send event thanks to it
//        this.mCallerContext
//                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                .emit("loadSettings", payload);
//    }
//    @Override
//    public Map<String,Integer> getCommandsMap() {
//        Log.d("React"," View manager getCommandsMap:");
//        return MapBuilder.of(
//                "saveImage",
//                COMMAND_SAVE_IMAGE);
//    }
//
//    @Override
//    public void receiveCommand(
//            SignatureCaptureMainView view,
//            int commandType,
//            @Nullable ReadableArray args) {
//        Assertions.assertNotNull(view);
//        Assertions.assertNotNull(args);
//        switch (commandType) {
//            case COMMAND_SAVE_IMAGE: {
//                view.saveImage();
//                return;
//            }
//
//            default:
//                throw new IllegalArgumentException(String.format(
//                        "Unsupported command %d received by %s.",
//                        commandType,
//                        getClass().getSimpleName()));
//        }
//    }



//    @SuppressLint("ResourceAsColor")
//    @ReactProp(name = "src")
//    public void setSrc(CustomCameraView view, @Nullable String sources) {
//        view.setBackgroundColor(R.color.black);
//        //view.setSource(sources);
//    }


//
//    @ReactProp(name = ViewProps.RESIZE_MODE)
//    public void setResizeMode(ReactImageView view, @Nullable String resizeMode) {
//        view.setScaleType(ImageResizeMode.toScaleType(resizeMode));
//    }
}
