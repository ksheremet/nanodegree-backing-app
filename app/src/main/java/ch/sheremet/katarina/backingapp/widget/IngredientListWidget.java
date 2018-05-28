package ch.sheremet.katarina.backingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import ch.sheremet.katarina.backingapp.MainActivity;
import ch.sheremet.katarina.backingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientListWidget extends AppWidgetProvider {

    private static final String TAG = IngredientListWidget.class.getSimpleName();

    private static String getRecipeName(Context context) {
        SharedPreferences sharedPref = context
                .getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        // Show app name in default
        return sharedPref.getString(context.getString(R.string.share_pref_recipe_name), context.getString(R.string.app_name));
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.d(TAG, "Update Widget");
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_list_widget);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        String recipeName = getRecipeName(context);
        if (recipeName.equals(context.getString(R.string.app_name))) {
            views.setViewVisibility(R.id.widget_list, View.INVISIBLE);
            views.setViewVisibility(R.id.default_widget_image, View.VISIBLE);
            views.setOnClickPendingIntent(R.id.default_widget_image, pendingIntent);
        } else {
            views.setViewVisibility(R.id.widget_list, View.VISIBLE);
            views.setPendingIntentTemplate(R.id.widget_list, pendingIntent);
            views.setViewVisibility(R.id.default_widget_image, View.INVISIBLE);
            views.setRemoteAdapter(R.id.widget_list, new Intent(context, IngredientListWidgetService.class));
            views.setTextViewText(R.id.recipe_name, recipeName);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

