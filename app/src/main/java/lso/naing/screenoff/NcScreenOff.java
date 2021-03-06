package lso.naing.screenoff;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

class NcScreenOff extends DeviceAdminReceiver {
    private void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context,
                context.getString(R.string.app_name));
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context,
                context.getString(R.string.app_name));
    }

}