package edu.upi.cs.yudiwbs.kkcrawler;

import java.io.*;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Created by yudiwbs on 15/03/2017.
 */

public class Crawler {
    //String targetDir ="D:\\corpus\\kaskus_otomotif\\crawlmentah\\"; //harus diakhiri dengan slah
    //String logFile = "D:\\corpus\\kaskus_otomotif\\log.txt";
    String targetDir = "";
    String logFile   = "";
    String outFileName  = "";  //nanti jadi namafile_0001.txt dst
    String seed = "";
    int startPage = 0;  //halaman satu biasanya pengumuman
    int endPage = 0;    //hati2, kaskus walaupun sudah kelewat tetap akan memberikan hasil (halaman terakhir)

    //String seed = "https://www.kaskus.co.id/thread/54a8f734c1cb176d188b4569/all-about-car-audio---part-1/";
    //nanti ditambah angka dibelakang
    //all-about-car-audio---part-1/1
    //all-about-car-audio---part-1/2 dst

    int minDelay = 30000; //dalam ms , nanti ditambah nilai random
    int randomDelay = 30000; //nilai random tambahan

    private String crawl(String strUrl,PrintWriter pwLog) {
        URL url;
        String out="err";
        StringBuilder sb = new StringBuilder();
        try {
            // get URL conten
            url = new URL(strUrl);
            //url = new URL("http://yuliadi.com/");
            System.out.println("Crawl:"+strUrl);   //nanti pake logger?
            URLConnection conn = url.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                //System.out.println(inputLine);
                sb.append(inputLine);
            }
            br.close();
            System.out.println("selesai:"+strUrl);   //nanti pake logger?
        } catch (MalformedURLException e) {
            e.printStackTrace();
            pwLog.println("Error:"+e.toString());
            pwLog.flush();
        } catch (IOException e) {
            e.printStackTrace();
            pwLog.println("Error:"+e.toString());
            pwLog.flush();
        }
        return sb.toString();
    }




    /*
        loop crawl
    */
    void startCrawl() {

        //tambah backslah jika belum
         //if
        //targetDir
        String sep = File.separator;
        String karakterAkhir = targetDir.substring(targetDir.length() - 1);
        if (!karakterAkhir.equals(sep)) {
            targetDir = targetDir + sep;
        }

        //siapkan log
        PrintWriter pwLog = null;
        try {
            File log = new File(logFile);
            if(!log.exists()){
                pwLog = new PrintWriter(logFile);
            } else
                pwLog =  new PrintWriter(new FileWriter(logFile, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        pwLog.println();
        pwLog.println("Sesi crawl:"+dtf.format(now));
        for (int i=startPage; i<=endPage; i++ ) {

            String strUrl = seed + i;
            pwLog.println("Mulai Crawl URL:"+strUrl);
            pwLog.flush();
            String hasil = crawl(strUrl,pwLog);
            //System.out.println("hasil="+hasil);
            String namaFileOut = targetDir+outFileName+"_"+String.format("%05d", i)+".txt";
            try {
                PrintWriter pw = new PrintWriter(namaFileOut);
                pwLog.println("Target file:"+namaFileOut);
                pw.write(hasil);
                pwLog.println("Crawl selesai");
                pwLog.flush();
                long sleepTime = minDelay + Math.round(Math.random()*randomDelay);
                System.out.println("Waktu sleep (detik):"+sleepTime/1000);
                pw.close();
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                pwLog.println("Error:"+e.toString());
                pwLog.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                pwLog.println("Error:"+e.toString());
                pwLog.flush();
            }
        }
        System.out.println("===> Selesai semua..");
        now = LocalDateTime.now();
        pwLog.println();
        pwLog.println("Sesi crawl selesai:"+dtf.format(now));
        pwLog.close();
    }


    public static void main(String[] args) {
        //jangan crawl disini, buat spt CrawlerLaptopYW, CrawlerPCLab dst
        /*Crawler c = new Crawler();
        //c.targetDir = "D:\\corpus\\kaskus_sparepart\\mentah";
        //c.targetDir = "D:\\corpus\\kaskus_mobilio\\mentah";
        c.targetDir = "/home/yudiwbs/corpus/kaskus/aylaagya/mentah";
        //c.logFile   = "D:\\corpus\\kaskus_mobilio\\log.txt";
        c.logFile   = "/home/yudiwbs/corpus/kaskus/aylaagya/log.txt";
        //~/corpus/kaskus/aylaagya/
        c.outFileName  = "aylaagya";  //nanti jadi namafile_0001.txt dst
        c.seed = "https://www.kaskus.co.id/thread/55c10623620881f53a8b4568/ayla-agya--kaskus----part-3/";
        c.startPage =11 ;  //halaman satu biasanya pengumuman
        c.endPage = 100;    //hati2, kaskus walaupun sudah kelewat tetap akan memberikan hasil (halaman terakhir)
        c.startCrawl();
        */
    }

}
