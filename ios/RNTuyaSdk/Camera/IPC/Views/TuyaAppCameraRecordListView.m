//
//  TuyaAppCameraRecordListView.m
//  TuyaSDK
//
//  Created by Nagaraj Balan on 06/12/20.
//

#import "TuyaAppCameraRecordListView.h"
#import "TuyaAppCameraRecordCell.h"

@implementation TuyaAppCameraRecordListView

- (instancetype)initWithFrame:(CGRect)frame {
    if (self = [super initWithFrame:frame]) {
        CGRect subViewFrame = CGRectMake(0, 0, CGRectGetWidth(frame), CGRectGetHeight(frame));
        _emptyDataView = [[TuyaAppCameraEmptyDataView alloc] initWithFrame:subViewFrame];
        _emptyDataView.imageView.image  = [UIImage imageNamed:@"ty_camera_record_empty"];
        _emptyDataView.textLabel.text   = NSLocalizedString(@"ipc_playback_no_records_today", @"");
        [self addSubview:_emptyDataView];
        
        _tableView = [[UITableView alloc] initWithFrame:subViewFrame style:UITableViewStylePlain];
        [_tableView registerClass:[TuyaAppCameraRecordCell class] forCellReuseIdentifier:@"cameraRecordCell"];
        _tableView.delegate     = self;
        _tableView.dataSource   = self;
        _tableView.rowHeight    = 72;
        _tableView.estimatedRowHeight   = 72;
        [self addSubview:_tableView];
        _dataSource = @[];
    }
    return self;
}

- (void)setDataSource:(NSArray<TuyaAppCameraRecordModel *> *)dataSource {
    _dataSource = dataSource;
    _selectedIndexPath = nil;
    _tableView.hidden = _dataSource.count == 0;
    _emptyDataView.hidden = _dataSource.count != 0;
    if (_dataSource.count > 0) {
        [_tableView reloadData];
    }
}

#pragma mark - UITableViewDataSource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return _dataSource.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    TuyaAppCameraRecordCell *cell = [tableView dequeueReusableCellWithIdentifier:@"cameraRecordCell"];
    NSDictionary *timeslice = [self.dataSource objectAtIndex:indexPath.row];
    if ([self.delegate respondsToSelector:@selector(cameraRecordListView:presentCell:source:)]) {
        [self.delegate cameraRecordListView:self presentCell:cell source:timeslice];
    }
    return cell;
}

#pragma mark - UITableViewDelegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    if ([_selectedIndexPath isEqual:indexPath]) {
        return;
    }
    if (_selectedIndexPath) {
        [tableView deselectRowAtIndexPath:_selectedIndexPath animated:YES];
    }
    _selectedIndexPath = indexPath;
    if ([self.delegate respondsToSelector:@selector(cameraRecordListView:didSelectedRecord:)]) {
        NSDictionary *timeslice = [self.dataSource objectAtIndex:indexPath.row];
        [self.delegate cameraRecordListView:self didSelectedRecord:timeslice];
    }
}

@end


@implementation TuyaAppCameraEmptyDataView

#pragma mark - Accessor

- (void)layoutSubviews {
    [super layoutSubviews];
    [_imageView  sizeToFit];
    [_textLabel sizeToFit];
    CGFloat textSpaceToImage    = 12;
    CGFloat totalHeight         = _imageView.frame.size.height + _textLabel.frame.size.height + textSpaceToImage;
    CGFloat imageLeft = (self.frame.size.width - _imageView.frame.size.width) / 2;
    CGFloat imageTop = (self.frame.size.height - totalHeight) / 2;
    CGRect imageFrame = _imageView.frame;
    imageFrame.origin = CGPointMake(imageLeft, imageTop);
    _imageView.frame = imageFrame;
    
    CGFloat labelTop = CGRectGetMaxY(imageFrame);
    _textLabel.frame = CGRectMake(0, labelTop, self.frame.size.width, 20);
}

- (UIImageView *)imageView {
    if (_imageView == nil) {
        _imageView = [[UIImageView alloc] init];
        [self addSubview:_imageView];
    }
    return _imageView;
}

- (UILabel *)textLabel {
    if (_textLabel == nil) {
        _textLabel = [[UILabel alloc] init];
        _textLabel.textColor = [UIColor blackColor];
        _textLabel.textAlignment = NSTextAlignmentCenter;
        [self addSubview:_textLabel];
    }
    return _textLabel;
}

@end
