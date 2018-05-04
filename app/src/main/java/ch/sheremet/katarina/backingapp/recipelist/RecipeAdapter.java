package ch.sheremet.katarina.backingapp.recipelist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ch.sheremet.katarina.backingapp.R;
import ch.sheremet.katarina.backingapp.model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private List<Recipe> mRecipeList;

    public void setRecipeList(List<Recipe> recipeList) {
        this.mRecipeList = recipeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        // TODO
        if (!mRecipeList.get(position).getImage().isEmpty()) {
            Picasso.get()
                    .load(mRecipeList.get(position).getImage())
                    .error(R.drawable.cupcake)
                    .placeholder(R.drawable.cupcake)
                    .into(holder.mRecipeImage);
        }
        holder.mRecipeName.setText(mRecipeList.get(position).getName());
        }

    @Override
    public int getItemCount() {
        if (mRecipeList == null) {
            return 0;
        } else {
            return mRecipeList.size();
        }
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder{

        ImageView mRecipeImage;
        TextView mRecipeName;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            mRecipeImage = itemView.findViewById(R.id.recipe_image_view);
            mRecipeName = itemView.findViewById(R.id.recipe_text_view);
        }
    }
}
