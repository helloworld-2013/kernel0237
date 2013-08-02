//
//  ViewController.m
//  HelloWorld
//
//  Created by Indra Gunawan on 1/8/13.
//  Copyright (c) 2013 Indra Gunawan. All rights reserved.
//

#import "ViewController.h"
#import "CalculatorVC.h"
#import "MasterDetailsVC.h"

@interface ViewController ()

@end

@implementation ViewController

- (IBAction)showCalculator
{
    CalculatorVC *vc = [[CalculatorVC alloc] initWithNibName:@"CalculatorVC" bundle:[NSBundle mainBundle]];
    [self.navigationController pushViewController:vc animated:YES];
}

- (IBAction)showMasterDetails
{
    MasterDetailsVC *vc = [[MasterDetailsVC alloc] initWithNibName:@"MasterDetailsVC" bundle:[NSBundle mainBundle]];
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    [self setTitle:@"Hello World"];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
