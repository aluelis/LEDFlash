package aluelis.ledflash;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.widget.RemoteViews;

/**
 * Created by szvetlintanyi on 24/12/15.
 */

public class FlashReceiver extends BroadcastReceiver {

    static boolean isFlashOn = false;
    RemoteViews views;
    static Camera camera;

    @Override
    public void onReceive(Context context, Intent intent) {

        views = new RemoteViews(context.getPackageName(), R.layout.flash_layout);

        if (!isFlashOn) {
            views.setTextColor(R.id.flash_text, context.getResources().getColor(R.color.green));
            views.setImageViewResource(R.id.flash_image, R.drawable.lightbulb_on);
            camera = Camera.open();
            Camera.Parameters p = camera.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(p);
            camera.startPreview();
            isFlashOn = true;

        } else {
            views.setTextColor(R.id.flash_text, context.getResources().getColor(R.color.grey));
            views.setImageViewResource(R.id.flash_image, R.drawable.lightbulb_off);
            camera.stopPreview();
            camera.release();
            camera = null;
            isFlashOn = false;
        }


        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(new ComponentName(context, Flash.class), views);
    }
}