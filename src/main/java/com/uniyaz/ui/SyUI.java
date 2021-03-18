package com.uniyaz.ui;

import com.uniyaz.core.domain.*;
import com.uniyaz.ui.component.ContentComponent;
import com.uniyaz.ui.component.SyMenuTab;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("com.uniyaz.MyAppWidgetset")
public class SyUI extends UI {

    private VerticalLayout mainLayout;
    private ContentComponent contentComponent;

    private Anket anket;
    private Panel panel;
    private Soru soru;


    private Secenek secenek;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        buildMainLayout();
        setContent(mainLayout);
    }

    private void buildMainLayout() {

        mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();

        contentComponent = new ContentComponent();


        SyMenuTab syMenuTab = new SyMenuTab();


        mainLayout.addComponent(syMenuTab);
        mainLayout.addComponent(contentComponent);

        mainLayout.setExpandRatio(syMenuTab, 0.4f);
        mainLayout.setExpandRatio(contentComponent, 8.6f);
    }

    public ContentComponent getContentComponent() {
        return contentComponent;
    }

    public void setContentComponent(ContentComponent contentComponent) {
        this.contentComponent = contentComponent;
    }

    public void setAnket(Anket anket) {
        this.anket=anket;
    }

    public void setPanel(Panel panel) {
        this.panel=panel;
    }
    public Anket getAnket() {
        return anket;
    }

    public Panel getPanel() {
        return panel;
    }

    public Soru getSoru() {
        return soru;
    }

    public void setSoru(Soru soru) {
        this.soru = soru;
    }

    public Secenek getSecenek() {
        return secenek;
    }

    public void setSecenek(Secenek secenek) {
        this.secenek = secenek;
    }



}