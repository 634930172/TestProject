package com.john.testproject.utils;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/11/30 0030
 * <p/>
 * Description:
 */

public class MakeXml  {

    private final static String rootPath = "F:\\layoutroot\\values-{0}x{1}\\";

    private final static float dw = 320f;
    private final static float dh = 480f;

    private final static String WTemplate = "<dimen name=\"x{0}\">{1}px</dimen>\n";
    private final static String HTemplate = "<dimen name=\"y{0}\">{1}px</dimen>\n";

    public static void makeXml() {
        makeString(320, 480);
        makeString(480,800);
        makeString(480, 854);
        makeString(540, 960);
        makeString(600, 1024);
        makeString(720, 1184);
        makeString(720, 1196);
        makeString(720, 1280);
        makeString(768, 1024);
        makeString(800, 1280);
        makeString(1080, 1812);
        makeString(1080, 1920);
        makeString(1440, 2560);
    }

    public static void makeString(int w, int h) {

        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<resources>");
        float cellw = w / dw;
        for (int i = 1; i < 1440; i++) {
            sb.append(WTemplate.replace("{0}", i + "").replace("{1}",
                    change(cellw * i) + ""));
        }
        sb.append(WTemplate.replace("{0}", "320").replace("{1}", w + ""));
        sb.append("<\"/\"resources>");
        Log.e("TAG", w+"*"+h+":"+sb );
        StringBuffer sb2 = new StringBuffer();
        sb2.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb2.append("<resources>");
        float cellh = h / dh;
        for (int i = 1; i < 2560; i++) {
            sb2.append(HTemplate.replace("{0}", i + "").replace("{1}",
                    change(cellh * i) + ""));
        }
        sb2.append(HTemplate.replace("{0}", "480").replace("{1}", h + ""));
        sb2.append("<\"/\"resources>");
        Log.e("TAG", w+"*"+h+":"+sb2 );
        String path = rootPath.replace("{0}", h + "").replace("{1}", w + "");
        File rootFile = new File(path);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        File layxFile = new File(path + "lay_x.xml");
        if (!layxFile.exists()) {
            layxFile.mkdirs();
        }
        File layyFile = new File(path + "lay_y.xml");
        if (!layyFile.exists()) {
            layyFile.mkdirs();
        }
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(layxFile));
            pw.print(sb.toString());
            pw.close();
            pw = new PrintWriter(new FileOutputStream(layyFile));
            pw.print(sb2.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static float change(float a) {
        int temp = (int) (a * 100);
        return temp / 100f;
    }
}
