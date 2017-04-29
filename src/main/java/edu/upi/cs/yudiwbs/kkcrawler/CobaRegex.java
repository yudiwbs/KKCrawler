package edu.upi.cs.yudiwbs.kkcrawler;

/**
 * Created by yudiwbs on 07/04/2017.
 */
public class CobaRegex {

    public static void main(String[] args) {
          /*
          String s = "[0]";
          String[] arrS = s.split("\\[[0-9]*\\]");
          if (arrS.length>1) {
              System.out.println("ans =" + arrS[1]);
          } else {
              System.out.println("tidak ada jwaban");
          }
          */

        /*
          //contoh yang jawabannya di belakang
          String s = "isi setelah dipisahkan:Efek dari lampu bagian dalemnya Gan.. kalau mau agak adem, lampu dalemnya diganti aja... dulu pernah baca2... tapi lupa gantinya sama apa jenis lampunya biar ga terlalu panas. tapi so far ane pake ndak masalah kok Gan panas gitu.. CMIWW..  [0]   [1] ";

        //String[] arrS = s.split("\\[[0-9]*\\]");
        String[] arrS = s.split("\\[2\\]");
        if (arrS.length>1) {
            System.out.println("0 =" + arrS[0]);
            System.out.println("1 =" + arrS[1]);
        } else {
            System.out.println("tidak ada jwaban");
        }
        */

        String s = "a:   [1]  wew... beres nya tidak beres ya Gan... xixixixi";
        String hsl = s.replaceAll("\\[[0-9]+\\]"," "); //hapus quote yg lain kalau ada
        System.out.println(hsl);

    }
}

