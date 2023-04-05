package ro.pub.cs.systems.eim.colocviu1_1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

public class ProcessingThread extends Thread {

    private static final int DEFAULT_SLEEP_TIME_MILLIS = 5000;
    private static boolean isRunning = true;
    private Context context;
    private int countClicks;
    private String cardinalPoints;
    public ProcessingThread(Context context,String cardinalPoints, int countClicks) {
        this.context = context;
        this.cardinalPoints = cardinalPoints;
        this.countClicks = countClicks;
    }

    @Override
    public void run() {
        Log.d(Constants.TAG, "Thread has started!");
        sleep(DEFAULT_SLEEP_TIME_MILLIS);
        sendMessage();
        Log.d(Constants.TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION);
        intent.putExtra(Constants.BROADCAST_INTENT_KEY, cardinalPoints + "; Click counts: " +
                countClicks + " " + new Date(System.currentTimeMillis()));
        context.sendBroadcast(intent);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
