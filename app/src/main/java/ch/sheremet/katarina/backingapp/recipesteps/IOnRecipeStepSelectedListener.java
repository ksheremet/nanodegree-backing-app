package ch.sheremet.katarina.backingapp.recipesteps;

public interface IOnRecipeStepSelectedListener {
    void onRecipeStepClick(int i);
    void nextStep();
    void previousStep();
}
