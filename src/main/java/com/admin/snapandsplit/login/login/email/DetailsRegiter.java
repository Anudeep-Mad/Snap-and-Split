package com.admin.snapandsplit.login.login.email;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.admin.snapandsplit.R;

/**
 * Created by Admin on 4/16/2016.
 */
public class DetailsRegiter extends Fragment{

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewGroup = inflater.inflate (R.layout.details_fragment, container, false);

        return viewGroup;
    }

}
