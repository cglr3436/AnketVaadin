package com.uniyaz.core.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SORU")
public class Soru extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "ADI", nullable = false, length = 100)
    @NotNull
    private String adi;

    @Column(name = "SECENEK_TIPI", nullable = false, length = 100)
    @NotNull
    private String secenek_tipi;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PANEL", foreignKey = @ForeignKey(name = "FK_SORU_PANEL"))
    private Panel panel;

    public Soru(Panel panel) {
        this.panel = panel;
    }

    public Soru() {
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public String getSecenek_tipi() {
        return secenek_tipi;
    }

    public void setSecenek_tipi(String secenek_tipi) {
        this.secenek_tipi = secenek_tipi;
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

    public void setSoru(Soru soru) {
    }
}

