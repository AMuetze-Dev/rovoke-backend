package de.amit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import de.amit.controller.recipebook.IngredientController;
import de.amit.controller.recipebook.RecipeController;
import de.amit.controller.recipebook.RecipeIngredientController;
import de.amit.controller.recipebook.UnitController;
import de.amit.model.UpdateObject;
import de.amit.model.recipebook.AttributeObject;
import de.amit.model.recipebook.Ingredient;
import de.amit.model.recipebook.Recipe;
import de.amit.model.recipebook.RecipeIngredient;

public class ExcelHandler {

	private class CellData {
		private final int column;
		private final int row;

		CellData(int column, int row) {
			this.column = column;
			this.row = row;
		}

		@Override
		public String toString() {
			return (char) (column + 65) + "" + row;
		}

	}

	private final String rootDirectory = "C:\\Users\\aaron\\Desktop\\Rezepte";
	private Map<CellData, String> data;

	public ExcelHandler() {
		final File[] fs = listFiles();
		for (final File f : fs)
			writeRecipe(f);

	}

	private void addIngredients(List<String[]> ingredients) {
		try {
			List<Ingredient> existingIngredients = new IngredientController().getAll();
			final RecipeIngredient[] recipeIngredients = new RecipeIngredient[ingredients.size()];

			for (int i = 0; i < ingredients.size(); i++) {
				int id = -1;
				for (final Ingredient existingIngredient : existingIngredients)
					if (existingIngredient.getName().equalsIgnoreCase(ingredients.get(i)[3])) {
						id = existingIngredient.getId() - 1;
						break;
					}
				if (id != -1)
					recipeIngredients[i] = new RecipeIngredient(-1, ingredients.get(i)[0],
							Double.parseDouble(ingredients.get(i)[1]), getUnit(ingredients.get(i)[2]), id);
				if (id == -1) {
					final UpdateObject<?>[] uo = new UpdateObject[4];
					uo[0] = new UpdateObject<>("name", ingredients.get(i)[3]);
					uo[1] = new UpdateObject<>("protein", 0);
					uo[2] = new UpdateObject<>("carbohydrates", 0);
					uo[3] = new UpdateObject<>("fat", 0);
					new IngredientController().add(uo);
					i--;
					existingIngredients = new IngredientController().getAll();
				}
			}
			new RecipeIngredientController().add(recipeIngredients);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}

	private String getCellValue(char column, int row) {
		String rowData = data.get(new CellData(column - 65, row));
		if (rowData == null)
			rowData = "";
		return rowData;
	}

	private int getUnit(String unitName) {
		try {
			final List<AttributeObject> units = new UnitController().getAll();
			int id = -1;
			for (final AttributeObject ao : units)
				if (ao.getValue().equals(unitName)) {
					id = ao.getId() - 1;
					break;
				}
			if (id != -1)
				return id;
			new UnitController().add(new UpdateObject<>("name", unitName));
			return getUnit(unitName);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public File[] listFiles() {
		return new File(rootDirectory).listFiles(x -> x.getName().substring(x.getName().length() - 5).equals(".xlsx"));
	}

	private void writeRecipe(File f) {
		try {
			final FileInputStream fis = new FileInputStream(new File(f.getPath()));
			final Workbook workbook = new XSSFWorkbook(fis);
			final Sheet sheet = workbook.getSheetAt(0);

			data = new TreeMap<>((cd1, cd2) -> {
				int out = Integer.compare(cd1.row, cd2.row);
				if (out == 0)
					out = Integer.compare(cd1.column, cd2.column);
				return out;
			});

			for (final Row row : sheet)
				for (final Cell cell : row) {
					String out = "";
					out = switch (cell.getCellType()) {
					case STRING -> cell.getRichStringCellValue().getString();
					case NUMERIC ->
						(DateUtil.isCellDateFormatted(cell) ? cell.getDateCellValue() : cell.getNumericCellValue())
								+ "";
					case BOOLEAN -> cell.getBooleanCellValue() + "";
					case FORMULA -> cell.getCellFormula() + "";
					default -> "";
					};
					if (!out.equals(""))
						data.put(new CellData(cell.getColumnIndex(), cell.getRowIndex() + 1), out);
				}
			workbook.close();

			final String name = getCellValue('B', 1);
			final int preparation = (int) Double.parseDouble(getCellValue('B', 2));
			final int cooking = (int) Double.parseDouble(getCellValue('B', 3));
			String tmp = getCellValue('B', 4);

			int difficulty = -1;
			if (tmp.equalsIgnoreCase("Einfach"))
				difficulty = 1;
			else if (tmp.equalsIgnoreCase("Normal"))
				difficulty = 3;
			else if (tmp.equalsIgnoreCase("Schwierig") || tmp.equalsIgnoreCase("Schwer"))
				difficulty = 5;
			else
				System.err.println("Schwierigkeit: " + tmp);

			tmp = getCellValue('B', 5);
			int kind = -1;
			if (tmp.equalsIgnoreCase("Süß"))
				kind = 4;
			else if (tmp.equalsIgnoreCase("Herzhaft"))
				kind = 2;
			else if (tmp.equalsIgnoreCase("Beilage"))
				kind = 5;
			else
				System.err.println("Art des Gerichts: " + tmp);

			final List<String[]> ingredients = new ArrayList<>();
			for (int j = 9; !getCellValue('D', j).equals(""); j++) {
				final String[] ingredient = new String[4];
				for (int k = 0; k < 4; k++)
					ingredient[k] = getCellValue((char) (65 + k), j);
				ingredients.add(ingredient);
			}

			final List<String> instructions = new ArrayList<>();
			for (int j = 2; !getCellValue('F', j).equals(""); j++)
				instructions.add(getCellValue('F', j));

			final Recipe recipe = new Recipe(-1, name, difficulty, preparation, cooking, kind,
					instructions.toArray(new String[0]));
			new RecipeController().addRecipe(recipe);
			addIngredients(ingredients);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
