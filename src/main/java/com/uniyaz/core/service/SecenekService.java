package com.uniyaz.core.service;



import com.uniyaz.core.dao.SecenekDao;
import com.uniyaz.core.domain.Secenek;

import java.util.List;

public class SecenekService {


    SecenekDao secenekDao = new SecenekDao();

    public void saveSecenek(Secenek secenek) {
        validateSaveSecenek(secenek);
        secenekDao.saveSecenek(secenek);
    }

    public void deleteSecenek(Secenek secenek) {
        secenekDao.deleteSecenek(secenek);
    }

    private void validateSaveSecenek(Secenek secenek) {

        //    if (!secenek.getKodu().startsWith("U")) throw new RuntimeException("Ürün Kodu U ile başlamak zorunda");
    }

    public List<Secenek> findAllHql() {
        return secenekDao.findAllHql();
    }

    public List<Secenek> findAllbyAnketID(Long id) {
        return secenekDao.findAllbyAnketID(id);
    }
}



