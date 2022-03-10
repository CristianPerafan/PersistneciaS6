package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import exceptions.PersistenciaException;
import model.Billboards;
import model.InfrastructureController;

public class Main {
	
	private Scanner sc;
	private InfrastructureController controller;

	public Main() {
		sc = new Scanner(System.in);
		controller = new InfrastructureController();

	}
	
	public static void main(String [] args) {
		Main pc = new Main();
		
		System.out.println("Starting the App....");
		
		int optionMenu = 0;
		
		do {
			optionMenu = pc.showMenu();
			pc.executeOperation(optionMenu);
			
		}while(optionMenu != 0);
		
		
	}
	
	public int showMenu() {
		System.out.println("*** MENU ***\n"+
				"(1) Import CSV data\n"+
				"(2) Show biillboards\n"+
				"(0) Exit");
		int menuOption = sc.nextInt();
		sc.nextLine();
		
		return menuOption;
	}
	
	public void executeOperation(int option) {
		switch(option) {
		case 0:
			System.out.println("Bye see you soon");
			break;
		case 1:
			try {
				importCsvData();
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			showBillboards();
		default:
			break;
		}
	}
	
	public void importCsvData() throws PersistenciaException{
		
		System.out.println("¨*** IMPORT CSV DATA ***\n"+
				"Please enter the absolute path of the CSV file:");
		String absolutePath  = sc.nextLine();

		absolutePath = ".\\files\\Datos2.csv";
		
		File file = new File(absolutePath);
		
		if(file.exists()) {
			System.out.println("yes");
			try {
				FileReader fileReader = new FileReader(file);
				@SuppressWarnings("resource")
				BufferedReader reader = new BufferedReader(fileReader);
				String line = null;
				
				int column = 1;
				int numLine = 1;
				
				while((line = reader.readLine()) != null) {
					
					column = 1;
					
					String [] parts = line.split("|");
					
					int totalParts = parts.length;
					
					String value = "";
					
					
					//Attributes of the Billboard objects
					double width = 0;
					double high = 0;
					boolean state = true;
					String companyName = "";
					
					
					for(int i = 0;i<totalParts;i++) {
						
						/*
						If the characters are not equal to the 
						separator, they are concatenated
						*/
						
						if(!(parts[i].equals("|")) ) {
							value += parts[i];
						}
						
						if(parts[i].equals("|")) {
							

							if(!(numLine == 1)) {
								
								if(column==1) {
									width = Double.parseDouble(value);
									value = "";
	
								}
								
								if(column ==2) {
									high = Double.parseDouble(value);
									value = "";
									
								}
								
								if(column == 3) {
									
									if(value.equals("true")) {
										state = true;
									}
									else if(value.equals("false")) {
										state = false;
									}
									value = "";
									
								}
								
								column++;
							}
							
							
						//	
						} 
						
						if(i == (totalParts-1)) {
							companyName = value;
							value = "";
						}
						
						if(!(numLine == 1)) {
							if(i == (totalParts-1)) {
								Billboards obj = new Billboards(width,high,state,companyName);
								controller.addABillboard(obj);
							}
						}
							
					}
					
					numLine++;
					
					
				}
			
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else {
			System.out.println("The file does not exist or the absolute path is not the correct!!!");
		}
		
	}
	
	public void showBillboards() {
		System.out.println(controller.toString());
	}



}
