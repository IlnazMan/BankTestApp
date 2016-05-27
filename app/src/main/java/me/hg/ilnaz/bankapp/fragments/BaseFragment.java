package me.hg.ilnaz.bankapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import me.hg.ilnaz.bankapp.MainActivity;

/**
 * Created by ilnaz on 25.05.16, 3:56.
 */
public abstract class BaseFragment extends Fragment {

    public abstract FragmentInfo getFragmentInfo();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentInfo().getResId(), container, false);
        if (getFragmentInfo().isViewInject()) {
            ButterKnife.bind(this, view);
        }
        ActionBar actionBar = ((MainActivity) getActivity()).getToolbar();
        if (getFragmentInfo().getTitleResId() != 0 && actionBar != null) {
            actionBar.setTitle(getFragmentInfo().getTitleResId());
        }
        if(getFragmentInfo().getMenuResId()!=0){
            setHasOptionsMenu(true);
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (getFragmentInfo().getMenuResId() != 0)
            inflater.inflate(getFragmentInfo().getMenuResId(), menu);
    }
}
