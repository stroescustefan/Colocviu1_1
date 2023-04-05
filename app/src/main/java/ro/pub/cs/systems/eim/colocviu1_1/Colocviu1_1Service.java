package ro.pub.cs.systems.eim.colocviu1_1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Colocviu1_1Service extends Service {
    ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int clickCounts = intent.getExtras().getInt(Constants.CLICK_COUNT);
        String cardinalPoints = intent.getExtras().getString(Constants.CARDINAL_EDIT_TEXT);
        processingThread = new ProcessingThread(this, cardinalPoints, clickCounts);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}