/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * Generated with the TypeScript template
 * https://github.com/react-native-community/react-native-template-typescript
 *
 * @format
 */

import React from 'react';

import {SafeAreaView, NativeModules, Button} from 'react-native';

const NewModuleButton = () => {
  const {CalendarModule} = NativeModules;
  const onPress = () => {
    CalendarModule.createCalendarEvent('testName', 'testLocation');
  };

  return (
    <Button
      title="Click to invoke your native module!"
      color="#841584"
      onPress={onPress}
    />
  );
};

const App = () => {
  return (
    <SafeAreaView>
      <NewModuleButton />
    </SafeAreaView>
  );
};

export default App;
