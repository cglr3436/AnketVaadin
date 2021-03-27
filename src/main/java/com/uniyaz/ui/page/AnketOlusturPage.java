package com.uniyaz.ui.page;

import com.uniyaz.core.domain.*;

import com.uniyaz.core.domain.Panel;
import com.uniyaz.core.service.*;
import com.uniyaz.ui.SyUI;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import java.util.*;

public class AnketOlusturPage extends VerticalLayout {
    private VerticalLayout mainLayout;

    List<AnketCevap> hepsi;
    List<AnketCevap> hepsi2;

    public AnketOlusturPage(String kullanici_adi) {
        hepsi = new ArrayList<AnketCevap>();
        hepsi2 = new ArrayList<AnketCevap>();
        setSizeFull();
        SyUI syUI = (SyUI) UI.getCurrent();
        Anket anket = syUI.getAnket();
        buildMainLayout(kullanici_adi);
        addComponent(mainLayout);

        setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);

    }

    private void buildMainLayout(String kullanici_adi) {


        mainLayout = new VerticalLayout();
        mainLayout.setSizeUndefined();


        SyUI syUI = (SyUI) UI.getCurrent();
        Anket anket = syUI.getAnket();

        if (anket == null) return;
        AnketCevapService anketCevapService = new AnketCevapService();
        hepsi = anketCevapService.findAllbyAnketID(anket.getAdi(), kullanici_adi);

        PanelService panelService = new PanelService();

        List<Panel> panelList = panelService.findAllbyAnketID(anket.getId());


        Label anketBaslik = new Label(anket.getAdi().toString() + " ANKETI");
        mainLayout.addComponents(anketBaslik);

        for (Panel panel : panelList) {
            VerticalLayout panelici = new VerticalLayout();
            com.vaadin.ui.Panel VaadinPanel = new com.vaadin.ui.Panel();
            VaadinPanel.setCaption(panel.getAdi().toString());


            SoruService soruService = new SoruService();
            List<Soru> soruList = soruService.findAllbyAnketID(panel.getId());
            VerticalLayout soruici = new VerticalLayout();

            for (Soru soru : soruList) {

                SecenekService secenekService = new SecenekService();

                List<Secenek> secenekList = secenekService.findAllbyAnketID(soru.getId());


                if (soru.getSecenek_tipi().equals("Tekli")) {
                    TextField SoruveCevabi = new TextField("SORU: " + soru.getAdi());
                    String Cevap = Cevapvarmi(hepsi, soru.getId());
                    SoruveCevabi.setValue(Cevap);

                    AnketCevap anketCevap = new AnketCevap(soru, SoruveCevabi, "T", kullanici_adi);

                    Long a = AnketCevapIDbul(hepsi, soru.getId());
                    if (null != a) {
                        anketCevap.setId(a);
                    }

                    hepsi2.add(anketCevap);

                    soruici.addComponents(SoruveCevabi);
                }

                if (soru.getSecenek_tipi().equals("CoktanRadio")) {

                    OptionGroup radios = new OptionGroup("SORU: " + soru.getAdi(), secenekList);
                    radios.setImmediate(true);

                    for (Secenek secenek : secenekList) {

                        if (Secenekvarmi(hepsi, secenek.getId())) {
                            radios.setValue(secenek);

                        }
                    }

                    AnketCevap anketCevap = new AnketCevap(soru, radios, "R", kullanici_adi);
                    Long a = AnketCevapIDbul(hepsi, soru.getId());
                    if (null != a) {
                        anketCevap.setId(a);
                    }
                    hepsi2.add(anketCevap);

                    soruici.addComponents(radios);

                }

                if (soru.getSecenek_tipi().equals("CoktanCheckbox")) {

                    Label soruAdi = new Label("SORU:" + soru.getAdi().toString());
                    soruici.addComponents(soruAdi);

                    for (Secenek secenek : secenekList) {
                        CheckBox checkBox = new CheckBox(secenek.getAdi());
                        checkBox.setImmediate(true);
                        checkBox.setData(secenek);
                        secenek.setSoru(soru);
                        if (Secenekvarmi(hepsi, secenek.getId())) {
                            checkBox.setValue(true);
                        }

                        AnketCevap anketCevap = new AnketCevap(secenek, checkBox, "C", kullanici_adi);
                        hepsi2.add(anketCevap);

                        soruici.addComponents(checkBox);
                    }
                }
            }
            panelici.addComponent(soruici);
            VaadinPanel.setContent(panelici);
            mainLayout.addComponents(VaadinPanel);

        }

        Button ekleButton = buildEkleButton(anket);
        mainLayout.addComponents(ekleButton);


    }


    private Button buildEkleButton(Anket anket) {
        Button ekleButton = new Button("ANKET KAYDET");
        ekleButton.setIcon(FontAwesome.PLUS);
        ekleButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Long soru_id_check = 0l;
                String kullanici_kimlik = "";

                for (AnketCevap anketCevap : hepsi2) {
                    kullanici_kimlik = anketCevap.getKullanici_adi();

                    Soru soru = anketCevap.getSoru();

                    if (soru.getSecenek_tipi().equals("Tekli")) {
                        TextField textField = (TextField) anketCevap.getComponent();
                        String cevap = textField.getValue();
                        anketCevap.setAdi(anket.getAdi());
                        anketCevap.setTipi(soru.getSecenek_tipi());
                        anketCevap.setCevap(cevap);
                        Long a = AnketCevapIDbul(hepsi, soru.getId());
                        if (null != a) {
                            anketCevap.setId(a);
                        }
                        AnketCevapService anketCevapService = new AnketCevapService();
                        anketCevapService.saveAnketCevap(anketCevap);
                    }

                    if (soru.getSecenek_tipi().equals("CoktanRadio")) {

                        OptionGroup radios = (OptionGroup) anketCevap.getComponent();
                        Secenek secenek = (Secenek) radios.getValue();
                        Long a = AnketCevapIDbul(hepsi, soru.getId());
                        if (null != a) {
                            anketCevap.setId(a);
                        }

                        anketCevap.setAdi(anket.getAdi());
                        anketCevap.setTipi(soru.getSecenek_tipi());
                        anketCevap.setSecenek_id(secenek.getId());
                        anketCevap.setCevap(secenek.getAdi());

                        AnketCevapService anketCevapService = new AnketCevapService();
                        anketCevapService.saveAnketCevap(anketCevap);

                    }
                    if (soru.getSecenek_tipi().equals("CoktanCheckbox")) {

                        Long a = AnketCevapIDbul(hepsi, soru.getId());
                        if (null != a && soru_id_check != soru.getId()) {
                            soru_id_check = soru.getId();
                            AnketCevapService anketCevapService = new AnketCevapService();
                            anketCevapService.deleteSoruId(soru_id_check, kullanici_kimlik);
                        }

                        CheckBox radios = (CheckBox) anketCevap.getComponent();
                        Secenek secenek = (Secenek) radios.getData();
                        if (radios.getValue()) {

                            anketCevap.setAdi(anket.getAdi());
                            anketCevap.setTipi(secenek.getTipi());
                            anketCevap.setSecenek_id(secenek.getId());
                            anketCevap.setCevap(secenek.getAdi());

                            AnketCevapService anketCevapService = new AnketCevapService();
                            anketCevapService.saveAnketCevap(anketCevap);
                        }
                    }
                }

                removeAllComponents();
                AnketOlusturPage anketOlusturPage = new AnketOlusturPage(kullanici_kimlik);
                addComponent(anketOlusturPage);
            }

        });
        return ekleButton;
    }

    private boolean Secenekvarmi(List<AnketCevap> aranacakListe, Long secenek_id) {
        for (int i = 0; i < aranacakListe.size(); i++) {
            if (secenek_id.equals(aranacakListe.get(i).getSecenek_id())) {
                return true;
            }
        }
        return false;
    }

    private String Cevapvarmi(List<AnketCevap> aranacakListe, Long soru_id) {
        for (int i = 0; i < aranacakListe.size(); i++) {
            if (soru_id.equals(aranacakListe.get(i).getSoru_id())) {
                return aranacakListe.get(i).getCevap();
            }
        }
        return "";
    }

    private Long AnketCevapIDbulSecenek(List<AnketCevap> aranacakListe, Long secenek_id) {
        for (int i = 0; i < aranacakListe.size(); i++) {
            if (secenek_id.equals(aranacakListe.get(i).getSecenek_id())) {
                return aranacakListe.get(i).getId();
            }
        }
        return null;
    }

    private Long AnketCevapIDbul(List<AnketCevap> aranacakListe, Long soru_id) {
        for (int i = 0; i < aranacakListe.size(); i++) {
            if (soru_id.equals(aranacakListe.get(i).getSoru_id())) {
                return aranacakListe.get(i).getId();
            }
        }
        return null;
    }
}
