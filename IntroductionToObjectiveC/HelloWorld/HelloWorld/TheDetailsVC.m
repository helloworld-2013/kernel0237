//
//  TheDetailsVC.m
//  HelloWorld
//
//  Created by Indra Gunawan on 1/8/13.
//  Copyright (c) 2013 Indra Gunawan. All rights reserved.
//

#import "TheDetailsVC.h"

@interface TheDetailsVC ()

@end

@implementation TheDetailsVC

@synthesize theName, theLabel;

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
    [self setTitle:@"The Details"];
    
    if ([@"Isaac Newton" isEqualToString:theName]) {
        theLabel.text = @"Isaac Newton was an English physicist and mathematician who is widely regarded as one of the most influential scientists of all time. His book Principia formulated the laws of motion and universal gravitation that dominated scientists' view of the physical universe for the next three centuries.";
    } else if ([@"Albert Einstein" isEqualToString:theName]) {
        theLabel.text = @"Albert Einstein was a German-born theoretical physicist who developed the general theory of relativity. Near the beginning of his career, Einstein thought that Newtonian mechanics was no longer enough to reconcile the laws of classical mechanics with the laws of electromagnetic field.";
    } else if ([@"Linus Torvalds" isEqualToString:theName]) {
        theLabel.text = @"Linus Torvalds is a Finnish American software engineer, who was the principal force behind the development of the Linux Kernel. He later became the chief architect of the Linux kernel, and now act as the project's coordinator. As of 2006, approximately two percent of the Linux kernel was written by Torvalds himself.";
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
