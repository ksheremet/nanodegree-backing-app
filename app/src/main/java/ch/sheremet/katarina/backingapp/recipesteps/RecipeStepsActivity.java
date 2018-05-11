package ch.sheremet.katarina.backingapp.recipesteps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
        if (savedInstanceState == null) {
            List<String> recipeStepsDesc = new ArrayList<>();
            recipeStepsDesc.add("Recipe Ingredients");
            for (int i = 0; i < mRecipe.getBakingSteps().size(); i++) {
                recipeStepsDesc.add(mRecipe.getBakingSteps().get(i).getShortDescription());
            }
            RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
            recipeStepsFragment.setRecipeStepsDesc(recipeStepsDesc);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.recipe_steps_fragment, recipeStepsFragment).commit();
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
        instructionFragment.setBackingStep(mRecipe.getBakingSteps().get(mCurrentRecipeStep-1));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.recipe_steps_fragment, instructionFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount()>0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
