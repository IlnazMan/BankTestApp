package me.hg.ilnaz.bankapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ilnaz on 25.05.16, 6:22.
 */
public class DoubleTextView extends LinearLayout {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.value)
    TextView value;
    @StringRes
    private int titleId;
    @StringRes
    private int valueId;
    private float titlePadding;
    private float titlePaddiLeft;
    private float titlePaddiRight;
    private float titlePaddiTop;
    private float titlePaddiBottom;
    private float titleMarginLeft;
    private float titleMarginRight;
    private float valuePadding;
    private float valuePaddiLeft;
    private float valuePaddiRight;
    private float valuePaddiTop;
    private float valuePaddiBottom;
    private float valueMarginLeft;
    private float valueMarginRight;


    public DoubleTextView(Context context) {
        super(context);
        init(context, null);
    }

    public DoubleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.widget_double_text, this);
        ButterKnife.bind(this);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DoubleTextView, 0, 0);
        try {
            titleId = ta.getResourceId(R.styleable.DoubleTextView_label, 0);
            valueId = ta.getResourceId(R.styleable.DoubleTextView_text, 0);
            titlePadding = ta.getDimension(R.styleable.DoubleTextView_title_padding, 0);
            titlePaddiLeft = ta.getDimension(R.styleable.DoubleTextView_title_padding_left, 0);
            titlePaddiRight = ta.getDimension(R.styleable.DoubleTextView_title_padding_right, 0);
            titlePaddiTop = ta.getDimension(R.styleable.DoubleTextView_title_padding_top, 0);
            titlePaddiBottom = ta.getDimension(R.styleable.DoubleTextView_title_padding_bottom, 0);
            titleMarginLeft = ta.getDimension(R.styleable.DoubleTextView_title_margin_left, 0);
            titleMarginRight = ta.getDimension(R.styleable.DoubleTextView_title_margin_right, 0);
            valuePadding = ta.getDimension(R.styleable.DoubleTextView_value_padding, 0);
            valuePaddiLeft = ta.getDimension(R.styleable.DoubleTextView_value_padding_left, 0);
            valuePaddiRight = ta.getDimension(R.styleable.DoubleTextView_value_padding_right, 0);
            valuePaddiTop = ta.getDimension(R.styleable.DoubleTextView_value_padding_top, 0);
            valuePaddiBottom = ta.getDimension(R.styleable.DoubleTextView_value_padding_bottom, 0);
            valueMarginLeft = ta.getDimension(R.styleable.DoubleTextView_value_margin_left, 0);
            valueMarginRight = ta.getDimension(R.styleable.DoubleTextView_value_margin_right, 0);
        } finally {
            ta.recycle();
        }
        if (titleId != 0) {
            title.setText(titleId);
        }
        if (valueId != 0) {
            value.setText(valueId);
        }

        if (titlePadding == 0) {
            title.setPadding((int) titlePaddiLeft, (int) titlePaddiTop, (int) titlePaddiRight, (int) titlePaddiBottom);
        } else {
            int padding = (int) titlePadding;
            title.setPadding(padding, padding, padding, padding);
        }

        setMargins(title, (int) titleMarginLeft, 0, (int) titleMarginRight, 0);
        if (valuePadding == 0) {
            value.setPadding((int) valuePaddiLeft, (int) valuePaddiTop, (int) valuePaddiRight, (int) valuePaddiBottom);
        } else {
            int padding = (int) valuePadding;
            value.setPadding(padding, padding, padding, padding);
        }

        setMargins(value, (int) valueMarginLeft, 0, (int) valueMarginRight, 0);
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public CharSequence getTitle() {
        return title.getText();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public CharSequence getValue() {
        return value.getText();
    }

    public void setValue(String value) {
        this.value.setText(value);
    }


}
