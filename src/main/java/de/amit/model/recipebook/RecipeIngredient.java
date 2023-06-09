package de.amit.model.recipebook;

import java.io.Serializable;

public class RecipeIngredient implements Serializable {

	private static final long serialVersionUID = 1L;
	private String category;
	private double amount;
	private int unit;
	private int ingredientID;
	private int recipeID;

	public RecipeIngredient() {

	}

	public RecipeIngredient(int recipeID, String category, double amount, int unit, int ingredientID) {
		this.recipeID = recipeID;
		this.category = category;
		this.amount = amount;
		this.unit = unit;
		this.ingredientID = ingredientID;
	}

	public double getAmount() {
		return amount;
	}

	public String getCategory() {
		return category;
	}

	public int getIngredientID() {
		return ingredientID;
	}

	public int getRecipeID() {
		return recipeID;
	}

	public int getUnit() {
		return unit;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setIngredientID(int ingredientID) {
		this.ingredientID = ingredientID;
	}

	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

}
