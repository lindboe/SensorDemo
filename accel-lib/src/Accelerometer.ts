import { NativeModules } from 'react-native'

const isTurboModuleEnabled = global.__turboModuleProxy != null;

const accelerometer = isTurboModuleEnabled ?
require("./NativeAccelerometer").default :
NativeModules.Accelerometer;

export default accelerometer;