package com.uniyaz.ui.page;

import com.uniyaz.core.domain.EnumSecim;
import com.uniyaz.core.domain.Soru;
import com.uniyaz.core.domain.Secenek;
import com.uniyaz.core.service.SecenekService;
import com.uniyaz.ui.SyUI;
import com.uniyaz.ui.component.ContentComponent;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.*;

import java.util.List;

/**
 * Created by AKARTAL on 12.3.2021.
 */
public class SecenekListePage extends VerticalLayout {

    private VerticalLayout mainLayout;
    private Table table;
    private Container container;
    private Soru soru;

    public SecenekListePage(){
        this(new Soru());
    }

    public SecenekListePage(Soru soru) {
        SyUI syUI = (SyUI) SyUI.getCurrent();
        this.soru=syUI.getSoru();
        //this.soru=soru;
        setSizeFull();
        buildMainLayout();
        addComponent(mainLayout);

        setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);

        fillTable();

        //Button button=buildSecenekButton();
        //addComponents(button);
    }

    private void buildMainLayout() {

        mainLayout = new VerticalLayout();
        mainLayout.setSizeUndefined();

        Secenek secenek=new Secenek(soru);
        Button ekleButton = buildEkleButton(secenek);
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
        table.setColumnHeaders("ID", "ADI", "TIPI","");
    }

    private void buildContainer() {

        container = new IndexedContainer();
        container.addContainerProperty("id", Long.class, null);
        container.addContainerProperty("adi", String.class, null);
        container.addContainerProperty("tipi", String.class, null);
        container.addContainerProperty("guncelle", Button.class, null);
     //   container.addContainerProperty("secim", Button.class, null);
       // container.addContainerProperty("secenek", Button.class, null);

    }

    private void fillTable() {

        SecenekService secenekService = new SecenekService();
        container.removeAllItems();
        List<Secenek> secenekList = secenekService.findAllbyAnketID(soru.getId());//secenekService.findAllHql();
        for (Secenek secenek : secenekList) {
            Item item = container.addItem(secenek);
            item.getItemProperty("id").setValue(secenek.getId());
            item.getItemProperty("adi").setValue(secenek.getAdi());
            item.getItemProperty("tipi").setValue(secenek.getTipi());

            Button guncelle = buildGuncelleButton(secenek);
            item.getItemProperty("guncelle").setValue(guncelle);

        //    Button secim = buildSecimButton(secenek);
         //   item.getItemProperty("secim").setValue(secim);

          //  Button siparisButton = buildSiparisButton(secenek);
          //  item.getItemProperty("secenek").setValue(siparisButton);


        }

    }

        private Button buildSecenekButton() {

            Button secim = new Button();
            secim.setIcon(FontAwesome.CHECK);
            secim.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    SyUI syUI = (SyUI) SyUI.getCurrent();
                    ContentComponent  contentComponent = syUI.getContentComponent();
                    SecenekPage secenekPage = new SecenekPage();
                    contentComponent.addComponent(secenekPage);

                }
            });
            return secim;
        }

    private Button buildSecimButton(Secenek secenek) {

        Button secim = new Button();
        secim.setIcon(FontAwesome.CHECK);
        secim.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                SyUI syUI = (SyUI) SyUI.getCurrent();
                syUI.setSecenek(secenek);
            }
        });
        return secim;
    }


    private Button buildSiparisButton(Secenek secenek) {
        Button siparisButton = new Button();
        siparisButton.setIcon(FontAwesome.PLUS);
        siparisButton.setCaption("Secenek Ekle");
        siparisButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

              //  SyUI syUI = (SyUI) SyUI.getCurrent();
              //  ContentComponent contentComponent = syUI.getContentComponent();
               // Secenek secenek=new Secenek();
               // secenek.setSecenek(secenek);

                SecenekPage secenekPage = new SecenekPage(secenek);  ///*********

                Window window = new Window();
                window.setCaption("EKLE");
                window.setClosable(true);
                window.setWindowMode(WindowMode.NORMAL);
                window.setWidth(30, Unit.PERCENTAGE);
                window.setHeight(30, Unit.PERCENTAGE);
                window.setResizable(true);
                window.center();
                window.setContent(secenekPage);

                SyUI syUI = (SyUI) SyUI.getCurrent();
                syUI.addWindow(window);
            }
        });
        return siparisButton;
    }

    private Button buildGuncelleButton(Secenek secenek) {
        Button siparisButton = new Button();
        siparisButton.setIcon(FontAwesome.EDIT);
        siparisButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                //  SyUI syUI = (SyUI) SyUI.getCurrent();
                //  ContentComponent contentComponent = syUI.getContentComponent();

                SecenekPage secenekPage = new SecenekPage(secenek);

                Window window = new Window();
                window.setCaption("EKLE");
                window.setClosable(true);
                window.setWindowMode(WindowMode.NORMAL);
                window.setWidth(30, Unit.PERCENTAGE);
                window.setHeight(30, Unit.PERCENTAGE);
                window.setResizable(true);
                window.center();
                window.setContent(secenekPage);

                SyUI syUI = (SyUI) SyUI.getCurrent();
                syUI.addWindow(window);
            }
        });
        return siparisButton;
    }


    private Button buildEkleButton(Secenek secenek) {
        Button ekleButton = new Button();
        ekleButton.setIcon(FontAwesome.PLUS);
        ekleButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                //  SyUI syUI = (SyUI) SyUI.getCurrent();
                //  ContentComponent contentComponent = syUI.getContentComponent();

                SecenekPage secenekPage = new SecenekPage(secenek);

                Window window = new Window();
                window.setCaption("EKLE");
                window.setClosable(true);
                window.setWindowMode(WindowMode.NORMAL);
                window.setWidth(30, Unit.PERCENTAGE);
                window.setHeight(30, Unit.PERCENTAGE);
                window.setResizable(true);
                window.center();
                window.setContent(secenekPage);

                SyUI syUI = (SyUI) SyUI.getCurrent();
                syUI.addWindow(window);
            }
        });
        return ekleButton;
    }
}