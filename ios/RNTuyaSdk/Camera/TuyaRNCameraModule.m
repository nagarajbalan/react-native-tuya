
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
#import "TYDemoCameraViewController.h"
#import "TPDemoUtils.h"
#import "UIView+React.h"
#import "RCTUIManager.h"

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
@synthesize bridge = _bridge;
RCT_EXPORT_MODULE(TuyaCameraModule)

RCT_EXPORT_METHOD(testFunction){
  RCTLogInfo(@"testFunction -> Printed from ios native");
}

RCT_EXPORT_METHOD(openLivePreview: (nonnull NSNumber *)reactTag passParams:(NSDictionary *)passedParams ) {
  RCTLogInfo(@"openLivePreview -> Printed from ios native");
  NSLog(@"%@", passedParams.tysdk_JSONString);
   
  NSString *devId = passedParams[@"devId"];
  NSLog(@"%@", devId);
  UIViewController *vc = [[TYDemoCameraViewController alloc] initWithDeviceId:devId];
  RCTUIManager *uiManager = _bridge.uiManager;
  dispatch_async(uiManager.methodQueue, ^{
      [uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {
            UIView *view = viewRegistry[reactTag];
            UIViewController *viewController = (UIViewController *)view.reactViewController;
                  
      }];
  });
}

@end
