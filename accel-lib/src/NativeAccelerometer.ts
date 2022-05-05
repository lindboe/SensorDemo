import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  addListener(event: string): void;
  removeListeners(count: number): void;
}
export default (TurboModuleRegistry.get<Spec>(
  'Accelerometer'
));
