package edu.upi.cs.yudiwbs.kkcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by yudiwbs on 18/03/2017.
 *
 *  - ambil div post
 *  - ambil data quote, berdasarkan span.post-quote
 *
 *  - isi element masih bercampur antara quote dan bukan, solusinya cari quote di isi, lalu teks
 *    non quote menjadi jawaban
 *
 *    struktur idealnya
 *    [quote1]
 *    jawab
 *    [quote2]
 *    jawab
 *
 *    tapi hati-hati strukturnya bisa
 *    [quote1]
 *    [quote2]
 *    jawaban semua

 *
 */


public class Cleaning {

    String inputDir = "";
    String outputDir = "";

    private class QA {
        String file;
        String url;
        String q;
        String a;
    }


    public static void main(String[] args) {
        String strFile = "D:\\corpus\\kaskus\\mobilio\\mentah\\mobilio_00010.txt"; //untuk test
        System.out.println("Proses cleaning");
        File input = new File(strFile);
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // img with src ending .png

        //Element el1 = doc.select("div.post").first();
        //System.out.println(el1.text());
        Elements els = doc.select("div.post");
        ArrayList arQA = new ArrayList<>();
        for (Element el:els) {
            Elements elsQuote = el.select("span.post-quote");
            String oldIsi = el.text();
            String isi = oldIsi;

            System.out.println();
            System.out.println(el);
            System.out.println("=======>");

            for (Element el2:elsQuote) {

                System.out.println(el2.text());
                isi = isi.replace(el2.text()," [] ");
                System.out.println("###");
            }
            System.out.println("===");
            System.out.println("isi awal:"+oldIsi);
            System.out.println("isi setelah dipisahkan:"+isi);
            System.out.println("=======>");
        }

    }
}
