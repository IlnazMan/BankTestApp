package me.hg.ilnaz.bankapp.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Delete;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.hg.ilnaz.bankapp.R;
import me.hg.ilnaz.bankapp.api.Bank;
import me.hg.ilnaz.bankapp.events.OneBankEvent;

/**
 * Created by ilnaz on 25.05.16, 4:17.
 */
public class BanksAdapter extends RecyclerView.Adapter<BanksAdapter.BankHolder> {
    private List<Bank> banks;
    private List<Bank> filtered;

    public BanksAdapter() {
        super();
    }

    public void setBanks(List<Bank> banks) {
        this.banks = banks;
        Collections.sort(this.banks, new Comparator<Bank>() {
            @Override
            public int compare(Bank lhs, Bank rhs) {
                return lhs.getShortName().compareTo(rhs.getShortName());
            }
        });
        filtered = banks;
    }

    public void saveOnDb() {
        if (banks != null)
            for (Bank bank : banks) {
                new Delete().from(Bank.class).where("bic = ?", bank.getBic()).execute();
                bank.save();
            }
    }

    public void onSearch(String query) {
        if (banks == null || banks.isEmpty()) return;
        filtered = new ArrayList<>();
        for (Bank b :
                banks) {
            if (b.getShortName().toLowerCase().contains(query.toLowerCase()))
                filtered.add(b);
        }
        notifyDataSetChanged();
    }

    @Override
    public BankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BankHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank, parent, false));
    }

    @Override
    public void onBindViewHolder(BankHolder holder, int position) {
        holder.draw(filtered.get(position));
    }

    @Override
    public int getItemCount() {
        return filtered != null ? filtered.size() : 0;
    }

    class BankHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.bic)
        TextView bic;

        public BankHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void draw(final Bank bank) {
            name.setText(bank.getShortName());
            bic.setText(bank.getBic());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new OneBankEvent(bank.getBic()));
                }
            });
        }
    }
}
