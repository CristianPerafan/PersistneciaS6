package model;

public class Billboards {
	
	private double height;
	private double width;
	private boolean state;
	private String companyName;
	private double area;

	public Billboards(double width,double height, boolean state, String companyName) {
		this.height = height;
		this.width = width;
		this.state = state;
		this.companyName = companyName;
		area = height*width;
	}
	

	public double getHeight() {
		return height;
	}


	public void setheight(double height) {
		this.height = height;
	}


	public double getWidth() {
		return width;
	}


	public void setWidth(double width) {
		this.width = width;
	}


	public boolean getState() {
		return state;
	}


	public void setState(boolean state) {
		this.state = state;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public double getArea() {
		return area;
	}
	
	public void setArea(double area) {
		this.area = area;
	}


	@Override
	public String toString() {
		String out = "";
		
		out += width+"++";
		out += height+"++";
		out += state+"++";
		out += companyName;
		
		return out;
	}
	
	public String toStringReport() {
		String out = "";
		out = " Billboard "+"< "+companyName+" >"+ "with area"+area+"\n";
		return out;
	}
	
	
	
	

	


	

	



}
