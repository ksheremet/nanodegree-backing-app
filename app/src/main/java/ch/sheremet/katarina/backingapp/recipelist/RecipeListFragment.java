package ch.sheremet.katarina.backingapp.recipelist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.sheremet.katarina.backingapp.R;
import ch.sheremet.katarina.backingapp.model.Recipe;

public class RecipeListFragment extends Fragment {

    private List<Recipe> mRecipeList;
    @BindView(R.id.recipe_recycler_view)
    private RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mRecipeAdapter;

    // Constructor for initiating fragment
    public RecipeListFragment() {
    }

    public void setRecipeList(final List<Recipe> recipeList) {
        this.mRecipeList = recipeList;
        if (mRecipeAdapter != null) {
            mRecipeAdapter.setRecipeList(mRecipeList);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        //ButterKnife.bind(rootView);
        mRecipeRecyclerView = rootView.findViewById(R.id.recipe_recycler_view);
        // TODO
        GridLayoutManager layoutManager = new GridLayoutManager(inflater.getContext(), 1);
        mRecipeRecyclerView.setLayoutManager(layoutManager);
        mRecipeRecyclerView.setHasFixedSize(true);
        mRecipeAdapter = new RecipeAdapter();
        mRecipeRecyclerView.setAdapter(mRecipeAdapter);
        mRecipeAdapter.setRecipeList(mRecipeList);
        return rootView;
    }
}
