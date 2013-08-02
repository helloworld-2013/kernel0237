//
//  CalculatorVC.m
//  HelloWorld
//
//  Created by Indra Gunawan on 1/8/13.
//  Copyright (c) 2013 Indra Gunawan. All rights reserved.
//

#import "CalculatorVC.h"

@interface CalculatorVC ()

@end

@implementation CalculatorVC

@synthesize display;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)performOperation:(NSString *)operation
{
    if ([@"+" isEqualToString:operation]){
        operandInMemory += operandDisplayed;
    } else if ([@"-" isEqualToString:operation]){
        operandInMemory -= operandDisplayed;
    }
}

- (IBAction)digitPressed:(UIButton*)sender
{
    NSString *digit = [[sender titleLabel] text];
    if (!isDigitPressed) {
        [display setText:digit];
        isDigitPressed = YES;
    } else {
        [display setText:[[display text] stringByAppendingString:digit]];
    }
}

- (IBAction)operatorPressed:(UIButton*)sender
{
    NSString *operation = [[sender titleLabel] text];
    
    operandDisplayed = [[display text] doubleValue];
    
    if (isFirstCalculation) {
        operandInMemory = operandDisplayed;
        isFirstCalculation = NO;
    } else {
        [self performOperation:previousOperation];
        operandDisplayed = operandInMemory;
    }
    
    previousOperation = operation;
    
    if ([@"=" isEqualToString:operation]) {
        isFirstCalculation = YES;
    } else if ([@"C" isEqualToString:operation]) {
        isFirstCalculation = YES;
        operandInMemory = 0;
        operandDisplayed = 0;
        previousOperation = @"";
    }
    
    isDigitPressed = NO;
    
    [display setText:[NSString stringWithFormat:@"%g", operandDisplayed]];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    [self setTitle:@"Calculator"];
    
    operandDisplayed = 0;
    operandInMemory = 0;
    
    isDigitPressed = NO;
    isFirstCalculation = YES;
    
    previousOperation = @"";
    
    [display setText:[NSString stringWithFormat:@"%g", operandDisplayed]];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
