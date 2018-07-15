package com.john.testproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.john.testproject.R;
import com.john.testproject.greendao.dao.CustomerDao;
import com.john.testproject.greendao.entity.Customer;
import com.john.testproject.greendao.entity.Student;
import com.john.testproject.greendao.helper.GreenDaoHelper;
import com.john.testproject.greendao.helper.GreenDaoOpenHelper;
import com.john.testproject.utils.L;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/2/1 14:37
 * Description：数据库基本操作
 */

public class GreenDaoAct extends BaseAct {

    @BindView(R.id.listView)
    ListView listView;
    private Customer customer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.greendao_layout);
        ButterKnife.bind(this);
    }

    //基本操作
    public void test1(View view) {
        //        Customer customer=new Customer((long) 1,"力力");
        //        GreenDaoHelper.getDaoSession().getCustomerDao().insert(customer);
    }

    //增
    public void test2(View view) {
        customer = new Customer();
        customer.setAge(5);
        customer.setName("小力力");
        customer.setSex("男");
        customer.setLike("篮球");
        GreenDaoHelper.getDaoSession().getCustomerDao().insert(customer);
        //新增的表
        Student student=new Student();
        student.setAge("22");
        student.setName("JOHN");
        GreenDaoHelper.getDaoSession().getStudentDao().insert(student);


    }

    // 删
    public void test3(View view) {
        GreenDaoHelper.getDaoSession().getCustomerDao().delete(customer);
    }

    //改
    public void test4(View view) {
        customer = new Customer((long) 1, "全是力力", 25);
        GreenDaoHelper.getDaoSession().getCustomerDao().update(customer);
    }

    //查
    public void test5(View view) {
        List<Customer> users = GreenDaoHelper.getDaoSession().getCustomerDao().loadAll();
        L.e("size: "+users.size());
        for (int i = 0; i < users.size(); i++) {
            L.e(" Id: " + users.get(i).getId() + "name: " +
                    users.get(i).getName() + " age: " + users.get(i).getAge()
            +"sex: "+users.get(i).getSex()+" like: ");
        }

//        List<Customer> customers = GreenDaoHelper.getDaoSession().
//                getCustomerDao().queryBuilder().where
//                (CustomerDao.Properties.Name.eq("小力力")).list();
//
//        for (Customer customer : customers) {
//            L.e("遍历Name: " + customer.getName());
//        }

    }

    //更新
    public void test6(View view) {
        //1.修改build gradle版本号 2.更改database字段
    }


}
