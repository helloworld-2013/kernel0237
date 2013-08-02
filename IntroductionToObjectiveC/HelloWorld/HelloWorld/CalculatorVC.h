//
//  CalculatorVC.h
//  HelloWorld
//
//  Created by Indra Gunawan on 1/8/13.
//  Copyright (c) 2013 Indra Gunawan. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CalculatorVC : UIViewController
{
    double operandDisplayed;
    double operandInMemory;
    
    BOOL isDigitPressed;
    BOOL isFirstCalculation;
    
    NSString *previousOperation;
}

@property (nonatomic, strong) IBOutlet UILabel *display;

@end
