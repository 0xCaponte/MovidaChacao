package com.reto.chacao.postdetail.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.reto.chacao.R;
import com.reto.chacao.statics.MovidaValues;
import com.reto.chacao.view.ZoomImageView;

/**
 * Created by Eduardo Luttinger on 03/06/2015.
 */
public class ZoomableImageDialog extends DialogFragment implements View.OnClickListener {


    private ZoomImageView mZoomableImage;

    private ImageView mCloseDialogButton;

    private View mContainer;

    private int mImage;


    public ZoomableImageDialog() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(Boolean.TRUE);
        mImage = (Integer)getArguments().get(MovidaValues.IMAGE_TO_ZOOM_ID);
        setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme_Translucent_NoTitleBar);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContainer = inflater.inflate(R.layout.dialog_zoom_image_view, container, false);

        setViews();
        setBehaviors();

        return mContainer;
    }




    private void setViews() {
        mCloseDialogButton = (ImageView) mContainer.findViewById(R.id.zoomCloseDialog);
        mZoomableImage = (ZoomImageView) mContainer.findViewById(R.id.zoomImage);
    }

    private void setBehaviors(){
        mCloseDialogButton.setOnClickListener(this);
        mZoomableImage.setImageResource(mImage);
    }

    @Override
    public void onClick(View views) {

        switch (views.getId()) {
            case R.id.zoomCloseDialog:
                dismiss();
                break;
        }

    }
}
