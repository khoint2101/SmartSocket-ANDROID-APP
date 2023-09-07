package com.graduate.smartsocket.fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class Profile extends Fragment {
    private Uri mUri;
    private ImageView imgAvatar;

    public void setUri(Uri mUri) {
        this.mUri = mUri;
    }
    public void setBitmapImageView(Bitmap bitmapImageView) {
        imgAvatar.setImageBitmap(bitmapImageView);
    }
}
