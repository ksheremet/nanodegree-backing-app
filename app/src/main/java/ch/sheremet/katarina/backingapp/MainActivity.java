package ch.sheremet.katarina.backingapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.sheremet.katarina.backingapp.model.Recipe;
import ch.sheremet.katarina.backingapp.recipelist.RecipeListFragment;
import ch.sheremet.katarina.backingapp.utilities.NetworkUtil;
import ch.sheremet.katarina.backingapp.utilities.RecipeParseJsonUtil;

public class MainActivity extends AppCompatActivity {

    private RecipeListFragment mRecipesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Instantiate fragment if it wasn't created before
        if (savedInstanceState == null) {
            mRecipesFragment = new RecipeListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_list_fragment, mRecipesFragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new RecipeAsyncTask().execute();
    }

    // TODO: use async task loader
    // TODO: consider to move to fragment
    class RecipeAsyncTask extends AsyncTask<Void, Void, List<Recipe>> {

        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            try {
                return RecipeParseJsonUtil.parseRecipes(NetworkUtil.getResponseFromHttpUrl());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);
            System.out.println(recipes.toString());
            mRecipesFragment.setRecipeList(recipes);
        }
    }
}
