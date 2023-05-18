package de.amit.model.recipebook;

import java.io.Serializable;

public class Ingredient implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private double protein;
	private double carbohydrates;
	private double fat;

	public Ingredient() {
	}

	public Ingredient(int id, String name, double protein, double carbohydrates, double fat) {
		this.id = id;
		this.name = name;
		this.protein = protein;
		this.carbohydrates = carbohydrates;
		this.fat = fat;
	}

	public double getCarbohydrates() {
		return carbohydrates;
	}

	public double getFat() {
		return fat;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getProtein() {
		return protein;
	}

	public void setCarbohydrates(double carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

}
