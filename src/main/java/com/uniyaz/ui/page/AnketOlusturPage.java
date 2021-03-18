package com.uniyaz.ui.page;

import com.uniyaz.core.domain.Anket;
import com.uniyaz.core.domain.Panel;

import com.uniyaz.core.domain.Secenek;
import com.uniyaz.core.domain.Soru;
import com.uniyaz.core.service.AnketService;
import com.uniyaz.core.service.PanelService;
import com.uniyaz.core.service.SecenekService;
import com.uniyaz.core.service.SoruService;
import com.uniyaz.ui.SyUI;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.*;

import java.util.Arrays;
import java.util.List;

public class AnketOlusturPage extends VerticalLayout {
    private VerticalLayout mainLayout;
    private Table table;
    private Container container;

    public AnketOlusturPage() {

        setSizeFull();
        buildMainLayout();
        addComponent( mainLayout );

        setComponentAlignment( mainLayout, Alignment.MIDDLE_CENTER );

        // fillTable();

        //Button button=buildAnketButton();
        //addComponents(button);
    }

    private void buildMainLayout() {

        mainLayout = new VerticalLayout();
        mainLayout.setSizeUndefined();


        SyUI syUI = (SyUI) UI.getCurrent();
        Anket anket = syUI.getAnket();

        if (anket == null) return;
        AnketService anketService = new AnketService();
        anketService.findAllHql();

        PanelService panelService = new PanelService();

        List<Panel> panelList = panelService.findAllbyAnketID( anket.getId() );

        VerticalLayout panelici = new VerticalLayout();
        //panelici.removeAllComponents();
        for (Panel panel : panelList) {
            Label panelAdi = new Label( panel.getAdi().toString() );
            panelici.addComponents( panelAdi );

            SoruService soruService = new SoruService();
            List<Soru> soruList = soruService.findAllbyAnketID( panel.getId() );
            VerticalLayout soruici = new VerticalLayout();

            for (Soru soru : soruList) {

                SecenekService secenekService = new SecenekService();
                List<Secenek> secenekList = secenekService.findAllbyAnketID( soru.getId() );

                OptionGroup group = new OptionGroup( soru.getAdi().toString() );

                // Label soruAdi=new Label("SORU:"+soru.getAdi().toString());
                // soruici.addComponents(soruAdi);
                if (soru.getSecenek_tipi().equals( "Tekli" )) {
                    TextField SecenekAdi = new TextField( "SORU:" + soru.getAdi() );
                    soruici.addComponents( SecenekAdi );
                }

                if (soru.getSecenek_tipi().equals( "CoktanRadio" )) {

                    OptionGroup radios = new OptionGroup( "SORU:" + soru.getAdi(), secenekList );
                    radios.setImmediate( true );
                    radios.addValueChangeListener( (Property.ValueChangeEvent event) -> {
                        Secenek vitalStatus = (Secenek) event.getProperty().getValue();
                        System.out.println( "User selected a vital status name: " + vitalStatus.getTipi() + ", labeled: " + vitalStatus.toString() );
                    } );


                    soruici.addComponents( radios );
                }

                if (soru.getSecenek_tipi().equals( "CoktanCheckbox" )) {
                    for (Secenek secenek : secenekList) {
                        CheckBox checkBox = new CheckBox( secenek.getAdi() );
                        checkBox.setImmediate( true );
                        checkBox.setData( secenek );
                        checkBox.addValueChangeListener( (Property.ValueChangeEvent event) -> {
                            // String vitalStatus = (String) event.getProperty().getValue();
                            // System.out.println( "cHECKBOX: " + vitalStatus+ ", labeled: " + vitalStatus.toString() );
                        } );
                        soruici.addComponents( checkBox );
                    }
                }
            }
            panelici.addComponent( soruici );

        }
        mainLayout.addComponents( panelici );


        Button ekleButton = buildEkleButton( anket );
        mainLayout.addComponents( ekleButton );


    }

    private void fillTable() {
        container.removeAllItems();
        AnketService anketService = new AnketService();
        List<Anket> anketList = anketService.findAllHql();
        for (Anket anket : anketList) {
            Item item = container.addItem( anket );
            item.getItemProperty( "id" ).setValue( anket.getId() );
            item.getItemProperty( "adi" ).setValue( anket.getAdi() );

            //  Button guncelle = buildGuncelleButton(anket);
            // item.getItemProperty("guncelle").setValue(guncelle);


        }

    }

    private Button buildEkleButton(Anket anket) {
        Button ekleButton = new Button();
        ekleButton.setIcon( FontAwesome.PLUS );
        ekleButton.addClickListener( new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                //  SyUI syUI = (SyUI) SyUI.getCurrent();
                //  ContentComponent contentComponent = syUI.getContentComponent();

                AnketPage anketPage = new AnketPage( anket );

                Window window = new Window();
                window.setCaption( "EKLE" );
                window.setClosable( true );
                window.setWindowMode( WindowMode.NORMAL );
                window.setWidth( 30, Sizeable.Unit.PERCENTAGE );
                window.setHeight( 30, Sizeable.Unit.PERCENTAGE );
                window.setResizable( true );
                window.center();
                window.setContent( anketPage );

                SyUI syUI = (SyUI) SyUI.getCurrent();
                syUI.addWindow( window );
            }
        } );
        return ekleButton;
    }

    private void buildTable() {

        table = new Table();
        table.setSelectable( true );
        buildContainer();
        table.setContainerDataSource( container );
        table.setColumnHeaders( "ID", "ÜNVAN", "", "" );
    }

    private void buildContainer() {

        container = new IndexedContainer();
        container.addContainerProperty( "id", Long.class, null );
        container.addContainerProperty( "adi", String.class, null );
        container.addContainerProperty( "guncelle", Button.class, null );
        //  container.addContainerProperty("secim", Button.class, null);
        container.addContainerProperty( "panel", Button.class, null );

    }
}
