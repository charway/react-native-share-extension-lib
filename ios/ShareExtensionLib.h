#import <UIKit/UIKit.h>
#import <React/RCTBridgeModule.h>

@interface ShareExtensionLib : UIViewController <RCTBridgeModule>
- (UIView*) shareView;
@end
