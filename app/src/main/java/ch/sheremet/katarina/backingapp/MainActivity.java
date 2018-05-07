package ch.sheremet.katarina.backingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import ch.sheremet.katarina.backingapp.model.Recipe;
import ch.sheremet.katarina.backingapp.recipelist.IOnRecipeSelectedListener;

public class MainActivity extends AppCompatActivity implements IOnRecipeSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        System.out.println("Recipe:" + recipe);
    }
}
