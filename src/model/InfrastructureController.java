package model;

import java.util.ArrayList;

public class InfrastructureController {
	
	private ArrayList<Billboards> billboards;

	public InfrastructureController(){
		billboards = new ArrayList<Billboards>();
	}
	
	public void addABillboard(Billboards billboard) {
		billboards.add(billboard);
	}
	
	public String toString() {
		String out = "";
		for(int i =0;i<billboards.size();i++) {
			if(i == 0) {
				out += "w          h      inUse       Brand\n";
			}
			
			out += billboards.get(i).getWidth()+"    ";
			out += billboards.get(i).getHeight()+"    ";
			out += billboards.get(i).getState()+"    ";
			out += billboards.get(i).getCompanyName()+"    ";
			
			
			out += "\n";
			
			if(i == (billboards.size()-1)) {
				out += "\n";
				out += "Total : "+billboards.size();
			}
		}
		
		return out;
	}

}
