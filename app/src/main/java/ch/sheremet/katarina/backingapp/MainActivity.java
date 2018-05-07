package ch.sheremet.katarina.backingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ch.sheremet.katarina.backingapp.model.Recipe;
import ch.sheremet.katarina.backingapp.recipelist.IOnRecipeSelectedListener;
import ch.sheremet.katarina.backingapp.recipesteps.RecipeStepsActivity;

public class MainActivity extends AppCompatActivity implements IOnRecipeSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Log.d(TAG, recipe.getBakingSteps().toString());
        RecipeStepsActivity.startActivity(MainActivity.this, recipe);
    }
}
