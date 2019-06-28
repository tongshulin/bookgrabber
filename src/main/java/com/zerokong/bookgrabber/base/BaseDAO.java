package com.zerokong.bookgrabber.base;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@Repository(value = "baseDAO")
public class BaseDAO<T> {
    private Logger logger = LoggerFactory.getLogger(BaseDAO.class);

    @Resource(name = "sessionFactory")
    public SessionFactory sessionFactory;

    @Resource(name = "jdbcTemplate")
    protected JdbcTemplate jdbcTemplate;

    public Session getSession() {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            if (null == session) {
                logger.debug("重新打开一个Session，调用了SessionFactory.openSession()方法！");
                session = sessionFactory.openSession();
            }
        } catch (Exception e) {
            logger.error("=================  There is an error when get a Hibernate session ！ ===================");
            logger.error(e.getMessage());
            e.printStackTrace();
            return sessionFactory.openSession();
        }
        return session;
    }

    protected void close(Session session) {
        if (null != session) {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                logger.error("=================  There is an error when get a Hibernate session ！  message is =======");
                logger.error(e.getMessage());
            }
        }
    }

    public void save(T t) {
        this.getSession().save(t);
    }


    public void saveOrUpdate(T t) {
        this.getSession().saveOrUpdate(t);
    }

    public void batchSave(List<T> ts){
        Session session = this.getSession();
        for(int i = 0; i< ts.size() ; i++){
            session.save(ts.get(i));
            if ( i % 20 == 0 ) {
                session.flush();
                session.clear();
            }
        }
    }

    /**
     * 根据查询语句返回查询结果
     *
     * @param orderExpression
     * @return
     */
    protected List<T> list(String hql) {
        return this.getSession().createQuery(hql).list();
    }

    protected List<Object[]> listObj(String hql){
        return this.getSession().createQuery(hql).list();
    }

    protected List<T> list(String hql, Integer currentPage, Integer pageSize) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 0;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }
        Query query = this.getSession().createQuery(hql);
        query.setFirstResult(pageSize * (currentPage - 1));
        query.setMaxResults(pageSize);
        return query.list();
    }

    protected List<Object[]> listObj(String hql, Integer currentPage, Integer pageSize) {
        try {
            if (currentPage == null || currentPage < 1) {
                currentPage = 0;
            }
            if (pageSize == null || pageSize <= 0) {
                pageSize = 20;
            }
            Query query = this.getSession().createQuery(hql);
            query.setFirstResult(pageSize * (currentPage - 1));
            query.setMaxResults(pageSize);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return null;
    }

    /**
     *
     * @param sourceHql
     * @param paramValue
     * @return
     * @throws Exception
     */
    protected List<T> list(String sourceHql, Object paramValue) throws Exception {
        if (paramValue == null) {
            return list(sourceHql);
        }
        Query query = this.getSession().createQuery(sourceHql);
        query.setParameter(0, paramValue);
        return query.list();
    }

    protected List<Object[]> listObj(String sourceHql, Object paramValue) throws Exception{
        if (paramValue == null) {
            return listObj(sourceHql);
        }
        Query query = this.getSession().createQuery(sourceHql);
        query.setParameter(0, paramValue);
        return query.list();
    }

    protected List<T> list(String sourceHql, Object paramValue, Integer currentPage, Integer pageSize)
            throws Exception {
        if (currentPage == null || currentPage < 1) {
            currentPage = 0;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }

        if (paramValue == null) {
            return list(sourceHql, currentPage, pageSize);
        }
        Query query = this.getSession().createQuery(sourceHql);
        query.setParameter(0, paramValue);
        query.setFirstResult(pageSize * (currentPage - 1) + 1);
        query.setMaxResults(pageSize);
        return query.list();
    }

    protected List<Object[]> listObj(String sourceHql, Object paramValue, Integer currentPage, Integer pageSize)
            throws Exception {
        if (currentPage == null || currentPage < 1) {
            currentPage = 0;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }

        if (paramValue == null) {
            return listObj(sourceHql, currentPage, pageSize);
        }
        Query query = this.getSession().createQuery(sourceHql);
        query.setParameter(0, paramValue);
        query.setFirstResult(pageSize * (currentPage - 1));
        query.setMaxResults(pageSize);
        return query.list();
    }

    /**
     *
     * @param sourceHql
     * @param paramsValue
     * @return
     * @throws Exception
     */
    protected List<T> list(String sourceHql, Object[] paramsValue) throws Exception {
        if (paramsValue == null || paramsValue.length < 0) {
            return list(sourceHql);
        }
        int length = paramsValue.length;
        int i = 0;
        Query query = this.getSession().createQuery(sourceHql);
        for (i = 0; i < length; i++) {
            query.setParameter(i, paramsValue[i]);
        }
        return query.list();
    }

    protected List<T> list(String sourceHql, Object[] paramsValue, Integer currentPage, Integer pageSize)
            throws Exception {
        if (currentPage == null || currentPage < 1) {
            currentPage = 0;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }
        if (paramsValue == null || paramsValue.length < 0) {
            return list(sourceHql,currentPage,pageSize);
        }
        int length = paramsValue.length;
        int i = 0;
        Query query = this.getSession().createQuery(sourceHql);
        for (i = 0; i < length; i++) {
            query.setParameter(i, paramsValue[i]);
        }
        query.setFirstResult(pageSize * (currentPage - 1) + 1);
        query.setMaxResults(pageSize);
        return query.list();
    }

    /**
     * 根据查询的列和参数值查询数据清单，坏处是无法实现模糊查询。
     *
     * @param sourceHql
     * @param paramValue
     * @return
     * @throws Exception
     */
    protected List<T> list(String sourceHql, String[] paramsName, Object[] paramsValue) throws Exception {
        if (paramsName == null || paramsValue == null || paramsName.length <= 1 || paramsValue.length < 0) {
            return list(sourceHql);
        }
        if (paramsName.length != paramsValue.length) {
            logger.info("参数名和参数值数量不一致！");
            return null;
        }
        StringBuffer hql = new StringBuffer(sourceHql).append(" where 1 = 1 ");
        int length = paramsName.length;
        int i = 0;
        for (; i < length; i++) {
            hql.append(paramsName[i]).append(" = ?");
        }
        logger.debug("查询语句：" + hql.toString());
        Query query = this.getSession().createQuery(hql.toString());
        for (i = 0; i < length; i++) {
            query.setParameter(i, paramsValue[i]);
        }
        return query.list();
    }

    protected List<T> list(String sourceHql, String[] paramsName, Object[] paramsValue,Integer currentPage,Integer pageSize) throws Exception {
        if (currentPage == null || currentPage < 1) {
            currentPage = 0;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }

        if (paramsName == null || paramsValue == null || paramsName.length <= 1 || paramsValue.length < 0) {
            return list(sourceHql,currentPage,pageSize);
        }
        if (paramsName.length != paramsValue.length) {
            logger.info("参数名和参数值数量不一致！");
            return null;
        }
        StringBuffer hql = new StringBuffer(sourceHql).append(" where 1 = 1 ");
        int length = paramsName.length;
        int i = 0;
        for (; i < length; i++) {
            hql.append(paramsName[i]).append(" = ?");
        }
        logger.debug("查询语句：" + hql.toString());
        Query query = this.getSession().createQuery(hql.toString());
        for (i = 0; i < length; i++) {
            query.setParameter(i, paramsValue[i]);
        }
        query.setFirstResult(pageSize * (currentPage - 1) + 1);
        query.setMaxResults(pageSize);
        return query.list();
    }

    /**
     * 联合查询HQL
     *
     * @param hql
     * @return
     */
    protected List<Object[]> execHQL(String hql) {
        return this.getSession().createQuery(hql).list();
    }

    protected List<Object[]> execSQL(String sql) {
        return this.getSession().createSQLQuery(sql).list();
    }

    protected int execHqlUpdate(String hql) {
        return this.getSession().createQuery(hql).executeUpdate();
    }

    protected int execHqlUpdate(String hql,Object o) {
        return this.getSession().createQuery(hql).setParameter(0, o).executeUpdate();
    }

    protected int execSqlUpdate(String hql) {
        return this.getSession().createSQLQuery(hql).executeUpdate();
    }

    protected int execSqlUpdate(String hql,Object o) {
        return this.getSession().createSQLQuery(hql).setParameter(0, o).executeUpdate();
    }


    protected void saveOrUpdate(List<T> list) throws Exception {
        if (null != list && list.size() > 0) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                T t = it.next();
                this.getSession().saveOrUpdate(t);
            }
        }
    }

    public void delete(T t) throws Exception {
        this.getSession().delete(t);
    }

    public void update(T t) {
        this.getSession().update(t);
    }

    public void merge(T t){
        this.getSession().merge(t);
    }

    @SuppressWarnings("unchecked")
    public List<T> getResultList(String hql) {
        return this.getSession().createQuery(hql).list();
    }

    @SuppressWarnings("unchecked")
    public T get(Class<?> clazz, Integer id) {
        T entity = null;
        try {
            entity = (T) this.getSession().get(clazz, id);
        } catch (Exception e) {
            entity = null;
            e.printStackTrace();
            logger.error("=================  occur an error when get a Hibernate Object ！  message is =======");
            logger.error(e.getMessage());
        }
        return entity;
    }


    public JdbcTemplate getJdpcTemplate() {
        return jdpcTemplate;
    }

    public void setJdpcTemplate(JdbcTemplate jdpcTemplate) {
        this.jdpcTemplate = jdpcTemplate;
    }
}

