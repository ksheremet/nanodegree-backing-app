package ch.sheremet.katarina.backingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    private int mId;
    private String mName;
    private List<Ingredient> mIngredients;
    private List<BakingStep> mBakingSteps;
    private int mServings;
    private String mImage;

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mServings = in.readInt();
        mImage = in.readString();
        mIngredients = new ArrayList<>();
        in.readList(mIngredients, Ingredient.class.getClassLoader());
        mBakingSteps = new ArrayList<>();
        in.readList(mBakingSteps, BakingStep.class.getClassLoader());
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public List<BakingStep> getBakingSteps() {
        return mBakingSteps;
    }

    public void setBakingSteps(List<BakingStep> mBakingSteps) {
        this.mBakingSteps = mBakingSteps;
    }

    public int getServings() {
        return mServings;
    }

    public void setServings(int mServings) {
        this.mServings = mServings;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }

    @Override
    public String toString() {
        return new StringBuilder("Recipe{")
                .append("mId=").append(mId)
                .append(", mName='").append(mName).append('\'')
                .append(", mIngredients=").append(mIngredients)
                .append(", mBakingSteps=").append(mBakingSteps)
                .append(", mServings=").append(mServings)
                .append(", mImage='").append(mImage).append('\'')
                .append('}').toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(mServings);
        dest.writeString(mImage);
        dest.writeList(mIngredients);
        dest.writeList(mBakingSteps);
    }
}
