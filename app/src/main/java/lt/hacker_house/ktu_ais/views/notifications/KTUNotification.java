package lt.hacker_house.ktu_ais.views.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.ArrayList;
import java.util.List;

import lt.hacker_house.ktu_ais.R;
import lt.hacker_house.ktu_ais.models.GradeUpdateModel;
import lt.hacker_house.ktu_ais.models.WeekModel;
import lt.hacker_house.ktu_ais.views.activities.LoginActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Vartotojas on 2017-05-06.
 */

public class KTUNotification {

    public void sendGradeNotification(GradeUpdateModel model, Context context) {
        String title = context.getString(R.string.received) + model.getMark();
        String text = model.getName() + ", " + model.getType();
        sendNotification(model.hashCode(), title, text, context);
    }

    @Deprecated
    public void sendUpcomingTestNotification(WeekModel model, Context context) {
        String title = "Kitos savaitės atsiskaitymai";
        String text = "";
        StringBuilder mStringBuilder = new StringBuilder();

        for (int i = 0; i < model.getGrades().size(); i++) {
            text = mStringBuilder.append(model.getGrades().get(i).getName()).append(", ").append(model.getGrades().get(i).
                    getTypeId()).append(System.getProperty("line.separator")).toString();
        }

        sendNotification(model.hashCode(), title, text, context);
    }

    private void sendNotification(int notificationId, String title, String text, Context context) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_priority_high_black_48dp)
                        .setContentTitle(title)
                        .setContentText(text);
        Intent resultIntent = new Intent(context, LoginActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(LoginActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        manager.notify(notificationId, builder.build());
    }
}