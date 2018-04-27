package ch.sheremet.katarina.backingapp.model;

public class BakingStep {
    private int mId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoURL;
    private String mThumbnailURL;

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
}
