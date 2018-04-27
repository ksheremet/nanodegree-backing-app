package ch.sheremet.katarina.backingapp.model;

public class Ingredient {
    private int mQuantity;
    private String mMeasure;
    private String mIngredientName;

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public void setMeasure(String mMeasure) {
        this.mMeasure = mMeasure;
    }

    public String getIngredientName() {
        return mIngredientName;
    }

    public void setIngredientName(String mIngredientName) {
        this.mIngredientName = mIngredientName;
    }

    @Override
    public String toString() {
        return new StringBuilder("Ingredient{")
                .append("mQuantity=").append(mQuantity)
                .append(", mMeasure='").append(mMeasure).append('\'')
                .append(", mIngredientName='").append(mIngredientName).append('\'')
                .append('}').toString();
    }
}
