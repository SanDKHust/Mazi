package vn.edu.hust.soict.khacsan.myapp.model.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import vn.edu.hust.soict.khacsan.myapp.R;

public class DialogConfirm {
    private static Dialog mYesNoDilalog;
    public static void showYesNoDialog(final Activity activity,String msg){
        if(mYesNoDilalog == null){
            mYesNoDilalog = new Dialog(activity);
            mYesNoDilalog.setContentView(R.layout.yes_no_dialog);
            mYesNoDilalog.findViewById(R.id.btnYes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });
            mYesNoDilalog.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mYesNoDilalog.dismiss();
                }
            });
        }
        ((TextView)mYesNoDilalog.findViewById(R.id.text_message)).setText(msg);
        mYesNoDilalog.show();
    }
}
