package me.hg.ilnaz.bankapp.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.activeandroid.query.Select;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.List;

import butterknife.BindView;
import me.hg.ilnaz.bankapp.Consts;
import me.hg.ilnaz.bankapp.R;
import me.hg.ilnaz.bankapp.api.Api;
import me.hg.ilnaz.bankapp.api.Bank;
import me.hg.ilnaz.bankapp.api.Banks;
import me.hg.ilnaz.bankapp.api.RequestMaker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by ilnaz on 25.05.16, 4:08.
 */
public class BanksFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    SpinKitView spinKitView;
    private BanksAdapter adapter;

    @Override
    public FragmentInfo getFragmentInfo() {
        return new FragmentInfo(R.layout.screen_banks)
                .setTitleResId(R.string.title_banks)
                .makeViewInject();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLayout.setOnRefreshListener(this);
        setHasOptionsMenu(true);
        List<Bank> list = new Select().from(Bank.class).execute();
        adapter = new BanksAdapter();
        recyclerView.setAdapter(adapter);
        if (list == null || list.isEmpty())
            loadData();
        else {
            adapter.setBanks(list);
            adapter.notifyDataSetChanged();
        }
    }

    private void loadData() {
        spinKitView.setVisibility(View.VISIBLE);
        Api api = new RequestMaker<Api>().makeRequest(Consts.BANKS_LIST_URL, Api.class, SimpleXmlConverterFactory.create());
        Call<Banks> banksCall = api.getBanks();
        Callback<Banks> callback = new Callback<Banks>() {
            @Override
            public void onResponse(Call<Banks> call, Response<Banks> response) {
                adapter.setBanks(response.body().getBankList());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.saveOnDb();
                    }
                }).start();
                adapter.notifyDataSetChanged();
                spinKitView.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Banks> call, Throwable t) {
                //Log.wtf("LOG", );
                t.printStackTrace();
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                spinKitView.setVisibility(View.GONE);

                refreshLayout.setRefreshing(false);

            }
        };
        banksCall.enqueue(callback);
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);

        SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
        search.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.onSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.onSearch(newText);
                return true;
            }
        });
    }
}
