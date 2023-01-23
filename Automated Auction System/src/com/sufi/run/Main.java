package com.sufi.run;

import java.util.Scanner;

import com.arijit.usecases.AdminUseCases;
import com.arijit.usecases.BuyerUseCases;
import com.arijit.usecases.SellerUseCases;

public class Main {
	
	static int num = 0;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		
		
		System.out.println("*****************************************************");
		System.out.println("👉👉👉👉 Welcome to Automated Auction System 👈👈👈👈");
		System.out.println("*****************************************************");
		
		run();
		
	}
	
	
	public static void run() {
		
		System.out.println();
		System.out.println("To Continue as Admin Enter 1 \r\n" 
				+ "To Continue as Buyer Enter 2 \r\n" 
				+ "To Continue as Seller Enter 3 \r\n"
				+ "To Exit Enter 4");
		System.out.println();		
		System.out.print("--------->  ");
		
		
		int input = sc.nextInt();
		
		
		if(input > 4 || input < 1) {
		
		System.out.println("Invalid Input !");
		}
		
		System.out.println("Redirecting to the page!");
		
		x();
		
		System.out.println();
		
		switch (input) {
			case 1 : {
				
				AdminUseCases.run();
			}
			break;
			
			case 2 : {
				
				BuyerUseCases.run();
			}
			break;
			
			case 3 : {
				
				SellerUseCases.run();
			}
			break;
			
			case 4 : {
				
				return;
			}
		}
	}
	
	public static void  x() {
	
		num++;
		
		try {
			
			System.out.print(".");
			Thread.sleep(1000);
		} 
		catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		 
		if(num == 3) {
			
			return;
		}
		else {
			
			x();
		}
		
		
	}
	
	
}
