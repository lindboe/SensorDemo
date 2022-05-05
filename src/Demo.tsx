
import React, {useEffect, useReducer, useRef, useState} from 'react';
import Accelerometer from './Accelerometer';

import {
  View,
  Button,
  Text,
  NativeEventEmitter,
  EmitterSubscription,
} from 'react-native';
const eventEmitter = new NativeEventEmitter(Accelerometer);

const NOT_MOVING = 'Not moving';
const LEFT = 'Left';
const RIGHT = 'Right';

type XDirection = typeof NOT_MOVING | typeof LEFT | typeof RIGHT;

function xDirection(rawX: number): XDirection {
  const rounded = Math.round(rawX)
  if (rounded === 0) {
    return NOT_MOVING
  } else if (rounded > 0) {
    return RIGHT;
  } else {
    return LEFT;
  }
}

type ArrowProps = {
  direction: XDirection
}

const symbols = {
  [LEFT]: '<',
  [RIGHT]: '>',
  [NOT_MOVING]: '0',
}

const colors = {
  [LEFT]: 'red',
  [RIGHT]: 'green',
  [NOT_MOVING]: 'yellow',
}

function Arrow({direction}: ArrowProps) {
  const style = {width: '100%', flex: 1, backgroundColor: 'black', justifyContent: 'center', alignItems: 'center'} as const;
  const textStyle = {fontSize: 200, color: colors[direction]}
  return <View style={style}>
    <Text style={textStyle}>{symbols[direction]}</Text>
  </View>
}

export function Stateful() {
  const [shouldObserve, setShouldObserve] = useState(false);
  const eventListener = useRef<EmitterSubscription | undefined>(undefined);
  const [data, setData] = useState<number | undefined>(undefined);
  const [direction, setDirection] = useState<XDirection>(NOT_MOVING);
  // instead of changed/not changed, try moved left/moved right

  useEffect(() => {
    if (shouldObserve) {
      console.log('STARTING');
      eventListener.current = eventEmitter.addListener(
        'Accelerometer',
        event => {
          console.log(event);
          setData(event.x);
          setDirection(xDirection(event.x))
        },
      );
      return () => eventListener.current?.remove();
    } else if (!shouldObserve) {
      eventListener.current?.remove();
    }
  }, [shouldObserve]);

  return (
    <View style={{flex: 1, width: '100%'}}>
      <Button title="start events" onPress={() => setShouldObserve(true)} />
      <Button title="stop events" onPress={() => setShouldObserve(false)} />
      <Text>X Acceleration: {data} </Text>
      <Text>X Changing: {direction}</Text>
      <Arrow direction={direction} />
    </View>
  );
}