package ch.sheremet.katarina.backingapp.recipelist;

import ch.sheremet.katarina.backingapp.model.Recipe;

public interface IOnRecipeSelectedListener {
    void onRecipeClick(Recipe recipe);
}
