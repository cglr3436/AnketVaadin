package com.uniyaz.ui.component;

import com.uniyaz.core.domain.EnumCinsiyet;
import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;

/**
 *
 */
public class CinsiyetCombobox extends ComboBox {

    public CinsiyetCombobox() {
        fillCombobox();
    }

    private void fillCombobox() {

        for (EnumCinsiyet cinsiyet : EnumCinsiyet.values()) {
            Item item = addItem(cinsiyet);
        }
    }
}