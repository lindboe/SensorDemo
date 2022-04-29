/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * Generated with the TypeScript template
 * https://github.com/react-native-community/react-native-template-typescript
 *
 * @format
 */

import React, {useEffect, useReducer, useRef, useState} from 'react';

import {
  SafeAreaView,
  NativeModules,
  Button,
  ScrollView,
  Image,
  Text,
  NativeSyntheticEvent,
  NativeScrollEvent,
  TextInput,
  View,
} from 'react-native';

const img = require('./src/asset/ruler.png');

const ScrollMeasure = ({reducerState: {value, lastMoveWasText}, dispatch}) => {
  const updateForScroll = offset => dispatch({type: 'scroll', offset});
  const [internal, setInternal] = useState(0);
  const onScroll = (event: NativeSyntheticEvent<NativeScrollEvent>) => {
    updateForScroll(event.nativeEvent.contentOffset.x);
    setInternal(event.nativeEvent.contentOffset.x);
  };

  const momentum = () => dispatch({type: 'animend'});

  const scrollRef = useRef<ScrollView>(null);
  useEffect(() => {
    if (scrollRef.current && lastMoveWasText) {
      console.log('MOVING TO', value);
      scrollRef.current.scrollTo({x: value, y: 0, animated: true});
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [value]);
  return (
    <>
      <View style={[comparisonViewStyle, {opacity: opacityFn(internal)}]} />
      <Text>X Offset: {internal}</Text>
      <ScrollView
        ref={scrollRef}
        horizontal
        onMomentumScrollEnd={momentum}
        bounces={false}
        alwaysBounceHorizontal={false}
        onScroll={onScroll}
        scrollEventThrottle={16}>
        <Image source={img} style={{width: 20000}} />
      </ScrollView>
    </>
  );
};

const comparisonViewStyle = {
  height: 50,
  width: '100%',
  backgroundColor: 'black',
};

const OFFSET_MAX = 19610;
const opacityFn = (value: number) => (value === 0 ? 0 : value / OFFSET_MAX);

const ControlledInput = ({value, setValue}: {value: number}) => {
  const onChangeText = (input: string) => {
    setValue(input ? parseInt(input, 10) : 0);
  };
  return (
    <>
      <TextInput value={value.toString()} onChangeText={onChangeText} />
      <View style={[comparisonViewStyle, {opacity: opacityFn(value)}]} />
    </>
  );
};

const NewModuleButton = () => {
  const {CalendarModule} = NativeModules;
  const onPress = () => {
    CalendarModule.createCalendarEvent(
      'testName',
      'testLocation',
      (name, location, id) => console.log('invoked, got', name, location, id),
    );
  };

  return (
    <Button
      title="Click to invoke your native module!"
      color="#841584"
      onPress={onPress}
    />
  );
};

type ReducerState = {
  value: number;
  lastMoveWasText: boolean;
  animInProgress: boolean;
};

type ScrollEvent = {
  type: 'scroll';
  offset: number;
};

type TextInputEvent = {
  type: 'text';
  value: number;
};

type EndEvent = {
  type: 'animend';
};

const reducer = (state, action) => {
  console.log('ACTION', action);
  switch (action.type) {
    case 'scroll':
      return state.lastMoveWasText
        ? {...state, animInProgress: true, lastMoveWasText: false}
        : {...state, value: action.offset, lastMoveWasText: false};
    case 'text':
      return {
        ...state,
        value: action.value,
        lastMoveWasText: true,
        animInProgress: true,
      };
    case 'animend':
      return {...state, animInProgress: false};
    default:
      return state;
  }
};

const App = () => {
  const [state, dispatch] = useReducer(reducer, {
    value: 0,
    lastMoveWasText: undefined,
  });
  console.log('STATE', state);
  const {value, lastMoveWasText} = state;
  const updateForType = value => dispatch({type: 'text', value});
  return (
    <SafeAreaView>
      <Text>Old Architecture</Text>
      <ControlledInput value={value} setValue={updateForType} />
      <ScrollMeasure reducerState={state} dispatch={dispatch} />
    </SafeAreaView>
  );
};

export default App;
