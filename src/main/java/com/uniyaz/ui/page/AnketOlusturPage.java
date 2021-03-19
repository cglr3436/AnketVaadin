package com.uniyaz.ui.page;

import com.uniyaz.core.domain.*;

import com.uniyaz.core.domain.Panel;
import com.uniyaz.core.service.*;
import com.uniyaz.ui.SyUI;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.*;

import java.util.*;

public class AnketOlusturPage extends VerticalLayout {
    private VerticalLayout mainLayout;

List<AnketCevap> hepsi;
    public AnketOlusturPage() {
        hepsi=new ArrayList<AnketCevap>();
        setSizeFull();
        String kullanici_adi=" ";
        buildMainLayout(kullanici_adi);
        addComponent( mainLayout );

        setComponentAlignment( mainLayout, Alignment.MIDDLE_CENTER );

        // fillTable();

        //Button button=buildAnketButton();
        //addComponents(button);
    }

    private void buildMainLayout(String kullanici_adi) {


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

                // Label soruAdi=new Label("SORU:"+soru.getAdi().toString());
                // soruici.addComponents(soruAdi);
                if (soru.getSecenek_tipi().equals( "Tekli" )) {
                    TextField SoruveCevabi = new TextField( "SORU:" + soru.getAdi() );

                    AnketCevap anketCevap= new AnketCevap(soru,SoruveCevabi,"T",kullanici_adi);
                    hepsi.add(anketCevap);
                    soruici.addComponents( SoruveCevabi );
                }

                if (soru.getSecenek_tipi().equals( "CoktanRadio" )) {

                    OptionGroup radios = new OptionGroup( "SORU:" + soru.getAdi(), secenekList );
                    radios.setImmediate( true );

                    radios.addValueChangeListener( (Property.ValueChangeEvent event) -> {
                        Secenek vitalStatus = (Secenek) event.getProperty().getValue();
                        System.out.println( "User selected a vital status name: " + vitalStatus.getTipi() + ", labeled: " + vitalStatus.toString() );
                    } );
                    AnketCevap anketCevap= new AnketCevap(soru,radios,"R",kullanici_adi);
                    hepsi.add(anketCevap);
                    soruici.addComponents( radios );
                }

                if (soru.getSecenek_tipi().equals( "CoktanCheckbox" )) {
                    for (Secenek secenek : secenekList) {
                        CheckBox checkBox = new CheckBox( secenek.getAdi() );
                        checkBox.setImmediate( true );
                        checkBox.setData( secenek );
                        secenek.setSoru( soru );
                        AnketCevap anketCevap= new AnketCevap(secenek,checkBox,"C",kullanici_adi);
                        hepsi.add(anketCevap);
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

    private Button buildEkleButton(Anket anket) {
        Button ekleButton = new Button("ANKET KAYDET");
        ekleButton.setIcon( FontAwesome.PLUS );
        ekleButton.addClickListener( new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                //  SyUI syUI = (SyUI) SyUI.getCurrent();
                //  ContentComponent contentComponent = syUI.getContentComponent();
                for (AnketCevap anketCevap:hepsi) {
                    //anketCevap.getAnket();
                    // anketCevap.getPanel();
                    Soru soru = anketCevap.getSoru();


                    if (soru.getSecenek_tipi().equals( "Tekli" )) {
                        TextField textField = (TextField) anketCevap.getComponent();
                        String cevap = textField.getValue();
                        anketCevap.setAdi( anket.getAdi() );
                        anketCevap.setTipi(soru.getSecenek_tipi());
                        anketCevap.setCevap( cevap );
                        AnketCevapService anketCevapService = new AnketCevapService();
                        anketCevapService.saveAnketCevap( anketCevap );
                    }

                    if (soru.getSecenek_tipi().equals( "CoktanRadio" )) {
                        //anketCevap.getSecenek();
                        OptionGroup radios = (OptionGroup) anketCevap.getComponent();
                        Secenek secenek = (Secenek) radios.getValue();

                      //  Secenek vitalStatus = (Secenek) radios.getPropertyDataSource().getValue();
                        anketCevap.setAdi( anket.getAdi() );
                        anketCevap.setTipi(soru.getSecenek_tipi());
                        anketCevap.setSecenek_id( secenek.getId() );
                        anketCevap.setCevap( secenek.getAdi() );
                        AnketCevapService anketCevapService = new AnketCevapService();
                        anketCevapService.saveAnketCevap( anketCevap );

                    }
                    if (soru.getSecenek_tipi().equals( "CoktanCheckbox" )) {
                        //anketCevap.getSecenek();
                        CheckBox radios = (CheckBox) anketCevap.getComponent();
                        Secenek secenek = (Secenek) radios.getData();
                        if(radios.getValue()) {
                            //  Secenek vitalStatus = (Secenek) radios.getPropertyDataSource().getValue();
                        anketCevap.setAdi( anket.getAdi() );
                        anketCevap.setTipi( secenek.getTipi() );
                        anketCevap.setSecenek_id( secenek.getId() );
                        anketCevap.setCevap( secenek.getAdi() );
                        AnketCevapService anketCevapService = new AnketCevapService();
                        anketCevapService.saveAnketCevap( anketCevap );
                        }
                    }
                }}

        } );
        return ekleButton;
    }


}
