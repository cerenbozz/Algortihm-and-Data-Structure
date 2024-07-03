/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maideceren_boz_hw4;

/**
 *
 * @author ceren
 */
public class HashN {
    String item;
    HashN node;
    int frequency;
    String position;

    public HashN(String item, HashN node) {
        this.item = item;
        this.node = node;
        this.frequency = 0;
        this.position = "";
    }


}