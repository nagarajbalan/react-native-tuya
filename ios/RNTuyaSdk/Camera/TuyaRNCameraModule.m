
//
//  TuyaCameraApi.m
//  TuyaSdkTest
//
//  Created by 浩天 on 2020/10/13.
//  All rights reserved.
//

#import "TuyaRNCameraModule.h"
#import <TuyaSmartBaseKit/TuyaSmartBaseKit.h>
#import <React/RCTLog.h>
#import <CoreGraphics/CoreGraphics.h>
#import <UIKit/UIKit.h>
#import <CoreLocation/CoreLocation.h>
#import "TuyaRNUtils+Network.h"

#define kTuyaCoreModuleAppkey @""
#define kTuyaCoreModuleAppSecret @""
#define kTuyaCoreModuleParamLat @"lat"
#define kTuyaCoreModuleParamLon @"lon"

#define kTuyaCoreModuleUserDefaultLocation_lat @"ty_rn_lat"
#define kTuyaCoreModuleUserDefaultLocation_lon @"ty_rn_lon"

@interface TuyaRNCameraModule()<CLLocationManagerDelegate>

@property (nonatomic, strong) CLLocationManager *locationManager;

@end


@implementation TuyaRNCameraModule

RCT_EXPORT_MODULE(TuyaCameraModule)

RCT_EXPORT_METHOD(testFunction){
  RCTLogInfo(@"testFunction -> Printed from ios native");
}

RCT_EXPORT_METHOD(openLivePreview:(NSDictionary *)passedParams
                  findEventsWithResolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject
                  ){

  @try {
//    [device setValuesForKeysWithDictionary:passedParams];
//    AppDelegate *appDelegate = (AppDelegate *) [UIApplication sharedApplication].delegate;
//    [appDelegate goToLivePreview:device];
    
  } @catch (NSException *exception) {

  }
  

}

@end
