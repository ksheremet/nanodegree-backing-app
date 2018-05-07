package ch.sheremet.katarina.backingapp.recipelist;

import android.content.Context;
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

    @BindView(R.id.recipe_recycler_view)
    RecyclerView mRecipeRecyclerView;
    private List<Recipe> mRecipeList;
    private RecipeAdapter mRecipeAdapter;
    private IOnRecipeSelectedListener mIOnRecipeSelectedListener;

    // Constructor for initiating fragment
    public RecipeListFragment() {
    }

    public void setRecipeList(final List<Recipe> recipeList) {
        this.mRecipeList = recipeList;
        if (mRecipeAdapter != null) {
            mRecipeAdapter.setRecipeList(mRecipeList);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mIOnRecipeSelectedListener = (IOnRecipeSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement IOnRecipeSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, rootView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        mRecipeRecyclerView.setLayoutManager(layoutManager);
        mRecipeRecyclerView.setHasFixedSize(true);
        mRecipeAdapter = new RecipeAdapter(mIOnRecipeSelectedListener);
        mRecipeRecyclerView.setAdapter(mRecipeAdapter);
        mRecipeAdapter.setRecipeList(mRecipeList);
        return rootView;
    }
}
