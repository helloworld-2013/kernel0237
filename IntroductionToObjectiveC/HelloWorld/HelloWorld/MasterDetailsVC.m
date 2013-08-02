//
//  MasterDetailsVC.m
//  HelloWorld
//
//  Created by Indra Gunawan on 1/8/13.
//  Copyright (c) 2013 Indra Gunawan. All rights reserved.
//

#import "MasterDetailsVC.h"
#import "TheDetailsVC.h"

@interface MasterDetailsVC ()

@end

@implementation MasterDetailsVC

@synthesize theNames,theTable;

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [theNames count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CELL_IDENTIFIER = @"TheCell";
    UITableViewCell *theCell;
    theCell = [theTable dequeueReusableCellWithIdentifier:CELL_IDENTIFIER];
    if (theCell == nil) {
        theCell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CELL_IDENTIFIER];
        theCell.textLabel.text = [theNames objectAtIndex:[indexPath row]];
    }
    return theCell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    TheDetailsVC *vc = [[TheDetailsVC alloc] initWithNibName:@"TheDetailsVC" bundle:[NSBundle mainBundle]];
    [vc setTheName:[theNames objectAtIndex:[indexPath row]]];
    [self.navigationController pushViewController:vc animated:YES];
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    [self setTitle:@"Master Details"];
    
    theNames = [[NSArray alloc] initWithObjects:@"Isaac Newton",@"Albert Einstein",@"Linus Torvalds",nil];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
