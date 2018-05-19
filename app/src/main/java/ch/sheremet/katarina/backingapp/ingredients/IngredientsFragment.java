package ch.sheremet.katarina.backingapp.ingredients;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.sheremet.katarina.backingapp.R;
import ch.sheremet.katarina.backingapp.model.Ingredient;

public class IngredientsFragment extends Fragment {
    private List<Ingredient> mIngredients;
    private static final String INGREDIENTS_LIST = "ingredients-list";
    @BindView(R.id.ingredients_recycler_view)
    RecyclerView mIngredientsRecyclerView;

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, rootView);

        if (savedInstanceState != null) {
            mIngredients = savedInstanceState.getParcelableArrayList(INGREDIENTS_LIST);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mIngredientsRecyclerView.setLayoutManager(linearLayoutManager);
        mIngredientsRecyclerView.setHasFixedSize(true);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter();
        mIngredientsRecyclerView.setAdapter(ingredientsAdapter);
        ingredientsAdapter.setIngredientsList(mIngredients);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(INGREDIENTS_LIST, new ArrayList<>(mIngredients));
    }
}
