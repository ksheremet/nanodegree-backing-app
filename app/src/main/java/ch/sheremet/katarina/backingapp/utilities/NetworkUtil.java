package ch.sheremet.katarina.backingapp.utilities;

import android.net.Uri;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtil {
    private static final String BASE_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    private static Uri.Builder buildBaseUrl() {
        return Uri.parse(BASE_URL)
                .buildUpon();
    }


    public static String getResponseFromHttpUrl() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                // TODO: check
                .url(new URL(Uri.parse(BASE_URL).buildUpon().toString()))
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
