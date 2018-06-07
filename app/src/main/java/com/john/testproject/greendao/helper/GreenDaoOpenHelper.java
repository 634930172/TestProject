package com.john.testproject.greendao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.john.testproject.greendao.dao.DaoMaster;
import com.john.testproject.greendao.dao.UserDao;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/2/2 10:20
 * Description:
 */

public class GreenDaoOpenHelper extends DaoMaster.OpenHelper  {
    public GreenDaoOpenHelper(Context context, String name) {
        super(context, name);
    }

    public GreenDaoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据迁移模块
//        MigrationHelper.migrate(db,
//                UserDao.class,
//                ProfessionDao.class,
//                LTestDao.class);
    }
}

