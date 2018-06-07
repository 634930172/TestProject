package com.john.testproject.greendao.helper;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/2/2 9:27
 * Description:
 */

public class GreenDaoContextHelper extends ContextWrapper {

    public GreenDaoContextHelper(Context base) {
        super(base);

    }


    @Override
    public File getDatabasePath(String name) {
        return super.getDatabasePath(name);
    }
}