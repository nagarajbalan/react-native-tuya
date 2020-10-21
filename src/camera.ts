import { NativeModules } from 'react-native';

const tuya = NativeModules.TuyaCameraModule;

export type CameraLivePreviewParams = {
  countryCode: string;
  uid: string;
  passwd: string;
  devId: string;
};

export function testFunction() {
  return tuya.testFunction();
}

export function openCameraLivePreview(reactTag:number, params: CameraLivePreviewParams): Promise<string> {
  return tuya.openLivePreview(reactTag, params);
}

export type CameraTumbnailParams = {
  devId: string;
};

export function getCameraTumbnail(params:CameraTumbnailParams): Promise<string> {
  return tuya.getTumbnail(params);
}

export function getHomeDetails(): Promise<any> {
  return tuya.getHomeDetails();
}
