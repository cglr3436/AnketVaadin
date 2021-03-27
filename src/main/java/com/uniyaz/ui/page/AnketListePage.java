package com.uniyaz.ui.page;


import com.uniyaz.core.domain.Anket;
import com.uniyaz.core.domain.Panel;
import com.uniyaz.core.service.AnketService;
import com.uniyaz.ui.SyUI;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.*;

import java.util.List;

public class AnketListePage extends VerticalLayout {

    private VerticalLayout mainLayout;
    private Table table;
    private Container container;

    public AnketListePage() {

        setSizeFull();
        buildMainLayout();
        addComponent(mainLayout);

        setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);

        fillTable();

    }

    private void buildMainLayout() {

        mainLayout = new VerticalLayout();
        mainLayout.setSizeUndefined();

        Anket anket = new Anket();
        Button ekleButton = buildEkleButton(anket);
        mainLayout.addComponents(ekleButton);

        buildTable();
        mainLayout.addComponent(table);


    }

    public Table getTable() {
        return table;
    }

    private void buildTable() {

        table = new Table();
        table.setSelectable(true);
        buildContainer();
        table.setContainerDataSource(container);
        table.setColumnHeaders("ID", "ÜNVAN", "", "");
    }

    private void buildContainer() {

        container = new IndexedContainer();
        container.addContainerProperty("id", Long.class, null);
        container.addContainerProperty("adi", String.class, null);
        container.addContainerProperty("guncelle", Button.class, null);
        container.addContainerProperty("panel", Button.class, null);

    }

    public void fillTable() {
        container.removeAllItems();
        AnketService anketService = new AnketService();
        List<Anket> anketList = anketService.findAllHql();
        for (Anket anket : anketList) {
            Item item = container.addItem(anket);
            item.getItemProperty("id").setValue(anket.getId());
            item.getItemProperty("adi").setValue(anket.getAdi());

            Button guncelle = buildGuncelleButton(anket);
            item.getItemProperty("guncelle").setValue(guncelle);

            Button siparisButton = buildSiparisButton(anket);
            item.getItemProperty("panel").setValue(siparisButton);


        }

    }

    private Button buildSiparisButton(Anket anket) {
        Button siparisButton = new Button();
        siparisButton.setIcon(FontAwesome.PLUS);
        siparisButton.setCaption("Panel Ekle");
        siparisButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                Panel panel = new Panel(anket);
                PanelPage panelPage = new PanelPage(panel);

                Window window = new Window();
                window.setCaption("EKLE");
                window.setClosable(true);
                window.setWindowMode(WindowMode.NORMAL);
                window.setWidth(30, Unit.PERCENTAGE);
                window.setHeight(50, Unit.PERCENTAGE);
                window.setResizable(true);
                window.center();

                window.setContent(panelPage);

                SyUI syUI = (SyUI) SyUI.getCurrent();
                syUI.addWindow(window);
            }
        });
        return siparisButton;
    }

    private Button buildGuncelleButton(Anket anket) {
        Button siparisButton = new Button();
        siparisButton.setIcon(FontAwesome.EDIT);
        siparisButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                AnketPage anketPage = new AnketPage(anket);

                Window window = new Window();
                window.setCaption("EKLE");
                window.setClosable(true);
                window.setWindowMode(WindowMode.NORMAL);
                window.setWidth(30, Unit.PERCENTAGE);
                window.setHeight(50, Unit.PERCENTAGE);
                window.setResizable(true);
                window.center();
                window.setContent(anketPage);

                SyUI syUI = (SyUI) SyUI.getCurrent();
                syUI.addWindow(window);
            }
        });
        return siparisButton;
    }


    private Button buildEkleButton(Anket anket) {
        Button ekleButton = new Button();
        ekleButton.setIcon(FontAwesome.PLUS);
        ekleButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                AnketPage anketPage = new AnketPage(anket);

                Window window = new Window();
                window.setCaption("EKLE");
                window.setClosable(true);
                window.setWindowMode(WindowMode.NORMAL);
                window.setWidth(30, Unit.PERCENTAGE);
                window.setHeight(50, Unit.PERCENTAGE);
                window.setResizable(true);
                window.center();
                window.setContent(anketPage);

                SyUI syUI = (SyUI) SyUI.getCurrent();
                syUI.addWindow(window);
                window.addCloseListener(new Window.CloseListener()
                {
                    @Override
                    public void windowClose(Window.CloseEvent e)
                    {
                        fillTable();
                    }
                });
            }
        });
        return ekleButton;
    }
}