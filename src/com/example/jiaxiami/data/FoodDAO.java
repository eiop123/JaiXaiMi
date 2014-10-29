package com.example.jiaxiami.data;

public interface FoodDAO {

	public int add(Food f);
	public Food[] getAll();
	public Food getFood(int ID);
	public void removeAll();
	public void delete(int ID);
	public void edit(Food f);
	Food[] search(String keyword);
}
