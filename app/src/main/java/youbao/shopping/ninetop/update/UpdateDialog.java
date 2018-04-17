package youbao.shopping.ninetop.update;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

import youbao.shopping.R;

public class UpdateDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        UpdateDialoger.Builder builder= new UpdateDialoger.Builder(getActivity());
        builder.setTitle(R.string.newUpdateAvailable);
        builder.setIcon(R.mipmap.ub);
        builder.setMessage(getArguments().getString(Constants.APK_UPDATE_CONTENT))
                .setPositiveButton("立即下载",   new View.OnClickListener()  {
                    @Override
                    public void onClick(View v) {
                        goToDownload();
                        dismiss();

        }
    })
            .setNegativeButton("以后再说", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }


    private void goToDownload() {
    	Intent intent=new Intent(getActivity().getApplicationContext(),DownloadService.class);
    	intent.putExtra(Constants.APK_DOWNLOAD_URL, getArguments().getString(Constants.APK_DOWNLOAD_URL));
    	getActivity().startService(intent);
    }


}
