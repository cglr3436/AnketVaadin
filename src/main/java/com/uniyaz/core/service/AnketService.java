package com.uniyaz.core.service;

import com.uniyaz.core.dao.AnketDao;
import com.uniyaz.core.domain.Anket;

import java.util.List;

public class AnketService {


        AnketDao anketDao = new AnketDao();

        public void saveAnket(Anket anket) {
            validateSaveAnket(anket);
            anketDao.saveAnket(anket);
        }

        public void deleteAnket(Anket anket) {
            anketDao.deleteAnket(anket);
        }

        private void validateSaveAnket(Anket anket) {

        //    if (!anket.getKodu().startsWith("U")) throw new RuntimeException("Ürün Kodu U ile başlamak zorunda");
        }

        public List<Anket> findAllHql() {
            return anketDao.findAllHql();
        }
    }


