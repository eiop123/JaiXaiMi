package com.example.jiaxiami.data;

public class Food {

	public int ID;
	public String Name;
	public String Addr;
	public String Tel;
	public int money;
	private double lon; //經度
	private double lat; //緯度
	
	public Food (int ID, String Name, String Addr, String Tel, int money) {
		this.ID = ID;
		this.Name = Name;
		this.Addr = Addr;
		this.Tel = Tel;
		this.money = money;
		this.lon = lon; //TODO Incomplete
		this.lat = lat; //TODO Incomplete
	}
	
}
