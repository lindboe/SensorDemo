import React, {useEffect, useRef, useState} from 'react';

import {
  SafeAreaView,
  Button,
  Text,
  NativeEventEmitter,
  EmitterSubscription,
} from 'react-native';
import Accelerometer from 'accel-lib/src/index';

function AccelerometerObserver() {
  const [shouldObserve, setShouldObserve] = useState(true);
  const eventListener = useRef<EmitterSubscription | undefined>(undefined);
  const xData = useRef({lastX: undefined, xChanged: false});
  const [direction, setDirection] = useState(undefined);
  // instead of changed/not changed, try moved left/moved right

  useEffect(() => {
    if (shouldObserve) {
      console.log('STARTING');
      const eventEmitter = new NativeEventEmitter(Accelerometer);
      eventListener.current = eventEmitter.addListener(
        'Accelerometer',
        event => {
          console.log(event);
          if (event.x === 0) {
            setDirection('Not moving');
          } else {
            setDirection('moving');
          }
        },
      );
      return () => eventListener.current?.remove();
    } else if (!shouldObserve) {
      eventListener.current?.remove();
    }
  }, [shouldObserve]);

  return (
    <>
      <Button title="start events" onPress={() => setShouldObserve(true)} />
      <Button title="stop events" onPress={() => setShouldObserve(false)} />
      <Text>X Changing: {direction}</Text>
    </>
  );
}

const App = () => {
  return (
    <SafeAreaView>
      <AccelerometerObserver />
      <Text>New Architecture</Text>
    </SafeAreaView>
  );
};

export default App;
