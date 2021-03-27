package com.uniyaz.ui.page;

import com.uniyaz.core.domain.Panel;
import com.uniyaz.core.domain.*;
import com.uniyaz.core.service.AnketCevapService;
import com.uniyaz.core.service.PanelService;
import com.uniyaz.core.service.SecenekService;
import com.uniyaz.core.service.SoruService;
import com.uniyaz.ui.SyUI;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

public class AnketDoldurPage extends VerticalLayout {
    private VerticalLayout mainLayout;

List<AnketCevap> hepsi;
    public AnketDoldurPage() {
        hepsi=new ArrayList<AnketCevap>();
        setSizeFull();
        String kullanici_adi="";
        SyUI syUI = (SyUI) UI.getCurrent();
        Anket anket = syUI.getAnket();
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
        AnketCevapService anketCevapService = new AnketCevapService();
         hepsi= anketCevapService.findAllbyAnketID(anket.getAdi(),kullanici_adi);

        PanelService panelService = new PanelService();

        List<Panel> panelList = panelService.findAllbyAnketID( anket.getId() );


        Label anketBaslik = new Label( anket.getAdi().toString() +" ANKETI");
        mainLayout.addComponents( anketBaslik );
        //panelici.removeAllComponents();
        for (Panel panel : panelList) {
            VerticalLayout panelici = new VerticalLayout();
            com.vaadin.ui.Panel VaadinPanel=new com.vaadin.ui.Panel();
            VaadinPanel.setCaption(panel.getAdi().toString());


           // Label panelAdi = new Label( panel.getAdi().toString() );
           // panelici.addComponents( panelAdi );

            SoruService soruService = new SoruService();
            List<Soru> soruList = soruService.findAllbyAnketID( panel.getId() );
            VerticalLayout soruici = new VerticalLayout();

            for (Soru soru : soruList) {
               // for (int i = 0; i < hepsi.size(); i++) {

                SecenekService secenekService = new SecenekService();

                //Soru soru = soruService.findAllbyID(hepsi.get(i).getSoru_id());

                List<Secenek> secenekList = secenekService.findAllbyAnketID( soru.getId() );

                // Label soruAdi=new Label("SORU:"+soru.getAdi().toString());
                // soruici.addComponents(soruAdi);

                    // Soru soru = soruService.findAllbyID(hepsi.get(i).getSoru_id());
                if (soru.getSecenek_tipi().equals( "Tekli" )) {
                    TextField SoruveCevabi = new TextField( "SORU: " + soru.getAdi() );
                    AnketCevap anketCevap= new AnketCevap(soru,SoruveCevabi,"T",kullanici_adi);

                    String Cevap=Cevapvarmi(hepsi,soru.getId());
                    SoruveCevabi.setValue(Cevap);
                    soruici.addComponents( SoruveCevabi );
                }

                if (soru.getSecenek_tipi().equals( "CoktanRadio" )) {

                    OptionGroup radios = new OptionGroup( "SORU: " + soru.getAdi(), secenekList );
                    radios.setImmediate( true );

                    for (Secenek secenek : secenekList) {
                        //  Secenek sec00enek = (Secenek) radios.getData();

                        if(Secenekvarmi(hepsi,secenek.getId())){
                             radios.setValue( secenek );
                        }
                    }

                    soruici.addComponents( radios );

                }

                if (soru.getSecenek_tipi().equals( "CoktanCheckbox" )) {
                    for (Secenek secenek : secenekList) {
                        CheckBox checkBox = new CheckBox( secenek.getAdi() );
                        checkBox.setImmediate( true );
                        checkBox.setData( secenek );
                        secenek.setSoru( soru );
                        if(Secenekvarmi(hepsi,secenek.getId())){
                            checkBox.setValue( true );
                        }

                        soruici.addComponents( checkBox );
                    }
                }
            }
            panelici.addComponent( soruici );

            VaadinPanel.setContent(panelici);
            mainLayout.addComponents(VaadinPanel);
            //panelici.removeAllComponents();
        }
       // mainLayout.addComponents( panelici );


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

private boolean Secenekvarmi(List<AnketCevap> aranacakListe,Long secenek_id) {
    for (int i = 0; i < aranacakListe.size(); i++) {
        if(secenek_id.equals(aranacakListe.get(i).getSecenek_id())){
            return true;
        }
    }
    return false;
    }
    private String Cevapvarmi(List<AnketCevap> aranacakListe,Long soru_id) {
        for (int i = 0; i < aranacakListe.size(); i++) {
            if(soru_id.equals(aranacakListe.get(i).getSoru_id())){
                return aranacakListe.get(i).getCevap();
            }
        }
        return "";
    }

}
