import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-rive-splash-screen' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

export default (NativeModules.RiveSplashScreen  ? NativeModules.RiveSplashScreen  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    ));
