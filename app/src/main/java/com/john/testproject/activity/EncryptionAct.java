package com.john.testproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.john.testproject.R;
import com.john.testproject.encryption.MDUtil;
import com.john.testproject.encryption.RSA;
import com.john.testproject.utils.L;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/2/5 9:50
 * Description:
 */

public class EncryptionAct extends BaseAct {

    private static final String RSA_PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GN" +
            "ADCBiQKBgQC5HpUlMRoQ3v9DZwud/3o2UwiUCFByjA9aHZM" +
            "sDfFVgxzzSw9uD2a4YJYqqiuhHltUHdU9DV//356GI9tpuM" +
            "K23DRFtAWRHON+tSvva1+TKXvvO/VDWBxPyGRlM2SduP" +
            "fKLXqG5F8tOYon2oMsu7yxJJLHELdYdSYNfTC73ARcZwIDAQAB";


    private static final String RSA_PRIVATE_KEY="MIICdwIBADANBgkqhk" +
            "iG9w0BAQEFAASCAmEwggJdAgEAAoGBALkelSUxGhDe/0NnC53/ejZTCJQ" +
            "IUHKMD1odkywN8VWDHPNLD24PZrhgliqqK6EeW1Qd1T0NX//fnoYj22m4w" +
            "rbcNEW0BZEc4361K+9rX5Mpe+879UNYHE/IZGUzZJ2498oteobkXy05i" +
            "ifagyy7vLEkkscQt1h1Jg19MLvcBFxnAgMBAAECgYEAsS9cgeSlllBa8" +
            "yDGNcw6JZUEOOxcU8dLSm3UV+5geACuzQ+jDS0gnImWMe//Io/zymdjmrQ" +
            "alsZq7OmVMXPuqYcNUDz0bKscCLR3pveFOG3c/g6oTtMDxHB2IjNOn5et" +
            "gLKNmOuyM0JaChqedAcYpLF55ZEYIz6lY8XCKUijEQECQQDbMQg4QfOu6" +
            "VR1llrproQeLSYzkNSDDazL2iUeSHmW0KoDPMbdRjuRBU0tmjpeIXFkoB" +
            "FNHtKhEI+garuh4IcxAkEA2DTN2P2HCMuf8hMJKhw2cFV+tYDhYjht8on" +
            "6b71qNHK00LE3D3FciD1/iIlr7XA8Jlc9aHMOiMsCeChw88vnFwJBAJkzR" +
            "hl8D0QblYqyUkyuS22EBQVZnw8SScoZaIQoB8PfiFTG7//MjOmpmO3ctBs" +
            "CqOy57B3JA1LL7nci4o4cDqECQBBCwGyefMZzkh8BwYGyR4HhXDtIaysY9" +
            "WP1bqlyxIcXTXQYr/xwWTZB8dKSC1T7SPrVOuAdLy2onxHs" +
            "cLpVTpcCQDRoOl6GjiK/5OyQ1Dp6T6BBtF9UwQCyFk7sdMdWYkIMU57/" +
            "rMXbfBUKArrhaxKZsKHesAVHCgnOyAKWPnlAHek=";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encryption_layout);
    }

    //MD5
    public void test1(View view) {
        String data="I love You";
        L.e("加密后: "+MDUtil.encode(MDUtil.TYPE.MD5,data));
    }

    //RSA
    public void test2(View view) {
        String data="I love You";
        RSA.generateRSAKeyPair();

    }

}
