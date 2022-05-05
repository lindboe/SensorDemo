
import React from 'react';

import {SafeAreaView, Text} from 'react-native';
import Demo from 'accel-lib/src/index';

const {Stateful} = Demo;

const App = () => {
  return (
    <SafeAreaView style={{height: '100%', width: '100%'}}>
      <Stateful />
      <Text>New Architecture, 0.69.0-rc0</Text>
    </SafeAreaView>
  );
};

export default App;