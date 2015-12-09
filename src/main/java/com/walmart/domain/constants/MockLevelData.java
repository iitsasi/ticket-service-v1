/**
 * 
 */
package com.walmart.domain.constants;

/**
 * @author Sasidhara Marrapu
 *
 */
/*
 * Enumeration to hold the static values for Status
 */
public enum MockLevelData {
	
	ORCHESTRA(1,"Orchestra",25,50,100.00),
	MAIN(2,"Main",20,100,75.00),
	BALCONY1(3,"Balcony 1",15,100,50.00),
	BALCONY2(4,"Balcony 2",15,100,40.00);
	
	int id;
	String name;
	int noOFRows;
	int noOfCols;
	double price;
	
	MockLevelData(int id, String name, int noOFRows, int noOfCols, double price){
		this.id = id;
		this.name =  name;
		this.noOFRows = noOFRows;
		this.noOfCols = noOfCols;
		this.price = price;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the noOFRows
	 */
	public int getNoOFRows() {
		return noOFRows;
	}
	/**
	 * @return the noOfCols
	 */
	public int getNoOfCols() {
		return noOfCols;
	}
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	
	public MockLevelData getMockLevel(int id){
		for(MockLevelData data : MockLevelData.values()){
			if(data.getId() == id){
				return data;
			}
		}
		return null;
	}

}
