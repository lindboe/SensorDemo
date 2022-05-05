import React from 'react';

import {SafeAreaView, Text} from 'react-native';
import Demo from 'accel-lib/src/index';

const {Stateful} = Demo;

function App() {
  return (
    <SafeAreaView style={{height: '100%', width: '100%'}}>
      <Stateful />
      <Text>Old Architecture</Text>
    </SafeAreaView>
  );
}

export default App;
