package lso.naing.screenoff;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    static final String LOG_TAG = "MainActivity";
    Button enable;
    DevicePolicyManager devicePolicyManager;
    ComponentName componentName;
    private static final int RESULT_ENABLE=11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Administer();
        turnScreenOffAndExit();

        devicePolicyManager=(DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        ActivityManager activityManager=(ActivityManager)getSystemService(ACTIVITY_SERVICE);
        componentName=new ComponentName(this,NcScreenOff.class);


    }


    private void turnScreenOffAndExit() {
// first lock screen
        turnScreenOff(getApplicationContext());

// then provide feedback
        ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(100);

// schedule end of activity
        final Activity activity = this;
        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    /* ignore this */
                }
                activity.finish();
            }
        };
        t.start();
    }

    /**
     * Turns the screen off and locks the device, provided that proper rights
     * are given.
     *
     * @param context
     * - The application context
     */
    static void turnScreenOff(final Context context) {
        DevicePolicyManager policyManager = (DevicePolicyManager) context
                .getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName adminReceiver = new ComponentName(context,
                NcScreenOff.class);
        boolean admin = policyManager.isAdminActive(adminReceiver);
        if (admin) {
            Log.i(LOG_TAG, "Going to sleep now.");
            policyManager.lockNow();
        } else {
            Log.i(LOG_TAG, "Not an admin");

        }
    }

    public void Administer() {

        Intent intent=new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"Adding screen");
        startActivityForResult(intent,RESULT_ENABLE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch (requestCode){
           case RESULT_ENABLE:
               if(requestCode==Activity.RESULT_OK) {

                   Toast.makeText(this, "You Have Enable Device Mananger", Toast.LENGTH_SHORT).show();
               }else

                   Administer();

                   Toast.makeText(this, "Please ACTIVATE ", Toast.LENGTH_LONG).show();
               break;
       }


        super.onActivityResult(requestCode, resultCode, data);
    }
}