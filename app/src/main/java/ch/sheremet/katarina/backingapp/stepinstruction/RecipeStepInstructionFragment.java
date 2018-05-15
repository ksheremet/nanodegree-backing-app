package ch.sheremet.katarina.backingapp.stepinstruction;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.sheremet.katarina.backingapp.R;
import ch.sheremet.katarina.backingapp.model.BakingStep;
import ch.sheremet.katarina.backingapp.recipesteps.IOnRecipeStepSelectedListener;

public class RecipeStepInstructionFragment extends Fragment {

    private static final String BAKING_STEP = "backing-step";
    private static final String PLAYBACK_POSITION = "playback-position";
    private static final String PLAY_WHEN_READY = "play-when-ready";
    private static final String CURRENT_WINDOW = "current-window";
    private static final String TAG = RecipeStepInstructionFragment.class.getSimpleName();

    private BakingStep mBackingStep;
    private IOnRecipeStepSelectedListener mOnRecipeStepSelectedListener;
    @Nullable
    @BindView(R.id.recipe_step_instruction_textview)
    TextView mRecipeInstructionTextView;
    @Nullable
    @BindView(R.id.previous_step_button)
    Button mPreviousStepButton;
    @Nullable
    @BindView(R.id.next_step_button)
    Button mNextStepButton;
    @BindView(R.id.exoplayer_view)
    PlayerView mPlayerView;
    @BindView(R.id.default_cake_imageview)
    ImageView mDefaultCakeImageView;
    private SimpleExoPlayer mExoPlayer;
    private long mPlaybackPosition = 0;
    private boolean mPlayWhenReady = true;
    private int mCurrentWindow = 0;


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

    public void setBackingStep(BakingStep backingStep) {
        this.mBackingStep = backingStep;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_instruction, container, false);
        ButterKnife.bind(this, rootView);

        if (savedInstanceState != null) {
            mBackingStep = savedInstanceState.getParcelable(BAKING_STEP);
            mPlaybackPosition = savedInstanceState.getLong(PLAYBACK_POSITION);
            mPlayWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
            mCurrentWindow = savedInstanceState.getInt(CURRENT_WINDOW);
        }

        if (mRecipeInstructionTextView != null) {
            mRecipeInstructionTextView.setText(mBackingStep.getDescription());
        }
        if (mNextStepButton != null) {
            mNextStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnRecipeStepSelectedListener.nextStep();
                }
            });
        }
        if (mPreviousStepButton != null) {
            mPreviousStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnRecipeStepSelectedListener.previousStep();
                }
            });
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mExoPlayer == null) {
            if (mBackingStep.getVideoURL().isEmpty()) {
                mPlayerView.setVisibility(View.INVISIBLE);
                if (mBackingStep.getThumbnailURL().isEmpty()) {
                    mDefaultCakeImageView.setImageDrawable(getResources().getDrawable(R.drawable.cupcake));
                } else {
                    Picasso
                            .get()
                            .load(mBackingStep.getThumbnailURL())
                            .placeholder(R.drawable.cupcake)
                            .error(R.drawable.cupcake)
                            .into(mDefaultCakeImageView);
                }
            } else {
                initializePlayer(Uri.parse(mBackingStep.getVideoURL()));
            }
        }
    }

    // https://google.github.io/ExoPlayer/guide.html
    // New codelab about ExoPlayer: https://codelabs.developers.google.com/codelabs/exoplayer-intro/#0
    private void initializePlayer(Uri uri) {
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        mPlayerView.setPlayer(mExoPlayer);

        mExoPlayer.setPlayWhenReady(mPlayWhenReady);
        mExoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
        MediaSource mediaSource = buildMediaSource(uri);
        mExoPlayer.prepare(mediaSource, false, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(getString(R.string.app_name))).
                createMediaSource(uri);
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mExoPlayer != null) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BAKING_STEP, mBackingStep);
        if (mExoPlayer != null) {
            outState.putLong(PLAYBACK_POSITION, mExoPlayer.getCurrentPosition());
            outState.putBoolean(PLAY_WHEN_READY, mExoPlayer.getPlayWhenReady());
            outState.putInt(CURRENT_WINDOW, mExoPlayer.getCurrentWindowIndex());
        }
    }
}
