package edu.upi.cs.yudiwbs.kkcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

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


    public static void main(String[] args) {
        String strFile = "D:\\corpus\\kaskus_mobilio\\mentah\\mobilio_00010.txt"; //untuk test
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
        for (Element el:els) {
            Elements elsQuote = el.select("span.post-quote");
            String isi = el.text();
            System.out.println("isi:"+isi);
            System.out.println();
            System.out.println(el);
            System.out.println("=======>");
            for (Element el2:elsQuote) {
                System.out.println(el2.text());
                isi.replace(el2.text()," [] ");
                System.out.println("###");
            }
            System.out.println("=======>");
        }

    }
}
