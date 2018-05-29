package ch.sheremet.katarina.backingapp.recipelist;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.sheremet.katarina.backingapp.R;
import ch.sheremet.katarina.backingapp.model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipeList;
    private IOnRecipeSelectedListener mIOnRecipeSelectedListener;

    RecipeAdapter(IOnRecipeSelectedListener IOnRecipeSelectedListener) {
        this.mIOnRecipeSelectedListener = IOnRecipeSelectedListener;
    }

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
    public void onBindViewHolder(@NonNull final RecipeViewHolder holder, final int position) {
        if (!mRecipeList.get(position).getImage().isEmpty()) {
            Picasso.get()
                    .load(mRecipeList.get(position).getImage())
                    .error(R.drawable.cupcake)
                    .placeholder(R.drawable.cupcake)
                    .into(holder.mRecipeImage);
        }
        holder.mRecipeName.setText(mRecipeList.get(position).getName());
        holder.mRecipeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIOnRecipeSelectedListener.onRecipeClick(mRecipeList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mRecipeList == null) {
            return 0;
        }
        return mRecipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_image_view)
        ImageView mRecipeImage;
        @BindView(R.id.recipe_text_view)
        TextView mRecipeName;
        @BindView(R.id.recipe_card_view)
        CardView mRecipeCardView;

        RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
