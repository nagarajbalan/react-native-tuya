//
//  TuyaAppBaseView.m
//  TuyaSDK
//
//  Created by Nagaraj Balan on 06/12/20.
//
//

#import "TuyaAppBaseView.h"

NSString *UIKitLocalizedString(NSString *string) {
    NSBundle *UIKitBundle = [NSBundle bundleForClass:[UIApplication class]];
    return UIKitBundle ? [UIKitBundle localizedStringForKey:string value:string table:nil] : string;
}

@implementation TuyaAppBaseView

- (void)setTopLineHidden:(BOOL)showTopLine {
    _topLineHidden = showTopLine;
}

- (void)setBottomLineHidden:(BOOL)showBottomLine {
    _bottomLineHidden = showBottomLine;
}

- (void)layoutSubviews {
    [super layoutSubviews];
    
    [self removeAllSubviews];
    
    if (!_topLineHidden) {
        float lineIndent;
        if (_topLineIndent != 0) {
            lineIndent = _topLineIndent;
        } else {
            lineIndent = 0;
        }
        
        float lineWidth;
        if (_topLineWidth != 0) {
            lineWidth = _topLineWidth;
        } else {
            lineWidth = self.width - lineIndent;
        }
        
        UIColor *lineColor;
        if (_topLineColor != nil) {
            lineColor = _topLineColor;
        } else {
            lineColor = HEXCOLOR(0xDBDBDB);
        }
        
        [self addSubview:[TuyaAppViewUtil viewWithFrame:CGRectMake(lineIndent, 0, lineWidth, 0.5) color:lineColor]];
    }
    
    if (!_bottomLineHidden) {
        float lineIndent;
        if (_bottomLineIndent != 0) {
            lineIndent = _bottomLineIndent;
        } else {
            lineIndent = 0;
        }
        
        float lineWidth;
        if (_bottomLineWidth != 0) {
            lineWidth = _bottomLineWidth;
        } else {
            lineWidth = self.width - lineIndent;
        }
        
        UIColor *lineColor;
        if (_bottomLineColor != nil) {
            lineColor = _bottomLineColor;
        } else {
            lineColor = HEXCOLOR(0xDDDFE7);
        }
        
        [self addSubview:[TuyaAppViewUtil viewWithFrame:CGRectMake(lineIndent, self.height - 0.5, lineWidth, 0.5) color:lineColor]];
    }
}

@end
