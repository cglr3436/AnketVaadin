package com.uniyaz.core.dao;

import com.uniyaz.core.domain.Anket;
import com.uniyaz.core.domain.AnketCevap;
import com.uniyaz.core.dto.AnketCevapDto;
import com.uniyaz.core.dto.AnketDto;
import com.uniyaz.core.utils.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import java.util.List;

/**
 * Created by AKARTAL on 12.3.2021.
 */
public class AnketCevapDao {

    public void saveAnketCevap(AnketCevap anket) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(anket);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAnketCevap(AnketCevap anket) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(anket);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<AnketCevap> findAByIdCriteria(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(AnketCevap.class);
            criteria.add(Restrictions.eq("id", id));
            //criteria.add(Restrictions.like("kodu", "U", MatchMode.START));
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<AnketCevap> findAllHql() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String hql =
                    "Select     anketAlias " +
                            "From       AnketCevap anketAlias ";
            Query query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<AnketCevapDto> findAllHqlAliasToBean() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String hql =
                    "Select     anketAlias.id, anketAlias.kodu " +
                            "From       AnketCevap anketAlias ";
            Query query = session.createQuery(hql);
            query.setResultTransformer(Transformers.aliasToBean(AnketCevapDto.class));
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public List<AnketDtoNative> findAllNative() {
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        try (Session session = sessionFactory.openSession()) {
//            NativeQuery sqlQuery = session.createSQLQuery("SELECT * FROM URUN");
//            Query query = sqlQuery.setResultTransformer(Transformers.aliasToBean(AnketDtoNative.class));
//            return query.list();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}

