package ch.sheremet.katarina.backingapp.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ch.sheremet.katarina.backingapp.model.BakingStep;
import ch.sheremet.katarina.backingapp.model.Ingredient;
import ch.sheremet.katarina.backingapp.model.Recipe;

public class RecipeParseJsonUtil {

    private static final String TAG = RecipeParseJsonUtil.class.getSimpleName();
    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "name";
    private static final String RECIPE_INGREDIENTS = "ingredients";
    private static final String RECIPE_STEPS = "steps";
    private static final String RECIPE_SERVINGS = "servings";
    private static final String RECIPE_IMAGE = "image";

    private static final String STEP_ID = "id";
    private static final String STEP_SHORT_DESCRIPTION = "shortDescription";
    private static final String STEP_DESCRIPTION = "description";
    private static final String STEP_VIDEO_URL = "videoURL";
    private static final String STEP_THUMBNAIL_URL = "thumbnailURL";

    private static final String INGREDIENT_QUANTITY = "quantity";
    private static final String INGREDIENT_MEASURE = "measure";
    private static final String INGREDIENT_NAME = "ingredient";

    public static List<Recipe> parseRecipes(final String recipeJson) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            JSONArray recipesJsonArray = new JSONArray(recipeJson);
            for (int i = 0; i < recipesJsonArray.length(); i++) {
                JSONObject recipeObject = recipesJsonArray.getJSONObject(i);
                Recipe recipe = new Recipe();
                if (recipeObject.has(RECIPE_ID)) {
                    recipe.setId(recipeObject.getInt(RECIPE_ID));
                }
                if (recipeObject.has(RECIPE_NAME)) {
                    recipe.setName(recipeObject.getString(RECIPE_NAME));
                }
                if (recipeObject.has(RECIPE_INGREDIENTS)) {
                    recipe.setIngredients(parseIngredientsJson(recipeObject.getJSONArray(RECIPE_INGREDIENTS)));
                }
                if (recipeObject.has(RECIPE_STEPS)) {
                    recipe.setBakingSteps(parseBakingStepsJson(recipeObject.getJSONArray(RECIPE_STEPS)));
                }
                if (recipeObject.has(RECIPE_SERVINGS)) {
                    recipe.setServings(recipeObject.getInt(RECIPE_SERVINGS));
                }
                if (recipeObject.has(RECIPE_IMAGE)) {
                    recipe.setImage(recipeObject.getString(RECIPE_IMAGE));
                }
                Log.d(TAG, recipe.toString());
                recipes.add(recipe);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing json", e);
        }
        return recipes;
    }

    private static List<BakingStep> parseBakingStepsJson(final JSONArray bakingStepJsonArray) throws JSONException {
        List<BakingStep> bakingSteps = new ArrayList<>();
        for (int i = 0; i < bakingStepJsonArray.length(); i++) {
            BakingStep bakingStep = new BakingStep();
            JSONObject backingStepJsonObject = bakingStepJsonArray.getJSONObject(i);
            if (backingStepJsonObject.has(STEP_ID)) {
                bakingStep.setId(backingStepJsonObject.getInt(STEP_ID));
            }
            if (backingStepJsonObject.has(STEP_SHORT_DESCRIPTION)) {
                bakingStep.setShortDescription(backingStepJsonObject.getString(STEP_SHORT_DESCRIPTION));
            }
            if (backingStepJsonObject.has(STEP_DESCRIPTION)) {
                bakingStep.setDescription(backingStepJsonObject.getString(STEP_DESCRIPTION));
            }
            if (backingStepJsonObject.has(STEP_VIDEO_URL)) {
                bakingStep.setVideoURL(backingStepJsonObject.getString(STEP_VIDEO_URL));
            }
            if (backingStepJsonObject.has(STEP_THUMBNAIL_URL)) {
                bakingStep.setThumbnailURL(backingStepJsonObject.getString(STEP_THUMBNAIL_URL));
            }

            bakingSteps.add(bakingStep);
            // TODO: check
           /* String mimeType = getMimeType(this, Uri.parse(thumbnailUrl));
            if (mimeType.startsWith("video/")){
                // do the swap here
            }*/
        }
        return bakingSteps;

    }

    private static List<Ingredient> parseIngredientsJson(final JSONArray ingredientsJsonArray) throws JSONException {
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsJsonArray.length(); i++) {
            Ingredient ingredient = new Ingredient();
            JSONObject ingredientJsonObject = ingredientsJsonArray.getJSONObject(i);
            if (ingredientJsonObject.has(INGREDIENT_QUANTITY)) {
                ingredient.setQuantity(ingredientJsonObject.getInt(INGREDIENT_QUANTITY));
            }
            if (ingredientJsonObject.has(INGREDIENT_MEASURE)) {
                ingredient.setMeasure(ingredientJsonObject.getString(INGREDIENT_MEASURE));
            }
            if (ingredientJsonObject.has(INGREDIENT_NAME)) {
                ingredient.setIngredientName(ingredientJsonObject.getString(INGREDIENT_NAME));
            }
            ingredients.add(ingredient);
        }
        return ingredients;
    }
}
