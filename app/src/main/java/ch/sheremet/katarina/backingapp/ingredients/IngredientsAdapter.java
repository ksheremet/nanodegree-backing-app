package ch.sheremet.katarina.backingapp.ingredients;

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
import ch.sheremet.katarina.backingapp.model.Ingredient;

class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private List<Ingredient> mIngredientsList;

    public void setIngredientsList(List<Ingredient> ingredientsList) {
        this.mIngredientsList = ingredientsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.mName.setText(mIngredientsList.get(position).getIngredientName());
        holder.mQuantity.setText(String.valueOf(mIngredientsList.get(position).getQuantity()));
        holder.mMeasure.setText(mIngredientsList.get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        if (mIngredientsList == null) {
            return 0;
        }
        return mIngredientsList.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_name_textview)
        TextView mName;
        @BindView(R.id.ingredient_quantity_textview)
        TextView mQuantity;
        @BindView(R.id.ingredient_measure_textview)
        TextView mMeasure;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
