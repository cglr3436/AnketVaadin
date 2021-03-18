package com.uniyaz.ui.page;

import com.uniyaz.core.domain.EnumSecim;
import com.uniyaz.core.domain.Secenek;
import com.uniyaz.core.domain.Soru;
import com.uniyaz.core.domain.Soru;
import com.uniyaz.core.service.SecenekService;
import com.uniyaz.core.service.SoruService;
import com.uniyaz.ui.component.SySaveButton;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;

/**
 * Created by AKARTAL on 12.3.2021.
 */
public class SecenekPage extends VerticalLayout {

    @PropertyId("id")
    private TextField id;

    @PropertyId("adi")
    private TextField adi;


    @PropertyId("tipi")
    private String tipi;



    private FormLayout mainLayout;
    private Secenek secenek;
    private BeanItem<Secenek> secenekBeanItem;
    private FieldGroup binder;
    private SySaveButton sySaveButton;
    @PropertyId("soru")
    private Soru soru;

    public SecenekPage() {
        this(new Secenek());
    }

    public SecenekPage(Secenek secenek) {
        soru=secenek.getSoru();
        tipi=secenek.getTipi();
        setSizeFull();
        buildMainLayout();
        addComponent(mainLayout);
        setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);

        secenekBeanItem = new BeanItem<Secenek>(secenek);
        binder = new FieldGroup(secenekBeanItem);
        binder.bindMemberFields(this);
    }

    private void buildMainLayout() {

        mainLayout = new FormLayout();
        mainLayout.setSizeUndefined();

        id = new TextField();
        id.setCaption("ID");
        id.setEnabled(false);
        mainLayout.addComponent(id);

        adi = new TextField();
        adi.setCaption("Adı");
        mainLayout.addComponent(adi);

        sySaveButton = new SySaveButton();
        sySaveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    binder.commit();
                   //  Anket anket= (Anket) soruBeanItem.getItemProperty("soru").getValue();
                    Secenek secenek = secenekBeanItem.getBean();
                    SecenekService secenekService = new SecenekService();
                    secenekService.saveSecenek(secenek);
                } catch (FieldGroup.CommitException e) {
                    Notification.show("Alanlar nesne ile uyumlu değil", Notification.Type.ERROR_MESSAGE);
                } catch (Exception e) {
                    Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        mainLayout.addComponent(sySaveButton);
    }
}