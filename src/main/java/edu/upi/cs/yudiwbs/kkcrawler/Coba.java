package edu.upi.cs.yudiwbs.kkcrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by yudiwbs on 15/03/2017.
 * untuk testing2
 */


public class Coba {
    public static void main(String[] args) {

        String s = "c:\\dir\\dir2";

        String s2 = s.substring(s.length() - 1);
        if (!s2.equals("\\")) {
            s = s + "\\";
        }

        System.out.println(s);

    }
}
