package com.dreamfacilities.audiobooks;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;


public class WidgetProvider extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] widgetIds) {
        for (int widgetId : widgetIds) {
            updateWidget(context, widgetId);
            Log.i("AUDIOBOOK WIDGET", "UPDATING");
        }
    }

    public static void updateWidget(Context context, int widgetId) {

        Book book = getLastAudioBook(context, widgetId);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        if (book != null) {
            Log.i("AUDIOBOOK WIDGET", book.autor);
            remoteViews.setTextViewText(R.id.widget_title, book.title);
            remoteViews.setTextViewText(R.id.widget_autor, book.autor);
        }
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_play, pendingIntent);

        AppWidgetManager.getInstance(context).updateAppWidget(widgetId, remoteViews);
    }

    private static Book getLastAudioBook(Context context, int widgetId) {
        SharedPreferences pref = context.getSharedPreferences("com.dreamfacilities.audiobooks_internal", context.MODE_PRIVATE);
        Book book = null;
        int id = pref.getInt("last", -1);
        if (id >= 0) {
            book = BooksSingleton.getInstance(context).getAdapter().getItemById(id);
        }
        return book;
    }

}
