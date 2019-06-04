#import "MathTexPlugin.h"
#import <math_tex/math_tex-Swift.h>

@implementation MathTexPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftMathTexPlugin registerWithRegistrar:registrar];
}
@end
