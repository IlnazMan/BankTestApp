package me.hg.ilnaz.bankapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import butterknife.BindView;
import me.hg.ilnaz.bankapp.Consts;
import me.hg.ilnaz.bankapp.DoubleTextView;
import me.hg.ilnaz.bankapp.R;
import me.hg.ilnaz.bankapp.api.Api;
import me.hg.ilnaz.bankapp.api.BankDetails;
import me.hg.ilnaz.bankapp.api.RequestMaker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ilnaz on 25.05.16, 5:49.
 */
public class OneBankFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private final static String BANK = "bank";

    @BindView(R.id.name)
    DoubleTextView name;
    @BindView(R.id.bic)
    DoubleTextView bic;
    @BindView(R.id.city)
    DoubleTextView city;
    @BindView(R.id.ks)
    DoubleTextView ks;
    @BindView(R.id.address)
    DoubleTextView address;
    @BindView(R.id.tel)
    DoubleTextView tel;

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    private String bankBic;

    public static Fragment getInstance(String bic) {
        Bundle b = new Bundle();
        b.putString(BANK, bic);
        Fragment f = new OneBankFragment();
        f.setArguments(b);
        return f;
    }

    @Override
    public FragmentInfo getFragmentInfo() {
        return new FragmentInfo(R.layout.screen_one_bank)
                .makeViewInject();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipe.setOnRefreshListener(this);
        Bundle args = getArguments();
        if (args == null)
            args = savedInstanceState;
        if (args != null) {
            bankBic = args.getString(BANK);
            BankDetails b = new Select().from(BankDetails.class).where("bic = ?", new String[]{bankBic}).executeSingle();
            if (b == null)
                loadData(bankBic);
            else
                setBank(b);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BANK, bankBic);
    }

    private void setBank(BankDetails details) {
        name.setValue(details.getName());
        address.setValue(details.getAddress());
        bic.setValue(details.getBic());
        tel.setValue(details.getTel());
        ks.setValue(details.getKs());
        city.setValue(details.getCity());
    }

    private void loadData(final String bicArg) {
        Call<BankDetails> detailsCall = new RequestMaker<Api>().makeRequest(Consts.ONE_BANK_URL, Api.class, GsonConverterFactory.create()).getBankByBic(bicArg, true);
        Callback<BankDetails> callback = new Callback<BankDetails>() {
            @Override
            public void onResponse(Call<BankDetails> call, Response<BankDetails> response) {

                if (response.body().getLimit().equals("0"))
                    Toast.makeText(getActivity(), R.string.toast_limit, Toast.LENGTH_SHORT).show();
                else{
                    setBank(response.body());
                    new Delete().from(BankDetails.class).where("bic = ?", bicArg).execute();
                    response.body().save();
                }
                swipe.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<BankDetails> call, Throwable t) {
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        };
        detailsCall.enqueue(callback);
    }

    @Override
    public void onRefresh() {
        loadData(bankBic);
    }
}
