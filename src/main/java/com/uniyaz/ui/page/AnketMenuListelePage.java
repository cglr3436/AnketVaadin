package com.uniyaz.ui.page;

import com.uniyaz.core.domain.Anket;
import com.uniyaz.core.service.AnketCevapService;
import com.uniyaz.core.service.AnketService;
import com.uniyaz.ui.SyUI;
import com.vaadin.data.Container;
import com.vaadin.ui.*;

import java.util.List;

import com.uniyaz.ui.component.ContentComponent;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.window.WindowMode;


public class AnketMenuListelePage extends VerticalLayout {

    private VerticalLayout mainLayout;
    private Table table;
    private Container container;

    public AnketMenuListelePage() {

        setSizeFull();
        buildMainLayout();
        addComponent(mainLayout);

        setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);
        fillTable();

    }

    private void buildMainLayout() {

        mainLayout = new VerticalLayout();
        mainLayout.setSizeUndefined();

        buildTable();
        mainLayout.addComponent(table);

    }

    private void buildTable() {

        table = new Table();
        table.setSelectable(true);
        buildContainer();
        table.setContainerDataSource(container);
        table.setColumnHeaders("ANKET", "KULLANICI", "");
    }

    private void buildContainer() {

        container = new IndexedContainer();
        container.addContainerProperty("adi", String.class, null);
        container.addContainerProperty("kullanici", String.class, null);
        container.addContainerProperty("guncelle", Button.class, null);

    }

    public void fillTable() {
        container.removeAllItems();
        AnketCevapService anketCevapService = new AnketCevapService();
        List<Object[]> anketKullaniciList = anketCevapService.findAllAnketKullaniciHql();

        for (Object[] anketKullanici : anketKullaniciList) {
            Item item = container.addItem(anketKullanici);
            String anket_adi = (String) anketKullanici[0];
            String kullanici = (String) anketKullanici[1];
            item.getItemProperty("adi").setValue(anket_adi);
            item.getItemProperty("kullanici").setValue(kullanici);
            Button guncelle = buildGuncelleButton(anket_adi, kullanici);
            item.getItemProperty("guncelle").setValue(guncelle);

        }

    }

    private Button buildGuncelleButton(String anketIsmi, String kullanici) {
        Button siparisButton = new Button();
        siparisButton.setIcon(FontAwesome.EDIT);
        siparisButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    SyUI syUI = (SyUI) UI.getCurrent();
                    AnketService anketService = new AnketService();
                    Anket anket = anketService.findbyAdi(anketIsmi);

                    syUI.setAnket(anket);
                    AnketOlusturPage anketOlusturPage = new AnketOlusturPage(kullanici);

                    com.vaadin.ui.Panel mainPanel = new com.vaadin.ui.Panel();
                    anketOlusturPage.setSizeFull();
                    mainPanel.setContent(anketOlusturPage);

                    Window window = new Window();
                    window.setCaption("");
                    window.setScrollTop(5);
                    window.setModal(true);
                    window.setClosable(true);
                    window.setWindowMode(WindowMode.NORMAL);
                    window.setWidth(50, Unit.PERCENTAGE);
                    window.setHeight(80, Unit.PERCENTAGE);
                    window.setResizable(true);
                    window.center();
                    window.setContent(mainPanel);
                    syUI.addWindow(window);
                    window.addCloseListener(new Window.CloseListener() {
                        @Override
                        public void windowClose(Window.CloseEvent e) {
                            fillTable();
                        }
                    });
                } catch (Exception e) {
                    Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        });

        return siparisButton;
    }


}