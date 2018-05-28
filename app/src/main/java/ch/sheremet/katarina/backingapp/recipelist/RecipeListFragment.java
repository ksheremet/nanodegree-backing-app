package ch.sheremet.katarina.backingapp.recipelist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    private static final String TAG = RecipeListFragment.class.getSimpleName();

    private static final int RECIPE_LOADER_ID = 111;

    @BindView(R.id.recipe_recycler_view)
    RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private IOnRecipeSelectedListener mIOnRecipeSelectedListener;
    private SimpleIdlingResource mIdleResource;

    private LoaderManager.LoaderCallbacks<List<Recipe>> mRecipeLoader = new LoaderManager.LoaderCallbacks<List<Recipe>>() {
        @NonNull
        @Override
        public Loader<List<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
            return new RecipeAsyncTaskLoader(getContext());
        }

        @Override
        public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> data) {
            if (data == null) {
                Toast.makeText(getContext(),
                        R.string.error_user_message, Toast.LENGTH_SHORT).show();
            } else {
                mRecipeAdapter.setRecipeList(data);
            }
            if (mIdleResource != null) {
                mIdleResource.setIdleState(true);
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {

        }
    };

    // Constructor for initiating fragment
    public RecipeListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mIOnRecipeSelectedListener = (IOnRecipeSelectedListener) context;
            mIdleResource = (SimpleIdlingResource) ((MainActivity) getActivity()).getIdling();
            if (mIdleResource != null) {
                mIdleResource.setIdleState(false);
            }
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

        if (getActivity().getSupportLoaderManager().getLoader(RECIPE_LOADER_ID) == null) {
            getActivity().getSupportLoaderManager()
                    .initLoader(RECIPE_LOADER_ID, null, mRecipeLoader);
        } else {
            getActivity().getSupportLoaderManager()
                    .restartLoader(RECIPE_LOADER_ID, null, mRecipeLoader);
        }

        return rootView;
    }

    static class RecipeAsyncTaskLoader extends AsyncTaskLoader<List<Recipe>> {

        private List<Recipe> mRecipeList;

        RecipeAsyncTaskLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            if (mRecipeList == null) {
                forceLoad();
            } else {
                deliverResult(mRecipeList);
            }
        }

        @Override
        public List<Recipe> loadInBackground() {
            try {
                return RecipeParseJsonUtil.parseRecipes(NetworkUtil.getResponseFromHttpUrl());
            } catch (IOException e) {
                Log.e(TAG, "Error fetching recipes", e);
                return null;
            }
        }

        @Override
        public void deliverResult(List<Recipe> data) {
            mRecipeList = data;
            super.deliverResult(data);
        }
    }
}
