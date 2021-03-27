package com.uniyaz.core.service;

import com.uniyaz.core.dao.AnketCevapDao;
import com.uniyaz.core.domain.AnketCevap;

import java.util.List;

public class AnketCevapService {


    AnketCevapDao anketCevapDao = new AnketCevapDao();

    public void saveAnketCevap(AnketCevap anket) {
        validateSaveAnketCevap(anket);
        anketCevapDao.saveAnketCevap(anket);
    }

    public void deleteAnketCevap(AnketCevap anket) {
        anketCevapDao.deleteAnketCevap(anket);
    }

    public void deleteAnketId(Long id) {
        anketCevapDao.deleteAnketId(id);
    }

    public void deleteSoruId(Long id, String kullanici_kimlik) {
        anketCevapDao.deleteSoruId(id, kullanici_kimlik);
    }

    private void validateSaveAnketCevap(AnketCevap anket) {

        //    if (!anket.getKodu().startsWith("U")) throw new RuntimeException("Ürün Kodu U ile başlamak zorunda");
    }

    public List<AnketCevap> findAllHql() {
        return anketCevapDao.findAllHql();
    }

    public List<Object[]> findAllAnketKullaniciHql() {
        return anketCevapDao.findAllAnketKullaniciHql();
    }

    public List<AnketCevap> findAllbyAnketID(String anketadi, String kullanici_adi) {
        return anketCevapDao.findAllbyAnketID(anketadi, kullanici_adi);

    }


    public Boolean findbySecenekID(String anketadi, String kullanici_adi, Long secenek_id) {
        return anketCevapDao.findbySecenekID(anketadi, kullanici_adi, secenek_id);

    }
}


