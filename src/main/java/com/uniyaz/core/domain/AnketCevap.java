package com.uniyaz.core.domain;

import com.uniyaz.core.service.AnketCevapService;
import com.uniyaz.core.service.SecenekService;
import com.uniyaz.core.service.SoruService;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.*;

@Entity
@Table(name = "ANKETCEVAP")
public class AnketCevap extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "ADI", nullable = false, length = 100)
    @NotNull
    private String adi;

    @Column(name = "KULLANICI_ADI", nullable = false, length = 100)
    @NotNull
    private String kullanici_adi;

    @Column(name = "TIPI", nullable = false, length = 100)
    @NotNull
    private String tipi;

    @Column(name = "SORU_ID")
    private Long soru_id;

    @Column(name = "SECENEK_ID")
    private Long secenek_id;

    @Column(name = "CEVAP", nullable = false, length = 100)
    @NotNull
    private String cevap;

    @Transient
    private Soru Soru;

    @Transient
    private Secenek secenek;

    @Transient
    private Panel panel;

    @Transient
    private Anket anket;

    @Transient
    private com.vaadin.ui.Component component;

    @Transient
    private String componet_tipi;


    public AnketCevap(Secenek secenek, com.vaadin.ui.Component component, String componet_tipi, String kullanici_adi) {
        this.secenek = secenek;
        this.secenek_id = secenek.getId();
        this.Soru = secenek.getSoru();
        this.soru_id = secenek.getSoru().getId();
        this.component = component;
        this.componet_tipi = componet_tipi;
        this.kullanici_adi = kullanici_adi;
    }


    public AnketCevap(Soru soru, com.vaadin.ui.Component component, String componet_tipi, String kullanici_adi) {

        this.Soru = soru;
        this.soru_id = soru.getId();
        this.kullanici_adi = kullanici_adi;
        this.component = component;
        this.componet_tipi = componet_tipi;
    }

    public AnketCevap() {
    }

    public Long getSecenek_id() {
        return secenek_id;
    }

    public void setSecenek_id(Long secenek_id) {
        this.secenek_id = secenek_id;
    }

    public String getCevap() {
        return cevap;
    }

    public void setCevap(String cevap) {
        this.cevap = cevap;
    }

    public Secenek getSecenek() {
        return secenek;
    }

    public void setSecenek(Secenek secenek) {
        this.secenek = secenek;
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public Anket getAnket() {
        return anket;
    }

    public void setAnket(Anket anket) {
        this.anket = anket;
    }

    public Long getSoru_id() {
        return soru_id;
    }

    public void setSoru_id(Long soru_id) {
        this.soru_id = soru_id;
    }

    public com.vaadin.ui.Component getComponent() {
        return component;
    }

    public void setComponent(com.vaadin.ui.Component component) {
        this.component = component;
    }

    public String getComponet_tipi() {
        return componet_tipi;
    }

    public void setComponet_tipi(String componet_tipi) {
        this.componet_tipi = componet_tipi;
    }

    public String getTipi() {
        return tipi;
    }

    public void setTipi(String tipi) {
        this.tipi = tipi;
    }

    public Soru getSoru() {
        return Soru;
    }

    public void setSoru(Soru Soru) {
        this.Soru = Soru;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    @Override
    public String toString() {
        return adi;
    }

    public String getKullanici_adi() {
        return kullanici_adi;
    }

    public void setKullanici_adi(String kullanici_adi) {
        this.kullanici_adi = kullanici_adi;
    }
}

