import { NativeModules } from 'react-native';

const tuya = NativeModules.TuyaCameraModule;

export type CameraLivePreviewParams = {
  countryCode: string;
  uid: string;
  passwd: string;
  devId: string;
};

export type CameraDetailsParams = {
  devId: string;
};

export type CameraNightVisionParams = {
  devId: string;
  nightMode: string;
};

export function testFunction() {
  return tuya.testFunction();
}

export function openCameraLivePreview(reactTag:number, params: CameraLivePreviewParams): Promise<string> {
  return tuya.openLivePreview(reactTag, params);
}

export function getCameraTumbnail(): Promise<any> {
  return tuya.getTumbnail();
}

export function getHomeDetails(): Promise<any> {
  return tuya.getHomeDetails();
}

export function getCameraDetails(params: CameraDetailsParams): Promise<any> {
  return tuya.getCameraDetails(params);
}

export function changeCameraIndicatorStatus(params: CameraDetailsParams): Promise<any> {
  return tuya.changeCameraIndicatorStatus(params);
}

export function changeCameraNightVision(params: CameraNightVisionParams): Promise<any> {
  return tuya.changeCameraNightVision(params);
}

