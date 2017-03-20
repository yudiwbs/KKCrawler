package edu.upi.cs.yudiwbs.kkcrawler;

/**
 * Created by yudiwbs on 18/03/2017.
 *
 */
public class CrawlerLaptopYW {
    public static void main(String[] args) {
        Crawler c = new Crawler();


        c.targetDir = "D:\\corpus\\kaskus\\brio_part1\\mentah";
        c.logFile   = "D:\\corpus\\kaskus\\brio_part1\\log.txt";
        //~/corpus/kaskus/aylaagya/
        c.outFileName  = "brio_part1";  //nanti jadi namafile_0001.txt dst
        c.seed = "https://www.kaskus.co.id/thread/528493d53fcb175c30000009/honda-brio-at-kaskus-part-i---part-1/";
        c.startPage =2 ;  //halaman satu biasanya pengumuman
        c.endPage = 50;    //hati2, kaskus walaupun sudah kelewat tetap akan memberikan hasil (halaman terakhir)
        c.startCrawl();
    }
}
