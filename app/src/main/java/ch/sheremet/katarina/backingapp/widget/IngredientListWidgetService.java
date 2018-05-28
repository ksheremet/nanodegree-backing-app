package ch.sheremet.katarina.backingapp.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class IngredientListWidgetService extends RemoteViewsService {
    private static final String TAG = IngredientListWidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "Return view factory");

        return new IngredientWidgetDataProvider(this);
    }
}
