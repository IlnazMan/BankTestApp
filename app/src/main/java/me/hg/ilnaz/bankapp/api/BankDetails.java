package me.hg.ilnaz.bankapp.api;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by ilnaz on 25.05.16, 3:26.
 */
@Table(name = "BanksDetails")
public class BankDetails extends Model{
    @Column(name = "name", index = true) @Getter private String name;
    @Column(name = "city") @Getter private String city;
    @Column(name = "address") @Getter private String address;
    @Column(name = "bic", index = true) @Getter private String bic;
    @Column(name = "ks") @Getter private String ks;
    @Column(name = "tel") @Getter private String tel;
    @Getter private String limit;
}
