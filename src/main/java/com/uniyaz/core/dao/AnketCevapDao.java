package com.uniyaz.core.dao;

import com.uniyaz.core.domain.Anket;
import com.uniyaz.core.domain.AnketCevap;
import com.uniyaz.core.domain.Secenek;
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
 *
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


    public void deleteAnketId(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String hql =
                    "delete " +
                            "From       AnketCevap anketAlias  where anketAlias.id ='" + id + "'";
            Query query = session.createQuery(hql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSoruId(Long id, String kullanici_kimlik) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String hql =
                    "delete " +
                            "From       AnketCevap anketAlias  where anketAlias.soru_id ='" + id + "'" + "and anketAlias.kullanici_adi='" + kullanici_kimlik + "'";
            Query query = session.createQuery(hql);
            query.executeUpdate();
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

    public List<Object[]> findAllAnketKullaniciHql() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String hql =
                    "Select     anketAlias.adi,anketAlias.kullanici_adi " +
                            "From       AnketCevap anketAlias where 1=1 group by anketAlias.adi,anketAlias.kullanici_adi";
            Query query = session.createQuery(hql);
            return (List<Object[]>) query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<AnketCevap> findAllbyAnketID(String anketadi, String kullanici_adi) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String hql =
                    "Select     anketAlias " +
                            "From       AnketCevap anketAlias " +
                            "Where anketAlias.adi='" + anketadi + "'"
                            + " and anketAlias.kullanici_adi='" + kullanici_adi + "'";
            Query query = session.createQuery(hql);

            return query.list();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean findbySecenekID(String anketadi, String kullanici_adi, Long secenek_id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String hql =
                    "Select     secenekAlias.id " +
                            "From       Secenek secenekAlias" +
                            " where secenekAlias.id in ( select anketAlias.secenek_id from AnketCevap anketAlias  Where anketAlias.adi='" + anketadi + "'"
                            + " and anketAlias.kullanici_adi='" + kullanici_adi + "'"
                            + " and anketAlias.secenek_id=secenekAlias.id " +
                            " and anketAlias.secenek_id= '" + secenek_id + "')";

            Query query = session.createQuery(hql);
            return query.list().isEmpty();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<AnketCevapDto> findAllHqlAliasToBean() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String hql =
                    "Select     anketAlias.id " +
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

