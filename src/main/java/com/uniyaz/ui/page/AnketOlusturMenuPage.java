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
import com.vaadin.ui.*;

import java.util.List;

/**
 * Created by AKARTAL on 12.3.2021.
 */
public class AnketOlusturMenuPage extends VerticalLayout {


  //  @PropertyId("adi")
    private TextField kullanici_adi;

   // @PropertyId("secenek_tipi")
    private ComboBox anket_tipi;

   // private Soru soru;
    private FormLayout mainLayout;

    //private BeanItem<Soru> soruBeanItem;
   // private FieldGroup binder;
    private SySaveButton sySaveButton;
   // @PropertyId("panel")
   // private Panel panel;
    private Object TEMP;

    public AnketOlusturMenuPage() {
        //removeAllComponents();
        buildMainLayout();
        addComponent(mainLayout);
        setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);
    }
public void set(AnketOlusturMenuPage a){
    addComponent(a);
}
    private void buildMainLayout() {

        mainLayout = new FormLayout();
        mainLayout.setSizeUndefined();
        mainLayout.removeAllComponents();

        kullanici_adi = new TextField();
        kullanici_adi.setCaption("KULLANICI ADI");
        //kullanici_adi.setEnabled(false);
        mainLayout.addComponent(kullanici_adi);



        AnketService anketService = new AnketService();
        List<Anket> anketList = anketService.findAllHql();
        anket_tipi=new ComboBox("Anket",anketList);

        mainLayout.addComponent(anket_tipi);


        sySaveButton = new SySaveButton();
        sySaveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    Anket anket= (Anket) anket_tipi.getValue();
                    String kullanici=""+ kullanici_adi.getValue();
                    SyUI syUI = (SyUI) UI.getCurrent();
                     syUI.setAnket(anket);
                    mainLayout.removeAllComponents();
                    AnketOlusturPage  anketOlusturPage = new AnketOlusturPage(kullanici);
                    mainLayout.addComponent(anketOlusturPage);

                    Button geri=new Button("GERI");
                    geri.addClickListener(new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent clickEvent) {
                            removeAllComponents();
                            AnketOlusturMenuPage anketOlusturMenuPage = new AnketOlusturMenuPage();
                            addComponent(anketOlusturMenuPage);

                        }
                    });
                    mainLayout.addComponent(geri);
                   //  Anket anket= (Anket) soruBeanItem.getItemProperty("panel").getValue();
                  //  Soru soru = soruBeanItem.getBean();
                  //  SoruService soruService = new SoruService();
                  //  soruService.saveSoru(soru);


                } catch (Exception e) {
                    Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        mainLayout.addComponent(sySaveButton);

    }
}