package com.uniyaz.ui.page;

import com.uniyaz.core.domain.Anket;
import com.uniyaz.core.domain.Panel;
import com.uniyaz.core.service.PanelService;
import com.uniyaz.ui.component.CinsiyetCombobox;
import com.uniyaz.ui.component.SySaveButton;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;

/**
 * Created by AKARTAL on 12.3.2021.
 */
public class PanelPage extends VerticalLayout {

    @PropertyId("id")
    private TextField id;

    @PropertyId("adi")
    private TextField adi;

private Panel panel;
    private FormLayout mainLayout;

    private BeanItem<Panel> panelBeanItem;
    private FieldGroup binder;
    private SySaveButton sySaveButton;
    @PropertyId("anket")
    private Anket anket;

    public PanelPage() {
        this(new Panel());
    }

    public PanelPage(Panel panel) {
        anket=panel.getAnket();
        setSizeFull();
        buildMainLayout();
        addComponent(mainLayout);
        setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);

        panelBeanItem = new BeanItem<Panel>(panel);
        binder = new FieldGroup(panelBeanItem);
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
                     Anket anket= (Anket) panelBeanItem.getItemProperty("anket").getValue();
                    Panel panel = panelBeanItem.getBean();
                    PanelService panelService = new PanelService();
                    panelService.savePanel(panel);
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