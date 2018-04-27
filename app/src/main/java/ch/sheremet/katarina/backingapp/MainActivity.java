package ch.sheremet.katarina.backingapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.sheremet.katarina.backingapp.utilities.NetworkUtil;
import ch.sheremet.katarina.backingapp.utilities.RecipeParseJsonUtil;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.hello_world)
    TextView mHelloWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new RecipeAsyncTask().execute();
    }

    class RecipeAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                return RecipeParseJsonUtil.parseRecipes(NetworkUtil.getResponseFromHttpUrl()).toString();
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println(s);
            System.out.println(mHelloWorld);
        }
    }
}
