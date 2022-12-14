#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(RiveSplashScreen, NSObject)

RCT_EXTERN_METHOD(showSplash:(NSString *)splashScreen inRootView:(UIView*)rootView withArtboard:(NSString *)artboardName withRiveFile:(NSString *)fileName withStateMachine:(NSString *)stateMachine)

RCT_EXTERN_METHOD(showSplashImage:(NSString *)splashScreen inRootView:(UIView*)rootView withSplashImage:(NSString *)splashImageName)

RCT_EXTERN_METHOD(show)

RCT_EXTERN_METHOD(hide)

+ (BOOL)requiresMainQueueSetup
{
  return YES;
}

@end
