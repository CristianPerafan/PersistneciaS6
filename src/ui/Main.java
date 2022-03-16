package ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
		deserializeInformation();

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
				"(2) Show billboards\n"+
				"(3) Add a billboard\n"+
				"(4) Save the information\n"+
				"(5) Export report of dangerousness\n"+
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
			break;
		case 3:
			addABillboard();
			break;
		case 4:
			System.out.println("Saving....");
			serializeBillboard(controller.getListBillboards());
			System.out.println("The information has been saved!!!");
			break;
		case 5:
			generateReportDangerousness();
			break;
		default:
			System.out.println("No valid option");
			break;
		}
	}
	
	public void deserializeInformation() {
		File file = new File(".\\files\\billb.txt");
		
		if(file.exists()) {
			try {
				FileReader fr = new FileReader(file);
				BufferedReader reader = new BufferedReader(fr);
				String line = null;
				
				while((line = reader.readLine())!= null) {
					String[] parts = line.split("\\++");
					if(parts.length == 4) {
						double width = Double.parseDouble(parts[0]);
						double height = Double.parseDouble(parts[1]);
						boolean state = true;
						
						if(parts[2].equals("false")) {
							state = false;
						}
						else if(parts[2].equals("true")) {
							state = true;
						}
						
						String company = parts[3];
						
						Billboards obj = new Billboards(width,height,state,company);
						
						controller.addABillboard(obj);
					}
					
				}
				reader.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void importCsvData() throws PersistenciaException{
		
		System.out.println("¨*** IMPORT CSV DATA ***\n"+
				"Please enter the absolute path of the CSV file:");
		String absolutePath  = sc.nextLine();
		
		File file = new File(absolutePath);
		
		if(file.exists()) {
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
				
				
				System.out.println("The data has been successfully exported");
				
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
		}
		else {
			System.out.println("The file does not exist or the absolute path is not the correct!!!");
		}
		
	}
	
	public void saveInformation() {
		serializeBillboard(controller.getListBillboards());
	}
	
	public void showBillboards() {
		System.out.println(controller.toString());
		
	}
	
	public void addABillboard() {
		System.out.println("Please, enter the information of the billboard, the information must be separeted by '++' \n"+
				"Example:\n"+ 
				"200++300++true++Mister Wings");
		
		
		String input = sc.nextLine();
		
		
		String [] parts = input.split("\\++");
	
		addABillboardToList(parts);
	
	}
	
	
	
	
	public void serializeBillboard(List<Billboards> billboards) {
		
		File file = new File(".\\files\\billb.txt");
		
		try {
			FileWriter fw = new FileWriter(file);
			
			for(int i = 0;i<billboards.size();i++) {
				String str = billboards.get(i).toString();
	            fw.write(str);
	            if(i < billboards.size()-1)//This prevent creating a blank like at the end of the file**
	                fw.write("\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addABillboardToList(String [] parts) {
		
		double width = Double.parseDouble(parts[0]);
		double heigth = Double.parseDouble(parts[1]);
		boolean state = true;
		if(parts[2].equals("true")) {
			state = true;
		}
		else if(parts[2].equals("false")) {
			state = false;
		}
		
		String name = parts[3];
		
		Billboards obj = new Billboards(width,heigth,state,name);
		
		controller.addABillboard(obj);
	}
	
	public void generateReportDangerousness() {
		String out = controller.reportDangerousness();
		System.out.println(out);
	}


}
