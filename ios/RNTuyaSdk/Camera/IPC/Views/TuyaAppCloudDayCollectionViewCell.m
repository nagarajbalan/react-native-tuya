//
//  TuyaAppCloudDayCollectionViewCell.m
//  TuyaSDK
//
//  Created by Nagaraj Balan on 06/12/20.
//

#import "TuyaAppCloudDayCollectionViewCell.h"

@implementation TuyaAppCloudDayCollectionViewCell

- (void)layoutSubviews {
    [super layoutSubviews];
    self.textLabel.frame = self.contentView.bounds;
}

- (void)setSelected:(BOOL)selected {
    [super setSelected:selected];
    if (selected) {
        self.textLabel.font = [UIFont boldSystemFontOfSize:12];
    }else {
        self.textLabel.font = [UIFont systemFontOfSize:12];
    }
}

- (UILabel *)textLabel {
    if (!_textLabel) {
        _textLabel = [[UILabel alloc] init];
        _textLabel.font = [UIFont systemFontOfSize:12];
        _textLabel.textAlignment = NSTextAlignmentCenter;
        [self.contentView addSubview:_textLabel];
    }
    return _textLabel;
}

@end
