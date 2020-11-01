package com.tuya.smart.rnsdk.camera;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.tuya.smart.android.camera.api.ITuyaHomeCamera;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.android.network.http.BusinessResponse;
import com.tuya.smart.android.user.api.ILoginCallback;
import com.tuya.smart.android.user.bean.User;
import com.tuya.smart.camera.camerasdk.typlayer.callback.OnRenderDirectionCallback;
import com.tuya.smart.camera.camerasdk.typlayer.callback.OperationDelegateCallBack;
import com.tuya.smart.camera.ipccamerasdk.bean.ConfigCameraBean;
import com.tuya.smart.camera.ipccamerasdk.p2p.ICameraP2P;
import com.tuya.smart.camera.middleware.p2p.ICameraConfig;
import com.tuya.smart.camera.middleware.p2p.ITuyaSmartCameraP2P;
import com.tuya.smart.camera.middleware.p2p.TuyaSmartCameraP2P;
import com.tuya.smart.camera.middleware.p2p.TuyaSmartCameraP2PFactory;
import com.tuya.smart.camera.middleware.widget.TuyaCameraView;
import com.tuya.smart.home.sdk.api.ITuyaHomeStatusListener;
import com.tuya.smart.home.sdk.bean.HomeBean;
import com.tuya.smart.home.sdk.callback.ITuyaGetHomeListCallback;
import com.tuya.smart.home.sdk.callback.ITuyaHomeResultCallback;
import com.tuya.smart.rnsdk.camera.activity.CameraLivePreviewActivity;

import androidx.annotation.NonNull;
import kotlin.jvm.internal.Intrinsics;

