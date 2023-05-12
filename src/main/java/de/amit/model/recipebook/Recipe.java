package de.amit.model.recipebook;

import java.io.Serializable;

public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String title;
    private int difficulty;
    private int preparation;
    private int cooking;
    private int kind;
    private int ingredients;
    private String[] instructions;

    public Recipe() {
    }

    public Recipe(int id, String title, int difficulty, int preparation, int cooking, int kind, int ingredients,
            String[] instructions) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.preparation = preparation;
        this.cooking = cooking;
        this.kind = kind;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public int getCooking() {
        return cooking;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getId() {
        return id;
    }

    public int getIngredients() {
        return ingredients;
    }

    public String[] getInstructions() {
        return instructions;
    }

    public int getKind() {
        return kind;
    }

    public int getPreparation() {
        return preparation;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCooking(int cooking) {
        this.cooking = cooking;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setIngredients(int ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstructions(String[] instructions) {
        this.instructions = instructions;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public void setPreparation(int preparation) {
        this.preparation = preparation;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
