package me.hg.ilnaz.bankapp.api;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import lombok.Getter;

/**
 * Created by ilnaz on 25.05.16, 3:43.
 */
@Root(name = "Record")
@Table(name = "Banks", id = "_id")
public class Bank extends Model {
    @Attribute(name = "ID", required = false)
    private long id;
    @Attribute(name = "DU", required = false)
    @Getter
    private String du;

    @Column(name = "name", index = true)
    @Element(name = "ShortName")
    @Getter
    private String shortName;

    @Column(name = "bic", index = true)
    @Element(name = "Bic")
    @Getter
    private String bic;

    public Bank() {
        super();
    }

    public Bank(long id, String du, String shortName, String bic) {
        super();
        this.id = id;
        this.du = du;
        this.shortName = shortName;
        this.bic = bic;
    }
}
