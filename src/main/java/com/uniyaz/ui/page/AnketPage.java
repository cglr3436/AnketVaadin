package com.uniyaz.ui.page;

import com.uniyaz.core.domain.Anket;
import com.uniyaz.core.service.AnketService;
import com.uniyaz.ui.component.CinsiyetCombobox;
import com.uniyaz.ui.component.SySaveButton;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;

/**
 *
 */
public class AnketPage extends VerticalLayout {

    @PropertyId("id")
    private TextField id;

    @PropertyId("adi")
    private TextField adi;


    private FormLayout mainLayout;

    private BeanItem<Anket> anketBeanItem;
    private FieldGroup binder;
    private SySaveButton sySaveButton;

    public AnketPage() {
        this(new Anket());
    }

    public AnketPage(Anket anket) {

        setSizeFull();
        buildMainLayout();
        addComponent(mainLayout);
        setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);

        anketBeanItem = new BeanItem<Anket>(anket);
        binder = new FieldGroup(anketBeanItem);
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

                    Anket anket = anketBeanItem.getBean();
                    AnketService anketService = new AnketService();
                    anketService.saveAnket(anket);
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