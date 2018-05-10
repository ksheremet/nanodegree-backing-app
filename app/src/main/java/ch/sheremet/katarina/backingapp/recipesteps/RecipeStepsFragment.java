package ch.sheremet.katarina.backingapp.recipesteps;

import android.content.Context;
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

public class RecipeStepsFragment extends Fragment {

    private static final String RECIPE_DESC_LIST = "desc_list";

    @BindView(R.id.recipe_desc_recycler_view)
    RecyclerView mRecipeDescRecyclerView;
    private List<String> mRecipeStepsDesc;
    private IOnRecipeStepSelectedListener mOnRecipeStepSelectedListener;

    public RecipeStepsFragment() {
    }

    public void setRecipeStepsDesc(List<String> recipeStepsDesc) {
        this.mRecipeStepsDesc = recipeStepsDesc;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnRecipeStepSelectedListener = (IOnRecipeStepSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement IOnRecipeStepSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        ButterKnife.bind(this, rootView);

        if (savedInstanceState != null) {
            setRecipeStepsDesc(savedInstanceState.getStringArrayList(RECIPE_DESC_LIST));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecipeDescRecyclerView.setLayoutManager(linearLayoutManager);
        mRecipeDescRecyclerView.setHasFixedSize(true);
        RecipeStepsAdapter recipeStepsAdapter = new RecipeStepsAdapter(mOnRecipeStepSelectedListener);
        mRecipeDescRecyclerView.setAdapter(recipeStepsAdapter);
        recipeStepsAdapter.setRecipeSteps(mRecipeStepsDesc);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(RECIPE_DESC_LIST, (ArrayList<String>) mRecipeStepsDesc);
    }
}
