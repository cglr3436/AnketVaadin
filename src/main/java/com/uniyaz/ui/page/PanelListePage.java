package com.uniyaz.ui.page;

import com.uniyaz.core.domain.Anket;
import com.uniyaz.core.domain.Panel;
import com.uniyaz.core.domain.Soru;
import com.uniyaz.core.service.PanelService;
import com.uniyaz.ui.SyUI;
import com.uniyaz.ui.component.ContentComponent;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.*;

import java.util.List;

public class PanelListePage extends VerticalLayout {

    private VerticalLayout mainLayout;
    private Table table;
    private Container container;
    private Anket anket;

    public PanelListePage() {
        this(new Anket());
    }

    public PanelListePage(Anket anket) {
        SyUI syUI = (SyUI) SyUI.getCurrent();
        this.anket = syUI.getAnket();
        //this.anket=anket;
        setSizeFull();
        buildMainLayout();
        addComponent(mainLayout);

        setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);

        fillTable();

    }

    private void buildMainLayout() {

        mainLayout = new VerticalLayout();
        mainLayout.setSizeUndefined();

        Panel panel = new Panel(anket);
        Button ekleButton = buildEkleButton(panel);
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
        table.setColumnHeaders("ID", "AD", "", "");
    }

    private void buildContainer() {

        container = new IndexedContainer();
        container.addContainerProperty("id", Long.class, null);
        container.addContainerProperty("adi", String.class, null);
        container.addContainerProperty("guncelle", Button.class, null);
        container.addContainerProperty("panel", Button.class, null);

    }

    public void fillTable() {

        PanelService panelService = new PanelService();
        container.removeAllItems();
        List<Panel> panelList = panelService.findAllbyAnketID(anket.getId());
        for (Panel panel : panelList) {
            Item item = container.addItem(panel);
            item.getItemProperty("id").setValue(panel.getId());
            item.getItemProperty("adi").setValue(panel.getAdi());

            Button guncelle = buildGuncelleButton(panel);
            item.getItemProperty("guncelle").setValue(guncelle);

            Button siparisButton = buildSiparisButton(panel);
            item.getItemProperty("panel").setValue(siparisButton);


        }

    }

    private Button buildPanelButton() {

        Button secim = new Button();
        secim.setIcon(FontAwesome.CHECK);
        secim.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                SyUI syUI = (SyUI) SyUI.getCurrent();
                ContentComponent contentComponent = syUI.getContentComponent();
                PanelPage panelPage = new PanelPage();
                contentComponent.addComponent(panelPage);

            }
        });
        return secim;
    }

    private Button buildSecimButton(Panel panel) {

        Button secim = new Button();
        secim.setIcon(FontAwesome.CHECK);
        secim.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                SyUI syUI = (SyUI) SyUI.getCurrent();
                syUI.setPanel(panel);
            }
        });
        return secim;
    }


    private Button buildSiparisButton(Panel panel) {
        Button siparisButton = new Button();
        siparisButton.setIcon(FontAwesome.PLUS);
        siparisButton.setCaption("Soru Ekle");
        siparisButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                Soru soru = new Soru(panel);

                SoruPage soruPage = new SoruPage(soru);  ///*********

                Window window = new Window();
                window.setCaption("EKLE");
                window.setClosable(true);
                window.setWindowMode(WindowMode.NORMAL);
                window.setWidth(30, Unit.PERCENTAGE);
                window.setHeight(50, Unit.PERCENTAGE);
                window.setResizable(true);
                window.center();
                window.setContent(soruPage);

                SyUI syUI = (SyUI) SyUI.getCurrent();
                syUI.addWindow(window);
            }
        });
        return siparisButton;
    }

    private Button buildGuncelleButton(Panel panel) {
        Button siparisButton = new Button();
        siparisButton.setIcon(FontAwesome.EDIT);
        siparisButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

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


    private Button buildEkleButton(Panel panel) {
        Button ekleButton = new Button();
        ekleButton.setIcon(FontAwesome.PLUS);
        ekleButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

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