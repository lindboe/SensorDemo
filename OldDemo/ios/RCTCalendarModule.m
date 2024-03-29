//
//  RCTCalendarModule.m
//  OldDemo
//
//  Created by Lizzi on 4/18/22.
//

#import <Foundation/Foundation.h>

// RCTCalendarModule.m
#import "RCTCalendarModule.h"
#import <React/RCTLog.h>

@implementation RCTCalendarModule

// RCT_EXPORT_METHOD macro will not be necessary with TM unless the method relies on RCT argument conversion; should avoid RCTConvert as macros will be discontinued
RCT_EXPORT_METHOD(createCalendarEvent:(NSString *)name
                  location:(NSString *)location
                  myCallback:(RCTResponseSenderBlock) callback)
{
  NSInteger eventId = 1;
  callback(@[name, location, @(eventId)]);
}

// To export a module named RCTCalendarModule
RCT_EXPORT_MODULE();

// Notes
// constantsToExport is even more complicated on iOS
// iOS doesn't have the note about TM making it so that constants are fetched every invocation?

@end
