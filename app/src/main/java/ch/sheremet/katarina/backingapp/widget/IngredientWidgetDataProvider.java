package ch.sheremet.katarina.backingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ch.sheremet.katarina.backingapp.R;

// Gets data
public class IngredientWidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private List<String> ingredients = new ArrayList<>();
    private Context mContext;

    public IngredientWidgetDataProvider(Context context) {
        this.mContext = context;
    }

    private void initData() {
        ingredients.clear();
        SharedPreferences sharedPref = mContext
                .getSharedPreferences(mContext.getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE);
        ingredients = new ArrayList<>(sharedPref.getStringSet(mContext.getResources()
                .getString(R.string.shared_pref_ingredient), new HashSet<String>()));
    }

    /**
     * Called when your factory is first constructed. The same factory may be shared across
     * multiple RemoteViewAdapters depending on the intent passed.
     */
    @Override
    public void onCreate() {
        initData();
    }

    /**
     * Called when notifyDataSetChanged() is triggered on the remote adapter. This allows a
     * RemoteViewsFactory to respond to data changes by updating any internal references.
     * <p>
     * Note: expensive tasks can be safely performed synchronously within this method. In the
     * interim, the old data will be displayed within the widget.
     *
     * @see AppWidgetManager#notifyAppWidgetViewDataChanged(int[], int)
     */
    @Override
    public void onDataSetChanged() {
        initData();
    }

    /**
     * Called when the last RemoteViewsAdapter that is associated with this factory is
     * unbound.
     */
    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredients == null) return 0;
        return ingredients.size();
    }

    /**
     * Note: expensive tasks can be safely performed synchronously within this method, and a
     * loading view will be displayed in the interim. See {@link #getLoadingView()}.
     *
     * @param position The position of the item within the Factory's data set of the item whose
     *                 view we want.
     * @return A RemoteViews object corresponding to the data at the specified position.
     */
    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteView = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);
        remoteView.setTextViewText(android.R.id.text1, ingredients.get(position));
        remoteView.setOnClickFillInIntent(android.R.id.text1, new Intent());
        return remoteView;
    }

    /**
     * This allows for the use of a custom loading view which appears between the time that
     * {@link #getViewAt(int)} is called and returns. If null is returned, a default loading
     * view will be used.
     *
     * @return The RemoteViews representing the desired loading view.
     */
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    /**
     * @return The number of types of Views that will be returned by this factory.
     */
    @Override
    public int getViewTypeCount() {
        return 1;
    }

    /**
     * @param position The position of the item within the data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @return True if the same id always refers to the same object.
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }
}
