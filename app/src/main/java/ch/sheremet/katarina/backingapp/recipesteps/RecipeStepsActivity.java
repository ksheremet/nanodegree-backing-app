package ch.sheremet.katarina.backingapp.recipesteps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ch.sheremet.katarina.backingapp.R;
import ch.sheremet.katarina.backingapp.model.Recipe;
import ch.sheremet.katarina.backingapp.stepinstruction.RecipeStepInstructionFragment;

public class RecipeStepsActivity extends AppCompatActivity implements IOnRecipeStepSelectedListener {

    public static final String RECIPE_PARAM = "recipe";
    private static final String TAG = RecipeStepsActivity.class.getSimpleName();

    private Recipe mRecipe;
    private int mCurrentRecipeStep;
    private boolean mTwoPane = false;

    public static void startActivity(Context context, Recipe recipe) {
        Intent recipeStepsIntent = new Intent(context, RecipeStepsActivity.class);
        recipeStepsIntent.putExtra(RECIPE_PARAM, recipe);
        context.startActivity(recipeStepsIntent);
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

        if (findViewById(R.id.recipe_instruction_fragment) != null && savedInstanceState == null) {
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
        }
    }

    @Override
    public void onRecipeStepClick(int i) {
        mCurrentRecipeStep = i;
        if (i == 0) {
            //TODO(ksheremet): Start activity with ingredients
            Log.d(TAG, "Recipe Ingredients");
            return;
        }
        Log.d(TAG, mRecipe.getBakingSteps().get(i - 1).getShortDescription());
        showRecipeStep();
    }

    @Override
    public void nextStep() {
        if (mCurrentRecipeStep <= mRecipe.getBakingSteps().size()) {
            mCurrentRecipeStep++;
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
        Log.d(TAG, String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
