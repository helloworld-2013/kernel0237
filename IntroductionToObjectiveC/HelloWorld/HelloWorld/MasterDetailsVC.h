//
//  MasterDetailsVC.h
//  HelloWorld
//
//  Created by Indra Gunawan on 1/8/13.
//  Copyright (c) 2013 Indra Gunawan. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MasterDetailsVC : UIViewController<UITableViewDelegate, UITableViewDataSource>

@property (nonatomic, strong) NSArray *theNames;
@property (nonatomic, strong) IBOutlet UITableView *theTable;

@end
