package com.tuya.smart.rnsdk.camera;

import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.tuya.smart.camera.middleware.widget.TuyaCameraView;
import com.tuya.smart.rnsdk.camera.activity.CameraLivePreviewActivity;

import androidx.annotation.NonNull;
import com.tuya.smart.rnsdk.camera.utils.Constants;

public class TuyaCameraModule extends ReactContextBaseJavaModule {
    public TuyaCameraView TuyaCameraView;
    ReactApplicationContext reactContext;

    public TuyaCameraModule (ReactApplicationContext reactContext) {
        this.reactContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return "TuyaCameraModule";
    }

    @ReactMethod
    public void openLivePreview(String countryCode, String uid, String passwd, String devId) {

        Intent intent = new Intent(reactContext, CameraLivePreviewActivity.class);

        if(intent.resolveActivity(reactContext.getPackageManager()) != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constants.INTENT_DEVID, devId);
            intent.putExtra(Constants.INTENT_COUNTRY_CODE, countryCode);
            intent.putExtra(Constants.INTENT_UID, uid);
            intent.putExtra(Constants.INTENT_PASSWD, passwd);
            reactContext.startActivity(intent);
            Log.d("ReactNative","Starting Activity");
        }
    }
}