import com.tuya.smart.rnsdk.camera.utils.Constants;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.rnsdk.utils.TuyaReactUtils;
import com.tuya.smart.sdk.api.IDevListener;
import com.tuya.smart.sdk.bean.DeviceBean;
import com.tuyasmart.camera.devicecontrol.ITuyaCameraDevice;
import com.tuyasmart.camera.devicecontrol.TuyaCameraDeviceControlSDK;
import com.tuyasmart.camera.devicecontrol.api.ITuyaCameraDeviceControlCallback;
import com.tuyasmart.camera.devicecontrol.bean.DpBasicIndicator;
import com.tuyasmart.camera.devicecontrol.bean.DpPTZControl;
import com.tuyasmart.camera.devicecontrol.bean.DpPTZStop;
import com.tuyasmart.camera.devicecontrol.model.DpNotifyModel;
import com.tuyasmart.camera.devicecontrol.model.PTZDirection;
import com.tuyasmart.stencil.utils.MessageUtil;
import com.tuyasmart.stencil.utils.PreferencesUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TuyaCameraModule extends ReactContextBaseJavaModule {
    public TuyaCameraView TuyaCameraView;
    ReactApplicationContext reactContext;
    public static  long HOME_ID = 1099001;
    private String picPath, localKey = "";
    private ICameraP2P mCameraP2P;
    private ITuyaSmartCameraP2P mSmartCameraP2P;
    private ITuyaCameraDevice mDeviceControl;
    private static ITuyaHomeCamera homeCamera;
    public static DeviceBean mCameraDevice;
    ITuyaCameraDevice mTuyaCameraDevice;



    public TuyaCameraModule (ReactApplicationContext reactContext) {
        this.reactContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return "TuyaCameraModule";
    }

    @ReactMethod
    public void getTumbnail(final Promise promise) {
        String path = null;
        try {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartLife/Thumbnail/CameraThumbnail.png";
            Log.d("TAG", "thumnail path -->"+path);
            WritableMap returnParams = Arguments.createMap();
            returnParams.putString("path",path);

            JSONObject obj = new JSONObject();
            obj.put("path",path);
            promise.resolve(returnParams);

        } catch (Exception e) {
            e.printStackTrace();
        }
       // return path;

    }

    @ReactMethod
    public void getHomeDetails(final Promise promise) {
        TuyaHomeSdk.getHomeManagerInstance().queryHomeList(new ITuyaGetHomeListCallback() {
            @Override
            public void onSuccess(List<HomeBean> homeBeans) {
                if (homeBeans.size() == 0) {

                    return;
                }
                final long homeId = homeBeans.get(0).getHomeId();

                HOME_ID = homeId;
                PreferencesUtil.set("homeId", HOME_ID);
                TuyaHomeSdk.newHomeInstance(homeId).getHomeDetail(getHomeDetailsCallback(promise));
            }

            @Override
            public void onError(String errorCode, String error) {
                TuyaHomeSdk.newHomeInstance(HOME_ID).getHomeLocalCache(new ITuyaHomeResultCallback() {
                    @Override
                    public void onSuccess(HomeBean bean) {
                        promise.resolve(TuyaReactUtils.INSTANCE.parseToWritableMap(bean));
                    }

                    @Override
                    public void onError(String errorCode, String errorMsg) {
                        promise.reject(errorCode, errorMsg);

                    }
                });
            }
        });
    }

    @ReactMethod
    public void openLivePreview(Double reactTag, ReadableMap params) {

        Intent intent = new Intent(reactContext, CameraLivePreviewActivity.class);

        if(intent.resolveActivity(reactContext.getPackageManager()) != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constants.INTENT_DEVID, params.getString("devId"));
            intent.putExtra(Constants.INTENT_COUNTRY_CODE, params.getString("countryCode"));
            intent.putExtra(Constants.INTENT_UID, params.getString("uid"));
            intent.putExtra(Constants.INTENT_PASSWD, params.getString("passwd"));

            reactContext.startActivity(intent);
            // CameraLivePreviewActivity cameraLivePreviewActivity = (CameraLivePreviewActivity) reactContext.getCurrentActivity();
            // cameraLivePreviewActivity.setOnPressSettingsCallback(onPressSettings);

            Log.d("ReactNative","Starting Activity");
        }
    }

    @ReactMethod
    public void getCameraIndicatorStatus(ReadableMap params, final Promise promise) {

        String devId = params.getString("devId");
        registerCameraDevice(devId);

        if (mTuyaCameraDevice.isSupportCameraDps(DpBasicIndicator.ID)) {
            boolean o = mTuyaCameraDevice.queryBooleanCameraDps(DpBasicIndicator.ID);
            promise.resolve(TuyaReactUtils.INSTANCE.parseToWritableMap(o));

        } else {
            promise.reject("-1", "Camera does not support");
        }
    }

    @ReactMethod
    public void changeCameraIndicatorStatus(ReadableMap params, final Promise promise) {
        if(mTuyaCameraDevice != null ) {
            changeIndicatorStatus(promise);
        } else {
            registerCameraDevice(params.getString("devId"));

        }

    }

    @Nullable
    public final ITuyaHomeResultCallback getHomeDetailsCallback(@NotNull final Promise promise) {
        ITuyaHomeResultCallback callback = new ITuyaHomeResultCallback() {
            @Override
            public void onSuccess(HomeBean bean) {
                promise.resolve(TuyaReactUtils.INSTANCE.parseToWritableMap(bean));
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                promise.reject(errorCode, errorMsg);
            }
        };
        return callback;
    }

    private void registerCameraDevice(String devId) {
        mTuyaCameraDevice = TuyaCameraDeviceControlSDK.getCameraDeviceInstance(devId);
        mTuyaCameraDevice.setRegisterDevListener(new IDevListener()  {
            @Override
            public void onDpUpdate(String s, String s1) {
                L.d("TuyaHomeSdk", "onDpUpdate devId:" + s + "  dps " + s1);
                //此处监听所有dp点的信息
            }

            @Override
            public void onRemoved(String s) {

            }

            @Override
            public void onStatusChanged(String s, boolean b) {

            }

            @Override
            public void onNetworkStatusChanged(String s, boolean b) {

            }

            @Override
            public void onDevInfoUpdate(String s) {

            }
        });
    }

    public void changeIndicatorStatus(final Promise promise) {
        if( mTuyaCameraDevice.isSupportCameraDps(DpBasicIndicator.ID)) {
            boolean currentStatus = mTuyaCameraDevice.queryBooleanCameraDps(DpBasicIndicator.ID);
            mTuyaCameraDevice.registorTuyaCameraDeviceControlCallback(DpBasicIndicator.ID, new ITuyaCameraDeviceControlCallback<Boolean>() {
                @Override
                public void onSuccess(String s, DpNotifyModel.ACTION action, DpNotifyModel.SUB_ACTION sub_action, Boolean status) {
                    promise.resolve(TuyaReactUtils.INSTANCE.parseToWritableMap(status));
                }

                @Override
                public void onFailure(String s, DpNotifyModel.ACTION action, DpNotifyModel.SUB_ACTION sub_action, String s1, String s2) {
                    promise.reject("-1", "Failure in indicator status change");
                }
            });
            mTuyaCameraDevice.publishCameraDps(DpBasicIndicator.ID, !currentStatus);
        } else {
            promise.reject("-1", "Camera not support.");
        }
    }
}
