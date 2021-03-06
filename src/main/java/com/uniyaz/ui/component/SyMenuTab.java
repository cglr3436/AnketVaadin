package com.uniyaz.ui.component;

import com.uniyaz.core.domain.Anket;
import com.uniyaz.core.domain.Panel;
import com.uniyaz.core.domain.Secenek;
import com.uniyaz.core.domain.Soru;
import com.uniyaz.ui.SyUI;
import com.uniyaz.ui.page.*;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

/**
 *
 */
public class SyMenuTab extends BasePage {

    private ContentComponent contentComponent;

    private TabSheet tabSheet;
    private TabSheet.Tab anketTab;
    private TabSheet.Tab panelTab;
    private TabSheet.Tab soruTab;
    private TabSheet.Tab secenekTab;
    private TabSheet.Tab AnketYapTab;
    private TabSheet.Tab AnketDoldurTab;
    private TabSheet.Tab AnketOlusturMenuTab;
    private TabSheet.Tab AnketMenuListeleTab;
    public Anket anket;
    public Panel panel;
    public Soru soru;
    public Secenek secenek;
    AnketListePage anketListePage;
    AnketOlusturPage anketOlusturPage;
    PanelListePage panelListePage;
    SoruListePage soruListePage;
    SecenekListePage secenekListePage;
    VerticalLayout mainlayout;
    AnketDoldurPage anketDoldurPage;
    AnketOlusturMenuPage anketOlusturMenuPage;
    AnketMenuListelePage anketMenuListelePage;


    public SyMenuTab() {
        buildMainLayout();
        addComponent(mainlayout);
    }

    @Override
    public void buildMainLayout() {
        mainlayout = new VerticalLayout();
        mainlayout.setSizeFull();

        fillTab();

    }

    private void fillTab() {
        mainlayout.removeAllComponents();
        SyUI syUI = (SyUI) UI.getCurrent();
        anket = syUI.getAnket();
        panel = syUI.getPanel();
        soru = syUI.getSoru();
        tabSheet = new TabSheet();

        anketListePage = new AnketListePage();
        anketTab = tabSheet.addTab(anketListePage);
        anketTab.setCaption("ANKET");
        anketListePage.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                anket = (Anket) itemClickEvent.getItemId();
                syUI.setAnket(anket);
                tabSheet.removeAllComponents();
                fillTab();
                tabSheet.setSelectedTab(anketTab);
            }
        });

        if (anket != null) {
            panelListePage = new PanelListePage(anket);
            panelTab = tabSheet.addTab(panelListePage);
            panelTab.setCaption("PANEL");
            panelListePage.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
                @Override
                public void itemClick(ItemClickEvent itemClickEvent) {
                    panel = (Panel) itemClickEvent.getItemId();
                    syUI.setPanel(panel);
                    tabSheet.removeAllComponents();
                    fillTab();
                    tabSheet.setSelectedTab(panelTab);
                }
            });

        }
        if (panel != null) {
            soruListePage = new SoruListePage(panel);
            soruTab = tabSheet.addTab(soruListePage);
            soruTab.setCaption("SORU");
            soruListePage.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
                @Override
                public void itemClick(ItemClickEvent itemClickEvent) {
                    soru = (Soru) itemClickEvent.getItemId();
                    syUI.setSoru(soru);
                    tabSheet.removeAllComponents();
                    fillTab();
                    tabSheet.setSelectedTab(soruTab);
                }
            });

        }
        if (soru != null) {
            secenekListePage = new SecenekListePage(soru);
            secenekTab = tabSheet.addTab(secenekListePage);
            secenekTab.setCaption("SECENEK");
            secenekListePage.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
                @Override
                public void itemClick(ItemClickEvent itemClickEvent) {
                    secenek = (Secenek) itemClickEvent.getItemId();
                    syUI.setSecenek(secenek);
                    tabSheet.removeAllComponents();
                    fillTab();
                    tabSheet.setSelectedTab(secenekTab);
                }
            });

        }

        anketOlusturMenuPage = new AnketOlusturMenuPage();
        AnketOlusturMenuTab = tabSheet.addTab(anketOlusturMenuPage);
        AnketOlusturMenuTab.setCaption("ANKET COZ");
        mainlayout.addComponents(tabSheet);

        anketMenuListelePage = new AnketMenuListelePage();
        AnketMenuListeleTab = tabSheet.addTab(anketMenuListelePage);
        AnketMenuListeleTab.setCaption("COZULMUS ANKETLER");
    //    tabSheet.setSelectedTab(AnketMenuListeleTab);

        tabSheet.addSelectedTabChangeListener(event -> {
            // the selected tab matches the expected one

            if (tabSheet.getTab(tabSheet.getSelectedTab()) == anketTab)
            {
                anketListePage.fillTable();
            }

            if (tabSheet.getTab(tabSheet.getSelectedTab()) == panelTab)
            {
                panelListePage.fillTable();
            }
            if (tabSheet.getTab(tabSheet.getSelectedTab()) == soruTab)
            {
                soruListePage.fillTable();
            }
            if (tabSheet.getTab(tabSheet.getSelectedTab()) == secenekTab)
            {
                secenekListePage.fillTable();
            }


            if (tabSheet.getTab(tabSheet.getSelectedTab()) == AnketMenuListeleTab)
            {
                anketMenuListelePage.fillTable();
            }
            // the selected content matches the expected one

        });

        mainlayout.addComponents(tabSheet);


    }


}
