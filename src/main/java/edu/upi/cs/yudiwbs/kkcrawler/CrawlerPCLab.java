package edu.upi.cs.yudiwbs.kkcrawler;

/**
 * Created by yudiwbs on 18/03/17.
 */
public class CrawlerPCLab {
    public static void main(String[] args) {
        Crawler c = new Crawler();
        c.targetDir = "/home/yudiwbs/corpus/kaskus/aylaagya/mentah";
        c.logFile   = "/home/yudiwbs/corpus/kaskus/aylaagya/log.txt";
        c.outFileName  = "aylaagya";  //nanti jadi namafile_0001.txt dst
        c.seed = "https://www.kaskus.co.id/thread/55c10623620881f53a8b4568/ayla-agya--kaskus----part-3/";
        c.startPage =101;  //halaman satu biasanya pengumuman
        c.endPage = 200;    //hati2, kaskus walaupun sudah kelewat tetap akan memberikan hasil (halaman terakhir)
        c.startCrawl();
    }
}
