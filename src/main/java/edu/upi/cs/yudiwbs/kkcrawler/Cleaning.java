package edu.upi.cs.yudiwbs.kkcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
        int id;
        String file;
        String url;
        String question;
        String anwser;
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("id:"+id+"\n");
            sb.append("q:"+question+"\n");
            sb.append("a:"+anwser+"\n");
            return sb.toString();
        }
    }

    public void proses() {
        String strFile = "D:\\corpus\\kaskus\\mobilio\\mentah\\mobilio_00010.txt"; //untuk test
        System.out.println("Proses cleaning");
        File input = new File(strFile);
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Element el1 = doc.select("div.post").first();
        //System.out.println(el1.text());

        //ambil posting
        Elements els = doc.select("div.post");

        //buang elemen yang tidak penting
        for( Element element : doc.select("time") )
        {
            element.remove();
        }

        for( Element element : doc.select("a.user-post-tool") )
        {
            element.remove();
        }



        for (Element el:els) {
            //ambil quote
            Elements elsQuote = el.select("span.post-quote");
            String oldIsi = el.text();
            String isi = oldIsi;

            System.out.println();
            System.out.println(el);
            System.out.println("Elemen div.post:");
            System.out.println("==============>");

            ArrayList<QA> arQA = new ArrayList<>();

            //loop quote yang ada
            //ambil answer
            //ganti string  answer di posting dengan []
            int i=0;
            for (Element el2:elsQuote) {
                QA qa = new QA();
                arQA.add(qa);

                String question = el2.text();
                //buang kalimat di depan queestion
                //contoh: Quote:Original Posted By diy.carport ► ...question..
                String[] arrStr  = question.split("►");
                if (arrStr.length>1) {
                    qa.question = arrStr[1];  //ambil quetion
                }
                qa.id = i;
                i++;
                System.out.println(el2.text());
                isi = isi.replace(el2.text(),String.format(" [%d] ",qa.id));
            }

            /*
                 bisa ada kasus
                 [1] jawaban
                 [1] jawaban 1 [2] jawaban 2
                 [1] [2] jawaban semua
                 jawaban semua [1] [2]
                 jawab 1 [1]
                 [1] jawaban dengan mengutip quote [2]  (jadi [2] adalah jawaban)
             */
            for (QA qa:arQA) {
                //split dengan [] sebagai pemisah
                String[] splitIsi = isi.split("\\[" + qa.id + "\\]");
                if (splitIsi.length>1) {
                    //System.out.println("ans =" + splitIsi[1]);
                    qa.anwser = splitIsi[1].replaceAll("\\[[0-9]+\\]"," "); //hapus quote yg lain kalau ada
                }
            }

            System.out.println("===");
            System.out.println("isi awal:"+oldIsi);
            System.out.println("isi setelah dipisahkan:"+isi);

            System.out.println("Hasil ektraksi");
            for (QA qa:arQA) {
                System.out.println(qa);
            }
            System.out.println("=======>");
        }
    }


    public static void main(String[] args) {
        Cleaning c = new Cleaning();
        c.proses();
    }
}
