package edu.upi.cs.yudiwbs.kkcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
    String outputFile = "";


    private void loadSinonimStopWords() {
        File fSinonim = new File("./resources/sinonim.txt");
        try (Scanner sc = new Scanner(fSinonim)) {
            while (sc.hasNext()) {
                String s = sc.next();
                System.out.println(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class QA {
        int id;
        String teksSumber;          //untuk debug: string lengkap quote dan jawaban
        String teksSumberProses;    //untu debug
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



    private void prosesFile(File input,PrintWriter pw) {

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

            //System.out.println();
            //System.out.println(el);
            //System.out.println("Elemen div.post:");
            //System.out.println("==============>");

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
                //System.out.println(el2.text());
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
                    qa.teksSumber = oldIsi;
                    qa.teksSumberProses = isi;
                    qa.anwser = splitIsi[1].replaceAll("\\[[0-9]+\\]"," ").trim(); //hapus quote yg lain kalau ada
                    //jawaban kosong, ambil string di depan
                    if (qa.anwser.equals("")) {
                        //System.out.println("******************************************************** answer kosong ******");
                        qa.anwser = splitIsi[0].replaceAll("\\[[0-9]+\\]"," ").trim(); //hapus quote yg lain kalau ada
                    }
                }
            }


            //nantinya output jadi dua file untuk dimasukkan ke data training

            if (arQA.size() > 0) {
                //System.out.println("======>");
                //System.out.println("isi awal:" + oldIsi);
                //System.out.println("isi setelah dipisahkan:" + isi);
                //System.out.println("Hasil ektraksi");
                for (QA qa : arQA) {
                    if (qa.question==null || qa.anwser==null) {
                        System.out.println("Ada yang null...");
                        System.out.println(qa.teksSumber);
                        System.out.println(qa.teksSumberProses);
                    } else {
                        if (qa.question.equals("") || qa.anwser.equals("")) {
                            System.out.println("Ada yang kosong...");
                            System.out.println(qa);
                        }
                        qa.question = qa.question.trim();
                        qa.anwser = qa.anwser.trim();
                        //System.out.println(qa);
                        pw.println("Q:");
                        pw.println(qa.question);
                        pw.println("A:");
                        pw.println(qa.anwser);
                        pw.println("");
                    }
                }
            }


        }
    }

    public void proses() {
        //String dir ="C:\\yudiwbs\\datamentah\\mentah\\";
        //String dir = "D:\\corpus\\kaskus\\mobilio\\mentah\\";

        System.out.println("Proses cleaning mulai");
        System.out.println("Dir:"+inputDir);
        //loop file dalam satu direktori
        final File dirFile = new File(inputDir);
        try {
            PrintWriter pw = new PrintWriter(outputFile);
            for(final File child : dirFile.listFiles()) {
                System.out.println("Proses file"+child.getAbsoluteFile());
                prosesFile(child,pw);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Proses cleaning selesai");
        //String file ="mobilio_00002.txt";
    }


    public static void main(String[] args) {
        Cleaning c = new Cleaning();
        //c.inputDir   = "C:\\yudiwbs\\datamentah\\mentah\\";
        //c.inputDir   = "C:\\yudiwbs\\datamentah\\mentah_kecil";
        //c.outputFile = "C:\\yudiwbs\\datamentah\\mentah_qa.txt";
        //String dir   = "D:\\corpus\\kaskus\\mobilio\\mentah\\";

        //String dir = "D:\\corpus\\kaskus\\mobilio\\mentah\\";

        c.inputDir   = "D:\\corpus\\kaskus\\mobilio\\mentah-kecil";
        c.outputFile = "D:\\corpus\\kaskus\\mobilio\\mentah-kecil-qa.txt";
        c.proses();
    }
}
