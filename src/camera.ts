import { NativeModules } from 'react-native';

const tuya = NativeModules.TuyaCameraModule;

export function openCameraLivePreview() {
  return tuya.openLivePreview();
}
