package com.uniyaz.ui.page;

import com.uniyaz.core.domain.*;
import com.uniyaz.core.domain.Panel;
import com.uniyaz.core.domain.Soru;
import com.uniyaz.core.service.SoruService;
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
public class SoruListePage extends VerticalLayout {

    private VerticalLayout mainLayout;
    private Table table;
    private Container container;
    private Panel panel;

    public SoruListePage(){
        this(new Panel());
    }

    public SoruListePage(Panel panel) {
        SyUI syUI = (SyUI) SyUI.getCurrent();
        this.panel=syUI.getPanel();
        //this.panel=panel;
        setSizeFull();
        buildMainLayout();
        addComponent(mainLayout);

        setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);

        fillTable();

        //Button button=buildSoruButton();
        //addComponents(button);
    }

    private void buildMainLayout() {

        mainLayout = new VerticalLayout();
        mainLayout.setSizeUndefined();

        Soru soru=new Soru(panel);
        Button ekleButton = buildEkleButton(soru);
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
        table.setColumnHeaders("ID", "ADI", "SECENEK_TIPI","", "");
    }

    private void buildContainer() {

        container = new IndexedContainer();
        container.addContainerProperty("id", Long.class, null);
        container.addContainerProperty("adi", String.class, null);
        container.addContainerProperty("secenek_tipi", String.class, null);
        container.addContainerProperty("guncelle", Button.class, null);
       // container.addContainerProperty("secim", Button.class, null);
        container.addContainerProperty("secenek", Button.class, null);

    }

    private void fillTable() {

        SoruService soruService = new SoruService();
        container.removeAllItems();
        List<Soru> soruList = soruService.findAllbyAnketID(panel.getId());//soruService.findAllHql();
        for (Soru soru : soruList) {
            Item item = container.addItem(soru);
            item.getItemProperty("id").setValue(soru.getId());
            item.getItemProperty("adi").setValue(soru.getAdi());
            item.getItemProperty("secenek_tipi").setValue(soru.getSecenek_tipi());

            Button guncelle = buildGuncelleButton(soru);
            item.getItemProperty("guncelle").setValue(guncelle);

           // Button secim = buildSecimButton(soru);
         //   item.getItemProperty("secim").setValue(secim);

            Button siparisButton = buildSiparisButton(soru);
            item.getItemProperty("secenek").setValue(siparisButton);


        }

    }

        private Button buildSoruButton() {

            Button secim = new Button();
            secim.setIcon(FontAwesome.CHECK);
            secim.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    SyUI syUI = (SyUI) SyUI.getCurrent();
                    ContentComponent  contentComponent = syUI.getContentComponent();
                    SoruPage soruPage = new SoruPage();
                    contentComponent.addComponent(soruPage);

                }
            });
            return secim;
        }

    private Button buildSecimButton(Soru soru) {

        Button secim = new Button();
        secim.setIcon(FontAwesome.CHECK);
        secim.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                SyUI syUI = (SyUI) SyUI.getCurrent();
                syUI.setSoru(soru);
            }
        });
        return secim;
    }


    private Button buildSiparisButton(Soru soru) {
        Button siparisButton = new Button();
        siparisButton.setIcon(FontAwesome.PLUS);
        siparisButton.setCaption("Secenek Ekle");
        siparisButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

              //  SyUI syUI = (SyUI) SyUI.getCurrent();
              //  ContentComponent contentComponent = syUI.getContentComponent();
               // Soru soru=new Soru();
               // soru.setSoru(soru);
                Secenek secenek=new Secenek(soru);
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

    private Button buildGuncelleButton(Soru soru) {
        Button siparisButton = new Button();
        siparisButton.setIcon(FontAwesome.EDIT);
        siparisButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                //  SyUI syUI = (SyUI) SyUI.getCurrent();
                //  ContentComponent contentComponent = syUI.getContentComponent();

                SoruPage soruPage = new SoruPage(soru);

                Window window = new Window();
                window.setCaption("EKLE");
                window.setClosable(true);
                window.setWindowMode(WindowMode.NORMAL);
                window.setWidth(30, Unit.PERCENTAGE);
                window.setHeight(30, Unit.PERCENTAGE);
                window.setResizable(true);
                window.center();
                window.setContent(soruPage);

                SyUI syUI = (SyUI) SyUI.getCurrent();
                syUI.addWindow(window);
            }
        });
        return siparisButton;
    }


    private Button buildEkleButton(Soru soru) {
        Button ekleButton = new Button();
        ekleButton.setIcon(FontAwesome.PLUS);
        ekleButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                //  SyUI syUI = (SyUI) SyUI.getCurrent();
                //  ContentComponent contentComponent = syUI.getContentComponent();

                SoruPage soruPage = new SoruPage(soru);

                Window window = new Window();
                window.setCaption("EKLE");
                window.setClosable(true);
                window.setWindowMode(WindowMode.NORMAL);
                window.setWidth(30, Unit.PERCENTAGE);
                window.setHeight(30, Unit.PERCENTAGE);
                window.setResizable(true);
                window.center();
                window.setContent(soruPage);

                SyUI syUI = (SyUI) SyUI.getCurrent();
                syUI.addWindow(window);
            }
        });
        return ekleButton;
    }
}