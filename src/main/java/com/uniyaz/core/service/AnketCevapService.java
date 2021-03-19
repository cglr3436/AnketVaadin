package com.uniyaz.core.service;

import com.uniyaz.core.dao.AnketCevapDao;
import com.uniyaz.core.dao.AnketDao;
import com.uniyaz.core.domain.Anket;
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

        private void validateSaveAnketCevap(AnketCevap anket) {

        //    if (!anket.getKodu().startsWith("U")) throw new RuntimeException("Ürün Kodu U ile başlamak zorunda");
        }

        public List<AnketCevap> findAllHql() {
            return anketCevapDao.findAllHql();
        }
    }


