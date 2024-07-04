/**
 * @file Proje1
 * @description Karışık olarak verilen metni düzeltip, sözlükteki kelimelerin
 * kaç kez geçtiğini bulan program
 * @author Zeynep Nursena Kütük 
 */

package proje1;

import java.util.Scanner;

public class Proje1 {

    /*büyük küçük harf düzeltme metotu*/
    public static char ignoreCase(char ch) {
        if (ch <= 90 && ch >= 65) {
            ch += 32;
        }
        return ch;
    }

    /*metinde kaç tane sözcük olduğunu sayan metot*/
    public static int sozcukSayma(String metin) {
        int sozcukSayisi = 0;
        for (int i = 0; i < metin.length(); i++) {
            if (metin.charAt(i) == ' ') {
                sozcukSayisi++;
            }
        }
        return sozcukSayisi;
    }

    /*metindeki kelimelerin karakterlerini diziye çeviren metot*/
    public static char[] chDiziCevir(String sozcuk) {
        char dizi[] = new char[sozcuk.length()];
        for (int i = 0; i < sozcuk.length(); i++) {
            dizi[i] = sozcuk.charAt(i);
        }
        return dizi;
    }

    /* metni diziye çeviren metot*/
    public static String[] metniDiziyeCevirme(String metin) {
        String yeniSozcuk = "";
        String[] sozcukler = new String[sozcukSayma(metin) + 1];

        int count = 0;
        for (int i = 0; i < metin.length(); i++) {

            if (metin.charAt(i) != ' ') {

                yeniSozcuk += metin.charAt(i);
            } else {
                sozcukler[count] = yeniSozcuk;
                yeniSozcuk = "";
                count++;
            }
            sozcukler[count] = yeniSozcuk;
        }
        return sozcukler;
    }

    /*verilen bozuk ve sözlük sözcüğünün eşit olup olmadığını bulan metot*/
    public static boolean SozcukMu(String bozukSozcuk, String sozlukSozcuk) {

        char[] bozukDizi = chDiziCevir(bozukSozcuk);
        char[] sozlukDizisi = chDiziCevir(sozlukSozcuk);
        boolean esit = false;

        if (bozukDizi.length == sozlukDizisi.length) {
            for (int i = 0; i < bozukDizi.length; i++) {
                if ((bozukDizi[i] == sozlukDizisi[i])) {
                    esit = true;
                } else if ((ignoreCase(bozukDizi[i])) == sozlukDizisi[i]) {
                    esit = true;
                } else {
                    esit = false;
                    break;
                }
            }
        }
        return esit;
    }

    /*string olarak verilen bir sözcüğün harfini bir sağa kaydırmaya yarayan metot*/
    public static String rotateRight(String sozcuk) {
        char[] karakter = chDiziCevir(sozcuk);
        String yeniMetin = "";
        char temp = karakter[0];
        for (int i = 1; i < karakter.length; i++) {
            yeniMetin += karakter[i];
        }
        yeniMetin += temp;
        return yeniMetin;
    }

    /* bozuk olarak gelen metni kelime kelime kontrol edip sözlükteki düzgün kelimelerle karşılaştırarak
    kelimeyi duzgunMetin'e tek tek atayan metot
     */
    public static String MetniDuzeltme(String[] bozukDizi, String[] sozlukDizi) {
        String duzgunMetin = "";
        for (int i = 0; i < bozukDizi.length; i++) {
            String tempSozcuk = bozukDizi[i];
            for (String sozlukDizi1 : sozlukDizi) {
                int count = 0;
                while (!SozcukMu(tempSozcuk, sozlukDizi1) && (count < tempSozcuk.length())) {
                    tempSozcuk = rotateRight(tempSozcuk);
                    count++;
                }
                if (tempSozcuk.equals(sozlukDizi1)) {
                    break;
                }
            }
            duzgunMetin += tempSozcuk;
            if (i < bozukDizi.length - 1) {
                duzgunMetin += " ";
            }
        }
        return duzgunMetin;
    }

    /*sözlükteki kelimelerin metinde kaç kez geçtiğini sayan metot*/
    public static int[] kacKez(String[] duzenlenenMetinDizisi, String[] sozlukDizisi) {

        int kacKez[] = new int[sozlukDizisi.length];
        int count = 0;
        for (int i = 0; i < sozlukDizisi.length; i++) {
            for (String duzenlenenMetinDizisi1 : duzenlenenMetinDizisi) {
                if (SozcukMu(duzenlenenMetinDizisi1, sozlukDizisi[i])) {
                    count++;
                }
            }
            kacKez[i] = count;
            count = 0;
        }
        return kacKez;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Karışık metni giriniz: ");
        String karisikMetin = s.nextLine();
        System.out.print("Sözlüğü giriniz: ");
        String sozluk = s.nextLine();

        String[] sozlukDizisi = metniDiziyeCevirme(sozluk);
        String[] karisikMetinDizisi = metniDiziyeCevirme(karisikMetin);
        String duzgunMetin = MetniDuzeltme(karisikMetinDizisi, sozlukDizisi);
        String[] duzgunDizi = metniDiziyeCevirme(duzgunMetin);

        System.out.print(duzgunMetin);

        int kacKez[] = kacKez(duzgunDizi, sozlukDizisi);
        System.out.println("");
        for (int i = 0; i < kacKez.length; i++) {
            System.out.print(kacKez[i] + " ");
        }
    }
}
