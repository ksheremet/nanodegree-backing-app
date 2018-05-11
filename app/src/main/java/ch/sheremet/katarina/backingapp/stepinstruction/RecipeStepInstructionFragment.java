package ch.sheremet.katarina.backingapp.stepinstruction;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.sheremet.katarina.backingapp.R;
import ch.sheremet.katarina.backingapp.model.BakingStep;
import ch.sheremet.katarina.backingapp.recipesteps.IOnRecipeStepSelectedListener;

public class RecipeStepInstructionFragment extends Fragment {

    private static final String BAKING_STEP = "backing-step";

    private BakingStep mBackingStep;
    private IOnRecipeStepSelectedListener mOnRecipeStepSelectedListener;
    @BindView(R.id.recipe_step_instruction_textview)
    TextView mRecipeInstructionTextView;
    @Nullable
    @BindView(R.id.previous_step_button)
    Button mPreviousStepButton;
    @Nullable
    @BindView(R.id.next_step_button)
    Button mNextStepButton;

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
        }

        mRecipeInstructionTextView.setText(mBackingStep.getDescription());
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BAKING_STEP, mBackingStep);

    }
}
