package ch.sheremet.katarina.backingapp.recipesteps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ch.sheremet.katarina.backingapp.R;
import ch.sheremet.katarina.backingapp.model.Recipe;

public class RecipeStepsActivity extends AppCompatActivity {

    public static final String RECIPE_PARAM = "recipe";
    private static final String TAG = RecipeStepsActivity.class.getSimpleName();

    private Recipe mRecipe;

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
        Log.d(TAG, mRecipe.getBakingSteps().toString());
        setTitle(mRecipe.getName());
        if (savedInstanceState == null) {
            RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.recipe_steps_fragment, recipeStepsFragment).commit();
        }
    }
}
