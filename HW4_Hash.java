/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maideceren_boz_hw4;

/**
 *
 * @author ceren
 */
import java.io.*;
import java.util.Scanner;

public class HW4_Hash implements Hash_Interface
{
    private int size;
    private HashArray[] HashTable;
    private Integer totalWords = 0;
    private Integer totalCollisions = 0;

    public Integer GetHash(String myString) {
        int Sum = 0;

        for (int i = 0; i < myString.length(); i++) {
           Sum += myString.charAt(i);
        }

        return Sum % size;
    }
    public void ReadFileandGenerateHash(String filename, int size) throws Exception {
        this.size = size;
        this.HashTable = new HashArray[size];
        try (Scanner sc = new Scanner(new FileReader(new File(filename)))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().replaceAll("[\\p{Punct}&&[^']]","").trim();
                String[] words = line.split("\\s+");
                int wordIndex = 0;
                while (wordIndex < words.length) {
                    String word = words[wordIndex++];
                    totalWords++;
                    int hashIndex = GetHash(word);
                    HashArray hashArray = HashTable[hashIndex];
                    if (hashArray == null) {
                        totalCollisions++;
                        HashN newHashN = new HashN(word, null);
                        HashArray newHashArray = new HashArray(newHashN);
                        newHashN.frequency = 1;
                        newHashN.position += (totalWords + " ");
                        HashTable[hashIndex] = newHashArray;
                    } else {
                        boolean wordFound = false;
                        HashN currentNode = hashArray.node;

                        while (currentNode != null) {
                            if (currentNode.item.equals(word)) {
                                wordFound = true;
                                currentNode.frequency++;

                                if (currentNode.frequency > hashArray.maxFrequency) {
                                    hashArray.maxFrequency = currentNode.frequency;
                                }

                                currentNode.position += (totalWords + " ");
                                break;
                            }
                            currentNode = currentNode.node;
                        }

                        if (!wordFound) {
                            totalCollisions++;
                            HashN newHashN = new HashN(word, hashArray.node);
                            newHashN.node = hashArray.node;
                            hashArray.node = newHashN;
                            newHashN.frequency = 1;
                            newHashN.position += (totalWords + " ");
                        }
                    }
                }
            }
        }
    }
    public void DisplayResult(String outputFile) throws IOException {
        try (FileWriter fw = new FileWriter(outputFile, false)) {
            for (HashArray hashArray : HashTable) {
                if (hashArray == null) continue;

                for (HashN currentNode = hashArray.node; currentNode != null; currentNode = currentNode.node) {
                    writeToFile(fw, currentNode);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeToFile(FileWriter fw, HashN currentNode) throws IOException {
        fw.write("The word '" + currentNode.item + "' is used " + currentNode.frequency + " time/times.\n");
    }


    public void DisplayResult() {
        for (HashArray hashArray : HashTable) {
            if (hashArray != null) {
                for (HashN currentNode = hashArray.node; currentNode != null; currentNode = currentNode.node) {
                    System.out.println(formatResult(currentNode));
                }
            }
        }
    }
    private String formatResult(HashN currentNode) {
        return "The word '" + currentNode.item + "' is used " + currentNode.frequency + " time/times.";
    }

    public void DisplayResultOrdered(String Outputfile) throws IOException {
        try (FileWriter fw = new FileWriter(Outputfile, false)) {
            HashN[] tempNode = new HashN[totalWords];
            int i = 0;

            for (HashArray hashArray : HashTable) {
                if (hashArray != null) {
                    for (HashN currentNode = hashArray.node; currentNode != null; currentNode = currentNode.node) {
                        tempNode[i++] = currentNode;
                    }
                }
            }

            HashN[] currentnode = quickSort(tempNode, 0, totalWords - 1);

            for (HashN node : currentnode) {
                if (node != null) {
                    fw.write("The word '" + node.item + "' is used " + node.frequency + " time/times.\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public HashN[] quickSort(HashN[] data, int low, int high) {
        if (low < high) {
            int partitionIndex = partition(data, low, high);
            quickSort(data, low, partitionIndex - 1);
            quickSort(data, partitionIndex + 1, high);
        }
        return data;
    }

    private int partition(HashN[] data, int low, int high) {
        HashN pivot = data[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (data[j] != null && (pivot == null || data[j].frequency >= pivot.frequency)) {
                i++;
                HashN temp = data[i];
                data[i] = data[j];
                data[j] = temp;
            }
        }

        if (data[high] != null) {
            HashN temp = data[i + 1];
            data[i + 1] = data[high];
            data[high] = temp;
        }

        return i + 1;
    }

    public int showFrequency(String myword) {
        HashArray currentArr = HashTable[GetHash(myword)];
        if (currentArr != null) {
            HashN currentNode = currentArr.node;

            while (currentNode != null) {
                if (currentNode.item.equals(myword)) {
                    return currentNode.frequency;
                }
                currentNode = currentNode.node;
            }
        }

        return -1;
    }

    public String showMaxRepeatedWord() {
        int maxFrequency = 0;
        HashN maxFrequencyNode = null;
        for (HashArray hashArray : HashTable) {
            if (hashArray != null) {
                for (HashN currentNode = hashArray.node; currentNode != null; currentNode = currentNode.node) {
                    if (currentNode.frequency > maxFrequency) {
                        maxFrequency = currentNode.frequency;
                        maxFrequencyNode = currentNode;
                    }
                }
            }
        }

        if (maxFrequencyNode != null) {
            return maxFrequencyNode.item + " is the most used word with " + maxFrequency + " times.";
        } else {
            return null;
        }

    }

    public int checkWord(String myword) {
        HashArray wordHashTable = HashTable[GetHash(myword)];

        if (wordHashTable != null) {
            HashN currentNode = wordHashTable.node;

            while (currentNode != null) {
                if (currentNode.item.equals(myword)) {
                    System.out.println(myword + " is found in the text. Repeated " + currentNode.frequency + " time. Location :" + currentNode.position);
                    return currentNode.frequency;
                }
                currentNode = currentNode.node;
            }
        }

        System.out.println(myword + " is not found in the text");
        return -1;
    }

    public float TestEfficiency() {
        int nonNullCount = 0;

        for (HashArray hashArray : HashTable) {
            if (hashArray != null) {
                nonNullCount++;
            }
        }
        return (1 - ((float) nonNullCount / size)) * (1 - ((float) totalCollisions / totalWords));
    }

    public int NumberOfCollusion() {
            int totalCollisions = 0;

            for (HashArray hashArray : HashTable) {
                if (hashArray != null) {
                    HashN currentNode = hashArray.node;
                    boolean hasDuplicate = false;

                    while (currentNode != null) {
                        if (currentNode.frequency > 1 && !hasDuplicate) {
                            totalCollisions++;
                            hasDuplicate = true;
                        }
                        currentNode = currentNode.node;
                    }
                }
            }

            return totalCollisions;
        }


}