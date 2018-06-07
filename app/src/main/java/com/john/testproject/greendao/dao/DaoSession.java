package com.john.testproject.greendao.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.john.testproject.greendao.database.Customer;
import com.john.testproject.greendao.database.User;

import com.john.testproject.greendao.dao.CustomerDao;
import com.john.testproject.greendao.dao.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig customerDaoConfig;
    private final DaoConfig userDaoConfig;

    private final CustomerDao customerDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        customerDaoConfig = daoConfigMap.get(CustomerDao.class).clone();
        customerDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        customerDao = new CustomerDao(customerDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(Customer.class, customerDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        customerDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public CustomerDao getCustomerDao() {
        return customerDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
