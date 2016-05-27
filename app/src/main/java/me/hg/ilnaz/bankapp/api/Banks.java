package me.hg.ilnaz.bankapp.api;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ilnaz on 25.05.16, 3:35.
 */
@Data
@NoArgsConstructor
@Root(name = "BicCode")
public class Banks {
    @Attribute(name = "name", required = false)
    private String name;

    @ElementList(inline = true)
    private List<Bank> bankList;
}
