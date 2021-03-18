package com.uniyaz.core.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SECENEK")
public class Secenek extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "ADI", nullable = false, length = 100)
    @NotNull
    private String adi;

    @Column(name = "TIPI", nullable = false, length = 100)
    @NotNull
    private String tipi;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SORU", foreignKey = @ForeignKey(name = "FK_SECENEK_SORU"))
    private Soru Soru;

    public Secenek(Soru soru) { this.Soru=soru; this.tipi=soru.getSecenek_tipi().toString(); }

    public Secenek() {
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
        return adi ;
    }
}

