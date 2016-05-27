package me.hg.ilnaz.bankapp.events;

import lombok.Getter;
import me.hg.ilnaz.bankapp.api.Bank;

/**
 * Created by ilnaz on 25.05.16, 5:46.
 */
public class OneBankEvent {
    @Getter
    private String bic;
    public OneBankEvent(String bic) {
        this.bic = bic;
    }
}
