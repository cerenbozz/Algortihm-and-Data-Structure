/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package maideceren_boz_hw4;

/**
 *
 * @author ceren
 */
public class MaideCeren_Boz_HW4 {
    public static void main(String[] args) throws Exception {
        HW4_Hash mynewHash = new HW4_Hash();
        mynewHash.ReadFileandGenerateHash("C:\\Yeni klasör\\Mydata.txt",10000);

        mynewHash.DisplayResult();
        mynewHash.DisplayResult("C:\\Yeni klasör\\Mydata1.txt");
        mynewHash.DisplayResultOrdered("C:\\Yeni klasör\\Mydata2.txt");

        mynewHash.checkWord("the");
        mynewHash.checkWord("Lutfullah");

        mynewHash.showFrequency("the");
        mynewHash.showFrequency("Lutfullah");

        System.out.println("Max Repeated Word: "+ mynewHash.showMaxRepeatedWord());
        System.out.println("Test Efficiency: "+mynewHash.TestEfficiency());
        System.out.println("NumberOfCollusion: "+mynewHash.NumberOfCollusion());

    }
}