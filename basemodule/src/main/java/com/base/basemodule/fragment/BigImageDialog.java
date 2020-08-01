package com.base.basemodule.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.ImageView;

import com.base.basemodule.R;
import com.base.basemodule.utils.image.ImageUtil;

public class BigImageDialog extends BaseDislogFragment {

    ImageView ivBg;
    String url;

    public BigImageDialog() {
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        url = args.getString("url");
    }

    @Override
    protected void displayDialog(Bundle savedInstanceState, AlertDialog.Builder builder) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_big_image_dialog;
    }

    @Override
    protected boolean isCanceledOnTouchOutside() {
        return true;
    }

    @Override
    protected boolean isTransparent() {
        return true;
    }

    @Override
    protected boolean setView(View view) {
        ivBg = view.findViewById(R.id.ivBg);
        ImageUtil.loadImage(getContext(),url,ivBg);
        ivBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
        return false;
    }
}
