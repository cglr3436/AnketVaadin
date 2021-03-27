package com.uniyaz.ui.page;

import com.uniyaz.core.domain.Anket;
import com.uniyaz.core.domain.EnumSecim;
import com.uniyaz.core.domain.Panel;
import com.uniyaz.core.domain.Soru;
import com.uniyaz.core.service.AnketService;
import com.uniyaz.core.service.SoruService;
import com.uniyaz.ui.SyUI;
import com.uniyaz.ui.component.SySaveButton;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.*;

import java.util.List;

/**
 *
 */
public class AnketOlusturMenuPage extends VerticalLayout {

    private FormLayout mainLayout;

    private TextField kullanici_adi;

    private ComboBox anket_tipi;

    private SySaveButton sySaveButton;

    public AnketOlusturMenuPage() {

        buildMainLayout();
        addComponent(mainLayout);
        setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);
    }

    private void buildMainLayout() {

        mainLayout = new FormLayout();
        mainLayout.setSizeUndefined();
        mainLayout.removeAllComponents();

        kullanici_adi = new TextField();
        kullanici_adi.setCaption("KULLANICI ADI");
        mainLayout.addComponent(kullanici_adi);


        AnketService anketService = new AnketService();
        List<Anket> anketList = anketService.findAllHql();
        anket_tipi = new ComboBox("Anket", anketList);

        mainLayout.addComponent(anket_tipi);

        sySaveButton = new SySaveButton();
        sySaveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    Anket anket = (Anket) anket_tipi.getValue();
                    String kullanici = "" + kullanici_adi.getValue();
                    buildGuncelleButton(anket.getAdi(), kullanici);

                } catch (Exception e) {
                    Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        mainLayout.addComponent(sySaveButton);

    }

    private void buildGuncelleButton(String anketIsmi, String kullanici) {

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


    }

}