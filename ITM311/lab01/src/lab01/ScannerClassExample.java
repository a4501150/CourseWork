package lab01;

import java.util.Scanner;
import java.lang.Math;

class ScannerClassExample
{
  //example of using the scanner class
  //which simplifies user input and output
  //this is part of the JDK 1.8.0_45 compiler

public static void main(String args[])	
{
  //declare a Scanner class object
  @SuppressWarnings("resource")
Scanner sc = new Scanner(System.in);
	
  //prompt user for their name
  System.out.println("please enter your name");
	
  //declare a local variable and read the integer
  String name = sc.nextLine();
	  
  //variable declaration 
  float w=0,x=0,y=0;
  
  //prompt user for a number and read the floats
  System.out.println("please enter first float");
  w = sc.nextFloat();
  
//prompt user for a number and read the floats
  System.out.println("please enter second float");
  x = sc.nextFloat();
  
//prompt user for a number and read the floats
  System.out.println("please enter third float");
  y = sc.nextFloat();
  
  //display the name back to the user
  System.out.println("hello " + name);
  
  //display the sum to the user
  System.out.println("sum of x + y is " + (x+y));
  
  //display the subtraction of y from x;
  System.out.println("subtraction is " + (x-y));
  
  //Assign the product of x and y to the variable w;  
  w = x * y;
  
  //display the quotient of x by y
  System.out.println("the quotient of x by y is " + (x/y));
  
  //Return the modulus of x and y and assign the result to the variable w.
  w = x % y;
  System.out.println("the modulus of x and y is " + w);
	
  //display the quotient of y by x with the result assigned to the variable z
  System.out.println(" the quotient of y by x is " + (y/x));
  @SuppressWarnings("unused")
float z = y/x;
  
  // Return the result of raising the number x by the number y (exponent).
  System.out.println("the result of raising the number x by the number y is " + Math.pow(x, y));

}
} 