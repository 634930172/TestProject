package com.john.testproject.utils;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Author: John
 * E-mail: cm1@erongdu.com
 * Date: 2017/10/18 下午3:19
 * <p/>
 * Description: 手机短信、通讯录、通话记录获取
 */
public class PhoneUtil {
    /*
    * 自定义显示Contacts提供的联系人的方法
    */
    public static List<Contact> getContacts(Context context) {
        ArrayList<Contact> contacts = new ArrayList<>();
        Cursor cursor = null;
        Cursor phoneCursor = null;
        try {
            //生成ContentResolver对象
            ContentResolver contentResolver = context.getContentResolver();

            // 获得所有的联系人
            cursor = contentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            //这段代码和上面代码是等价的，使用两种方式获得联系人的Uri
        /*Cursor cursor = contentResolver.query(Uri.parse("content://com.android.contacts/contacts"), null, null, null, null);*/
            if (cursor == null)
                return contacts;
            // 循环遍历
            if (cursor.moveToFirst()) {

                int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                int displayNameColumn = cursor
                        .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

                do {
                    // 获得联系人的ID
                    String contactId = cursor.getString(idColumn);
                    // 获得联系人姓名
                    String displayName = cursor.getString(displayNameColumn);
                    //使用Toast技术显示获得的联系人信息
                    //Toast.makeText(context, "联系人姓名：" + displayName, Toast.LENGTH_LONG).show();

                    // 查看联系人有多少个号码，如果没有号码，返回0
                    int phoneCount = cursor
                            .getInt(cursor
                                    .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                    if (phoneCount > 0) {
                        // 获得联系人的电话号码列表
                        phoneCursor = context.getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + "=" + contactId, null, null);
                        if (phoneCursor != null) {
                            if (phoneCursor.moveToFirst()) {
                                do {
                                    Contact contact = new Contact();
                                    contact.setName(displayName);
                                    //遍历所有的联系人下面所有的电话号码
                                    String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    contact.setPhone(phoneNumber);
                                    //使用Toast技术显示获得的号码
                                    //Toast.makeText(context, "联系人电话：" + phoneNumber, Toast.LENGTH_LONG).show();
                                    contacts.add(contact);
                                }
                                while (phoneCursor.moveToNext());
                            }
                            phoneCursor.close();
                        }
                    }
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "getContacts: 无提取联系人权限");

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (phoneCursor != null) {
                phoneCursor.close();
            }
        }


        return contacts;
    }

    static class Contact {
        private String name;
        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "contactInfo:contact:" + name + ";phone:" + phone ;
        }
    }

    public static List<RecordEntity> getCalls(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = null;
        List<RecordEntity> mRecordList = new ArrayList<RecordEntity>();
        //检查是否有获取
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return mRecordList ;
            }
            cursor = contentResolver.query(
                    CallLog.Calls.CONTENT_URI, null, null, null,
                    CallLog.Calls.DATE + " desc");

            while (cursor.moveToNext()) {
                RecordEntity record = new RecordEntity();
                record.name = cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.CACHED_NAME));
                record.phone = cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.NUMBER));
                record.isReceive = cursor.getInt(cursor
                        .getColumnIndex(CallLog.Calls.TYPE));
                record.time = String.valueOf(cursor.getLong(cursor
                        .getColumnIndex(CallLog.Calls.DATE)));

                System.out.println(record.toString());
                mRecordList.add(record);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return mRecordList;
    }

    private static class RecordEntity {
        /**
         * 归属地
         */
        public String addr;
        /**
         * 是主叫、被叫
         */
        public int isReceive;
        /**
         * 姓名
         */
        public String name;
        /**
         * 手机号
         */
        public String phone;
        /**
         * 时间
         */
        public String time;

        @Override
        public String toString() {
            return "RecordEntity [toString()=" + name + "," + phone + "," + isReceive + "," + time + "," + addr + "]";
        }
    }

    // --------------------------------收到的短息信息----------------------------------
    public static List<MessageInfo> getSmsInfos(Context context) {
        List<MessageInfo> list = new ArrayList<>();
        final String SMS_URI_INBOX = "content://sms/";// 所有内容
        Cursor cursor = null;
        Cursor localCursor = null;
        try {
            ContentResolver cr = context.getContentResolver();
            String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
            Uri uri = Uri.parse(SMS_URI_INBOX);
            cursor = cr.query(uri, projection, null, null, "date desc");
            if (cursor == null)
                return list;
            while (cursor.moveToNext()) {
                MessageInfo messageInfo = new MessageInfo();
                // -----------------------信息----------------
                int nameColumn = cursor.getColumnIndex("person");// 联系人姓名列表序号
                int phoneNumberColumn = cursor.getColumnIndex("address");// 手机号
                int smsbodyColumn = cursor.getColumnIndex("body");// 短信内容
                int dateColumn = cursor.getColumnIndex("date");// 日期
                int typeColumn = cursor.getColumnIndex("type");// 收发类型 1表示接受 2表示发送
                String nameId = cursor.getString(nameColumn);
                String phoneNumber = cursor.getString(phoneNumberColumn);
                String smsbody = cursor.getString(smsbodyColumn);
                Date d = new Date(Long.parseLong(cursor.getString(dateColumn)));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd " + "\n" + "hh:mm:ss", Locale.getDefault());
                String date = dateFormat.format(d);

                // --------------------------匹配联系人名字--------------------------

                Uri personUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, phoneNumber);
                localCursor = cr.query(personUri, new String[]{PhoneLookup.DISPLAY_NAME, PhoneLookup.PHOTO_ID, PhoneLookup._ID}, null, null, null);
                if (localCursor == null)
                    continue;
                /*System.out.println(localCursor.getCount());
                System.out.println("之前----" + localCursor);*/
                if (localCursor.getCount() != 0) {
                    localCursor.moveToFirst();
                    /*System.out.println("之后----" + localCursor);*/
                    String name = localCursor.getString(localCursor.getColumnIndex(PhoneLookup.DISPLAY_NAME));
                    messageInfo.setName(name);
                    messageInfo.setPhone(phoneNumber);
                    messageInfo.setSmsBody(smsbody);
                } else {
                    messageInfo.setName(phoneNumber);
                    messageInfo.setPhone(phoneNumber);
                    messageInfo.setSmsBody(smsbody);
                }
                localCursor.close();
                if (typeColumn == 1) {
                    messageInfo.setType("20");
                } else {
                    messageInfo.setType("10");
                }
                messageInfo.setTime(String.valueOf(d.getTime()));
                messageInfo.toString();
                list.add(messageInfo);
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e("TAG", "getContacts: 无读取联系人权限");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (localCursor != null) {
                localCursor.close();
            }

        }
        return list;
    }

    static class MessageInfo {
        String name;
        String phone;
        /**
         * 收发标识，10发20收
         */
        String type;
        /**
         * 收信人
         */
        String receiver;
        /**
         * 发送时间
         */
        String time;
        /**
         * 短信内容
         */
        String smsBody;

        public String getSmsBody() {
            return smsBody;
        }

        public void setSmsBody(String smsBody) {
            this.smsBody = smsBody;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "MessageInfo:name:" + name + ";phone:" + phone + ";isReceive:" + type + "；sendTime：" + time+";smsBody:"+smsBody;
        }
    }
}
