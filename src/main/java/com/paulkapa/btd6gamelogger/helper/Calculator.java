package com.paulkapa.btd6gamelogger.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

import com.paulkapa.btd6gamelogger.models.game.Tower;
import com.paulkapa.btd6gamelogger.models.game.Upgrade;

public class Calculator {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String args[]) throws IOException {
        String select = "reset";
        boolean promptForWrongInput = true;
        while(select != "exit") {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            if(select == "reset") {
                System.out.println("----------------------------------------------------------");
                System.out.println("1) Calculate price difference in percentage - tower cost");
                System.out.println("2) Calculate price difference in percentage - upgrade cost");
                System.out.println("3) Calculate average of values");
                System.out.println("-Type 'exit' to end program or type an option number-");
                System.out.print(" > ");
                select = "reset";
                select = sc.next().trim();
                System.out.println("----------------------------------------------------------");
            } else if(select != "c") {
                switch(select) {
                    case "1": {}
                    case "2": {
                        String inititalSelection = select;
                        System.out.println("----------------------------------------------------------");
                        System.out.println("1) Easy");
                        System.out.println("2) Medium - no difference");
                        System.out.println("3) Hard");
                        System.out.println("4) CHIMPS");
                        System.out.println("-Type 'back' to go back or type an option number-");
                        System.out.print(" > ");
                        select = "reset";
                        select = sc.next().trim();
                        System.out.println("----------------------------------------------------------");
                        switch(select) {
                            case "1": {
                                select = "1";
                                if(inititalSelection.equals("1")) {
                                    calculatePriceDifferenceTowers("Easy");
                                } else {
                                    calculatePriceDifferenceUpgrades("Super Monkey", "Easy");
                                }
                                break;
                            } case "2": {
                                select = "1";
                                if(inititalSelection.equals("1")) {
                                    calculatePriceDifferenceTowers("Medium");
                                } else {
                                    calculatePriceDifferenceUpgrades("Super Monkey", "Medium");
                                }
                                break;
                            } case "3": {
                                select = "1";
                                if(inititalSelection.equals("1")) {
                                    calculatePriceDifferenceTowers("Hard");
                                } else {
                                    calculatePriceDifferenceUpgrades("Super Monkey", "Hard");
                                }
                                break;
                            } case "4": {
                                select = "1";
                                if(inititalSelection.equals("1")) {
                                    calculatePriceDifferenceTowers("CHIMPS");
                                } else {
                                    calculatePriceDifferenceUpgrades("Super Monkey", "CHIMPS");
                                }
                                break;
                            } case "back": {
                                select = "reset";
                                break;
                            } default: {
                                select = "1";
                                System.out.println("Invalid option selected!");
                                break;
                            }
                        }
                        break;
                    } case "3": {
                        select = "reset";
                        System.out.println("----------------------------------------------------------");
                        String fileName = null;
                        boolean correct = false;
                        while(!correct) {
                            System.out.println("Input: 'file-name.txt'. You may create a file now!");
                            System.out.print(" > ");
                            fileName = sc.next().trim();
                            if(fileName.contains(".txt") && fileName.length() >= 4) {
                                correct = true;
                                System.out.println("----------------------------------------------------------");
                                System.out.println(calculateAverage(new File(fileName)));
                                System.out.println("----------------------------------------------------------");
                            } else {
                                select = "c";
                                System.out.println("Incorrect file name. Example: > input.txt");
                                break;
                            }
                        }
                        break;
                    } case "exit": {
                        select = "exit";
                        break;
                    } default: {
                        select = "reset";
                        System.out.println("Invalid option selected!");
                        break;
                    }
                }
                System.out.print("Type 'c' to continue! > ");
                select = sc.next().trim() == "reset" ? "reset" : "c";
            } else if(promptForWrongInput) {
                select = "reset";
                System.out.println("Your inputs seem to be wrong. Do you agree?");
                System.out.print(" > ");
                select = sc.next().trim().toUpperCase();
                if(select.equals("YES") || select.equals("Y")) {
                    promptForWrongInput = false;
                } else {
                    promptForWrongInput = true;
                }
                select = "reset";
            } else {
                select = "reset";
            }
        }
        sc.close();
    }

    public static void calculatePriceDifferenceTowers(String difficulty) throws IOException {
        System.out.println("----------------------------------------------------------");
        LinkedHashMap<String, Tower> towers = Tower.getTowers();
        double baseCost, cost;
        ArrayList<Double> percentages = new ArrayList<>();
        int initialSize, finalSize;
        for(String s : towers.keySet()) {
            if(towers.get(s).getType() != "Hero") {
                baseCost = (double)towers.get(s).getCost();
                System.out.println(s + ": cost=" + baseCost);
                System.out.print(difficulty + ": cost=");
                cost = Double.parseDouble(sc.next());
                percentages.add(cost / baseCost);
            }
        }
        initialSize = percentages.size();
        percentages = removeDuplicates(percentages);
        finalSize = percentages.size();
        System.out.println("----------------------------------------------------------");
        System.out.println("Entries tested = " + initialSize);
        System.out.println("Unique results = " + finalSize);
        System.out.println("----------------------------------------------------------");
        FileOutputStream fos = new FileOutputStream(new File("output-" + difficulty + ".txt"));
        String output;
        for(Double d : percentages) {
            int[] elements = findFraction(d);
            output = elements[0] + "/" + elements[1] + " = " + d + "\n";
            System.out.print(output);
            fos.write(output.getBytes());
            fos.flush();
        }
        System.out.println("----------------------------------------------------------");
        fos.close();
    }
    
    public static void calculatePriceDifferenceUpgrades(String towerName, String difficulty) throws IOException {
        System.out.println("----------------------------------------------------------");
        Upgrade[][] upgrades = Upgrade.getUpgradesByTowerName(towerName);
        double baseCost, cost;
        ArrayList<Double> percentages = new ArrayList<>();
        int initialSize, finalSize;
        System.out.println("Upgrades for " + towerName + ": ");
        for(int i = 0; i < upgrades.length; i++) {
            for(int j = 0; j < upgrades[i].length; j++) {
                Upgrade curr = upgrades[i][j];
                baseCost = (double)curr.getCost();
                System.out.println(curr.getName() + ": cost=" + baseCost);
                System.out.print(difficulty + ": cost=");
                cost = Double.parseDouble(sc.next());
                percentages.add(cost / baseCost);
            }
        }
        initialSize = percentages.size();
        percentages = removeDuplicates(percentages);
        finalSize = percentages.size();
        System.out.println("----------------------------------------------------------");
        System.out.println("Entries tested = " + initialSize);
        System.out.println("Unique results = " + finalSize);
        System.out.println("----------------------------------------------------------");
        FileOutputStream fos = new FileOutputStream(new File("output-" + towerName + "-" + difficulty + ".txt"));
        String output;
        for(Double d : percentages) {
            int[] elements = findFraction(d);
            output = elements[0] + "/" + elements[1] + " = " + d + "\n";
            System.out.print(output);
            fos.write(output.getBytes());
            fos.flush();
        }
        System.out.println("----------------------------------------------------------");
        fos.close();
    }
    
    public static int[] findFraction(double arg0) {
        for(int i = 0; i < 10000; i++) {
            for(int j = 1; j < 10000; j++) {
                if((double)i / (double)j == arg0) {
                    return new int[] {i, j};
                }
            }
        }
        return null;
    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
        ArrayList<T> newList = new ArrayList<T>();
        for (T element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }

    public static double calculateAverage(File input) throws IOException {
        FileReader fr = new FileReader(input);
        BufferedReader br = new BufferedReader(fr);
        ArrayList<Double> entries = new ArrayList<>();
        String line;
        boolean end = false;
        while(!end) {
            line = br.readLine();
            if(line == null) {
                end = true;
            } else {
                entries.add(Double.parseDouble(line));
            }
        }
        fr.close();
        double sum = 0d;
        int count = 0;
        for(double entry : entries) {
            sum += entry;
            count++;
        }
        return sum/(double)count;
    }
}