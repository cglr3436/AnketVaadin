package com.uniyaz.core.service;

import com.uniyaz.core.dao.AnketDao;
import com.uniyaz.core.dao.PanelDao;
import com.uniyaz.core.domain.Anket;
import com.uniyaz.core.domain.Panel;

import java.util.List;

public class PanelService {


        PanelDao panelDao = new PanelDao();

        public void savePanel(Panel panel) {
            validateSavePanel(panel);
            panelDao.savePanel(panel);
        }

        public void deletePanel(Panel panel) {
            panelDao.deletePanel(panel);
        }

        private void validateSavePanel(Panel panel) {

        //    if (!panel.getKodu().startsWith("U")) throw new RuntimeException("Ürün Kodu U ile başlamak zorunda");
        }

        public List<Panel> findAllHql() {
            return panelDao.findAllHql();
        }

    public List<Panel> findAllbyAnketID(Long id) {
        return panelDao.findAllbyAnketID(id);
    }
}


