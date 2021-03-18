package com.uniyaz.core.service;



import com.uniyaz.core.dao.AnketDao;
import com.uniyaz.core.dao.SoruDao;
import com.uniyaz.core.domain.Anket;
import com.uniyaz.core.domain.Soru;

import java.util.List;

public class SoruService {


    SoruDao soruDao = new SoruDao();

    public void saveSoru(Soru soru) {
        validateSaveSoru(soru);
        soruDao.saveSoru(soru);
    }

    public void deleteSoru(Soru soru) {
        soruDao.deleteSoru(soru);
    }

    private void validateSaveSoru(Soru soru) {

        //    if (!soru.getKodu().startsWith("U")) throw new RuntimeException("Ürün Kodu U ile başlamak zorunda");
    }

    public List<Soru> findAllHql() {
        return soruDao.findAllHql();
    }

    public List<Soru> findAllbyAnketID(Long id) {
        return soruDao.findAllbyAnketID(id);
    }
}



