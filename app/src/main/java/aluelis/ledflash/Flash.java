package aluelis.ledflash;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by szvetlintanyi on 17/12/15.
 */
public class Flash extends AppWidgetProvider {

    RemoteViews views;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            return;
        }

        for (int currentWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, FlashReceiver.class);
            intent.setAction("aluelis.FLASH");
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            views = new RemoteViews(context.getPackageName(), R.layout.flash_layout);

            views.setOnClickPendingIntent(R.id.widget, pendingIntent);
            appWidgetManager.updateAppWidget(currentWidgetId, views);
        }
    }
}
