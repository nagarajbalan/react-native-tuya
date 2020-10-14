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

export function openCameraLivePreview(params: CameraLivePreviewParams, reactTag:number): Promise<string> {
  return tuya.openLivePreview(params, reactTag);
}
