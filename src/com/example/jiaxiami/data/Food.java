package com.example.jiaxiami.data;

public class Food {

	public int ID;
	public String Name;
	public String Addr;
	public String Tel;
	public int money;
	public double lon; //經度
	public double lat; //緯度
	
	public Food (int ID, String Name, String Addr, String Tel, int money) {
		this.ID = ID;
		this.Name = Name;
		this.Addr = Addr;
		this.Tel = Tel;
		this.money = money;
	}
	
}
