/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package maideceren_boz_hw4;

/**
 *
 * @author ceren
 */
import java.io.IOException;

public interface Hash_Interface {
    public Integer GetHash(String mystring);
    public void ReadFileandGenerateHash(String filename, int size) throws Exception;
    public void DisplayResult(String Outputfile) throws IOException;
    public void DisplayResult();
    public void DisplayResultOrdered(String Outputfile) throws IOException;
    public int showFrequency(String myword);
    public String showMaxRepeatedWord();
    public int checkWord(String myword);
    public float TestEfficiency();
    public int NumberOfCollusion();

}
