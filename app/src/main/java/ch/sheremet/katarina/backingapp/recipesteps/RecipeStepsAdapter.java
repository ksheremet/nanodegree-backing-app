package ch.sheremet.katarina.backingapp.recipesteps;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.sheremet.katarina.backingapp.R;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepViewHolder> {

    private List<String> mRecipeSteps;

    public void setRecipeSteps(List<String> recipeSteps) {
        this.mRecipeSteps = recipeSteps;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_desc_item, parent, false);
        return new RecipeStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepViewHolder holder, int position) {
        holder.mStepDescription.setText(mRecipeSteps.get(position));
    }

    @Override
    public int getItemCount() {
        if (mRecipeSteps == null) {
            return 0;
        }
        return mRecipeSteps.size();
    }

    class RecipeStepViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_step_desc_text_view)
        TextView mStepDescription;

        RecipeStepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
