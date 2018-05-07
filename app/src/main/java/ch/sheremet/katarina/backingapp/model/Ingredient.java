package ch.sheremet.katarina.backingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private int mQuantity;
    private String mMeasure;
    private String mIngredientName;

    public Ingredient() {
    }

    protected Ingredient(Parcel in) {
        mQuantity = in.readInt();
        mMeasure = in.readString();
        mIngredientName = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mQuantity);
        dest.writeString(mMeasure);
        dest.writeString(mIngredientName);
    }
}
