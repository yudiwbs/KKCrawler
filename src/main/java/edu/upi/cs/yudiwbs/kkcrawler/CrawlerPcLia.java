package edu.upi.cs.yudiwbs.kkcrawler;

import javax.swing.*;
import java.net.Authenticator;
import java.util.Scanner;

/**
 * Created by dell on 21/03/2017.
 */
public class CrawlerPcLia {
    public static void main(String[] args) {
        System.setProperty("https.proxyHost","cache.itb.ac.id") ;  //https sama http beda
        System.setProperty("https.proxyPort", "8080") ;
        String usr= "";


        //supaya password nggak kelihatan saat dientry (pake pc remote soalnya)

        String pwd;
        final String message = "Enter password";
        if( System.console() == null ) {
            final JPasswordField pf = new JPasswordField();
            pwd = JOptionPane.showConfirmDialog( null, pf, message,
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE ) == JOptionPane.OK_OPTION
                    ? new String( pf.getPassword() ) : "";
        } else
            pwd = new String( System.console().readPassword( "%s> ", message ) );

        Scanner keyboard = new Scanner(System.in);
        System.out.print("username:");
        usr = keyboard.next();

        Authenticator.setDefault(new ProxyAuth(usr, pwd));

        Crawler c = new Crawler();
        c.targetDir = "D:\\yudiwbs\\corpus\\kaskus\\livina-part3\\mentah";
        c.logFile   = "D:\\yudiwbs\\corpus\\kaskus\\livina-part3\\log.txt";
        //~/corpus/kaskus/aylaagya/
        c.outFileName  = "livina_part3";  //nanti jadi namafile_0001.txt dst
        c.seed = "https://www.kaskus.co.id/thread/535890e2c3cb172c3e8b49c0/klik--komunitas-livina-kaskus----part-3/";
        c.startPage =1 ;  //halaman satu biasanya pengumuman
        c.endPage = 500;    //hati2, kaskus walaupun sudah kelewat tetap akan memberikan hasil (halaman terakhir)
        c.startCrawl();
    }
}
