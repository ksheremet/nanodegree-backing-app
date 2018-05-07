package ch.sheremet.katarina.backingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BakingStep implements Parcelable {
    private int mId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoURL;
    private String mThumbnailURL;

    public BakingStep() {
    }

    protected BakingStep(Parcel in) {
        mId = in.readInt();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoURL = in.readString();
        mThumbnailURL = in.readString();
    }

    public static final Creator<BakingStep> CREATOR = new Creator<BakingStep>() {
        @Override
        public BakingStep createFromParcel(Parcel in) {
            return new BakingStep(in);
        }

        @Override
        public BakingStep[] newArray(int size) {
            return new BakingStep[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String mShortDescription) {
        this.mShortDescription = mShortDescription;
    }

    public String getVideoURL() {
        return mVideoURL;
    }

    public void setVideoURL(String mVideoURL) {
        this.mVideoURL = mVideoURL;
    }

    public String getThumbnailURL() {
        return mThumbnailURL;
    }

    public void setThumbnailURL(String mThumbnailURL) {
        this.mThumbnailURL = mThumbnailURL;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    @Override
    public String toString() {
        return new StringBuilder("BakingStep{")
                .append("mId=").append(mId)
                .append(", mShortDescription='").append(mShortDescription).append('\'')
                .append(", mDescription='").append(mDescription).append('\'')
                .append(", mVideoURL='").append(mVideoURL).append('\'')
                .append(", mThumbnailURL='").append(mThumbnailURL).append('\'')
                .append('}').toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mShortDescription);
        dest.writeString(mDescription);
        dest.writeString(mVideoURL);
        dest.writeString(mThumbnailURL);
    }
}
