package ch.sheremet.katarina.backingapp.recipelist;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.sheremet.katarina.backingapp.MainActivity;
import ch.sheremet.katarina.backingapp.R;
import ch.sheremet.katarina.backingapp.idlingresource.SimpleIdlingResource;
import ch.sheremet.katarina.backingapp.model.Recipe;
import ch.sheremet.katarina.backingapp.utilities.NetworkUtil;
import ch.sheremet.katarina.backingapp.utilities.RecipeParseJsonUtil;

public class RecipeListFragment extends Fragment {

    @BindView(R.id.recipe_recycler_view)
    RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private IOnRecipeSelectedListener mIOnRecipeSelectedListener;
    private SimpleIdlingResource mIdleResource;

    // Constructor for initiating fragment
    public RecipeListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mIOnRecipeSelectedListener = (IOnRecipeSelectedListener) context;
            mIdleResource = (SimpleIdlingResource) ((MainActivity) getActivity()).getIdlingResource();
            mIdleResource.setIdleState(false);
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
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),
                getResources().getInteger(R.integer.recipe_grid_span_count));
        mRecipeRecyclerView.setLayoutManager(layoutManager);
        mRecipeRecyclerView.setHasFixedSize(true);
        mRecipeAdapter = new RecipeAdapter(mIOnRecipeSelectedListener);
        mRecipeRecyclerView.setAdapter(mRecipeAdapter);
        new RecipeAsyncTask().execute();
        return rootView;
    }

    // TODO: use async task loader
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
            mRecipeAdapter.setRecipeList(recipes);
            if (mIdleResource != null) {
                mIdleResource.setIdleState(true);
            }
        }
    }
}
