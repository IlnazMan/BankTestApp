package me.hg.ilnaz.bankapp.fragments;

import lombok.Getter;

/**
 * Created by ilnaz on 25.05.16, 3:52.
 */
public class FragmentInfo {
    @Getter
    private boolean viewInject;

    @Getter
    private int resId;

    @Getter
    private int menuResId;

    @Getter
    private int titleResId;

    public FragmentInfo(int resId) {
        this.resId = resId;
    }

    public FragmentInfo setMenuResId(int menuResId) {
        this.menuResId = menuResId;
        return this;
    }

    public FragmentInfo setTitleResId(int titleResId) {
        this.titleResId = titleResId;
        return this;
    }

    public FragmentInfo makeViewInject() {
        this.viewInject = true;
        return this;
    }
}
