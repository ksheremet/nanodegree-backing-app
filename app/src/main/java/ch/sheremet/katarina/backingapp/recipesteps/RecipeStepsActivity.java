package ch.sheremet.katarina.backingapp.recipesteps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.sheremet.katarina.backingapp.R;
import ch.sheremet.katarina.backingapp.ingredients.IngredientsFragment;
import ch.sheremet.katarina.backingapp.model.Ingredient;
import ch.sheremet.katarina.backingapp.model.Recipe;
import ch.sheremet.katarina.backingapp.stepinstruction.RecipeStepInstructionFragment;

public class RecipeStepsActivity extends AppCompatActivity implements IOnRecipeStepSelectedListener {

    public static final String RECIPE_PARAM = "recipe";
    public static final String CURRENT_RECIPE_STEP = "current-recipe-step";
    private static final String TAG = RecipeStepsActivity.class.getSimpleName();

    private Recipe mRecipe;
    private int mCurrentRecipeStep;
    private boolean mTwoPane = false;

    public static void startActivity(Context context, Recipe recipe) {
        Intent recipeStepsIntent = new Intent(context, RecipeStepsActivity.class);
        recipeStepsIntent.putExtra(RECIPE_PARAM, recipe);
        context.startActivity(recipeStepsIntent);
    }

    private void saveLastSeenRecipeIngredients() {
        // Save seen recipe to shared preferences
        Set<String> ingredientsSet = new HashSet<>();
        for (int i = 0; i < mRecipe.getIngredients().size(); i++) {
            Ingredient recipeIngredient = mRecipe.getIngredients().get(i);
            StringBuilder ingredientsBuilder = new StringBuilder(recipeIngredient.getIngredientName())
                    .append(" ").append(recipeIngredient.getQuantity()).append(recipeIngredient.getMeasure());
            ingredientsSet.add(ingredientsBuilder.toString());
        }
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.share_pref_recipe_name), mRecipe.getName());
        editor.putStringSet(getString(R.string.shared_pref_ingredient), ingredientsSet);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        if ((getIntent() == null) || (!getIntent().hasExtra(RECIPE_PARAM))) {
            finish();
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mRecipe = getIntent().getParcelableExtra(RECIPE_PARAM);
        setTitle(mRecipe.getName());
        saveLastSeenRecipeIngredients();

        if (findViewById(R.id.recipe_instruction_fragment) != null) {
            mTwoPane = true;
        }
        if (mTwoPane && savedInstanceState == null) {
            mTwoPane = true;
            RecipeStepInstructionFragment instructionFragment = new RecipeStepInstructionFragment();
            instructionFragment.setBackingStep(mRecipe.getBakingSteps().get(0));
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.recipe_instruction_fragment, instructionFragment)
                    .commit();
        }

        if (savedInstanceState == null) {
            List<String> recipeStepsDesc = new ArrayList<>();
            recipeStepsDesc.add("Recipe Ingredients");
            for (int i = 0; i < mRecipe.getBakingSteps().size(); i++) {
                recipeStepsDesc.add(mRecipe.getBakingSteps().get(i).getShortDescription());
            }
            RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
            recipeStepsFragment.setRecipeStepsDesc(recipeStepsDesc);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.recipe_steps_fragment, recipeStepsFragment)
                    .commit();
        } else {
            mCurrentRecipeStep = savedInstanceState.getInt(CURRENT_RECIPE_STEP);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_RECIPE_STEP, mCurrentRecipeStep);
    }

    @Override
    public void onRecipeStepClick(int i) {
        mCurrentRecipeStep = i;
        if (i == 0) {
            showIngredients();
            return;
        }
        Log.d(TAG, mRecipe.getBakingSteps().get(i - 1).getShortDescription());
        showRecipeStep();
    }

    private void showIngredients() {
        IngredientsFragment instructionFragment = new IngredientsFragment();
        instructionFragment.setIngredients(mRecipe.getIngredients());
        if (mTwoPane) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recipe_instruction_fragment, instructionFragment)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recipe_steps_fragment, instructionFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void nextStep() {
        Log.d(TAG, "Current Recipe Step: " + mCurrentRecipeStep);
        if (mCurrentRecipeStep < mRecipe.getBakingSteps().size()) {
            mCurrentRecipeStep++;
            Log.d(TAG, "Next Recipe Step: " + mCurrentRecipeStep);
            showRecipeStep();
        }
    }

    @Override
    public void previousStep() {
        if (mCurrentRecipeStep > 1) {
            mCurrentRecipeStep--;
            showRecipeStep();
        }
    }

    private void showRecipeStep() {
        RecipeStepInstructionFragment instructionFragment = new RecipeStepInstructionFragment();
        instructionFragment.setBackingStep(mRecipe.getBakingSteps().get(mCurrentRecipeStep - 1));
        if (mTwoPane) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recipe_instruction_fragment, instructionFragment)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recipe_steps_fragment, instructionFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        navigateUp();
    }

    private void navigateUp() {
        Log.d(TAG, String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
