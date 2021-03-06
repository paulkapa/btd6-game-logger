package com.paulkapa.btd6gamelogger.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.paulkapa.btd6gamelogger.models.game.Tower;
import com.paulkapa.btd6gamelogger.models.game.Upgrade;

/**
 * <b>Helper Class</b>
 * <p>
 * It is used to calculate the cost modifiers for different game difficulties or
 * game modes, based on the costs set for Medium difficulty, Standard game mode.
 */
public class Calculator {

    public static final String RESET = "reset";

    /**
     * Scanner instance used to get input from user.
     */
    private static Scanner sc = new Scanner(System.in);

    public static void main(String args[]) throws Exception {
        var select = Calculator.RESET;
        var promptForWrongInput = true;
        while (select.equals("exit")) {
            if (select.equals(Calculator.RESET)) {
                System.out.print("-".repeat(58) + "\n" + "1) Calculate price difference in percentage - tower cost"
                        + "\n" + "2) Calculate price difference in percentage - upgrade cost" + "\n"
                        + "3) Calculate average of values" + "\n"
                        + "-Type 'exit' to end program or type an option number-\n > ");
                select = sc.next().trim();
                System.out.println("-".repeat(58));
            } else if (!select.equals("c")) {
                switch (select) {
                    case "1", "2": {
                        String inititalSelection = select;
                        var diff = "";
                        System.out.print("-".repeat(58) + "\n" + "1) Easy" + "\n" + "2) Medium - default" + "\n"
                                + "3) Hard" + "\n" + "4) CHIMPS" + "\n"
                                + "-Type 'back' to go back or type an option number-\n > ");
                        select = sc.next().trim();
                        System.out.println("-".repeat(58));
                        switch (select) {
                            case "1": {
                                diff = "Easy";
                                break;
                            }
                            case "2": {
                                diff = "Medium";
                                break;
                            }
                            case "3": {
                                diff = "Hard";
                                break;
                            }
                            case "4": {
                                diff = "CHIMPS";
                                break;
                            }
                            case "back": {
                                break;
                            }
                            default: {
                                System.out.println("Invalid option selected!");
                                break;
                            }
                        }
                        if (!diff.equals("") && inititalSelection.equals("1"))
                            Calculator.calculatePriceDifferenceTowers(diff);
                        else
                            Calculator.calculatePriceDifferenceUpgrades("Super Monkey", diff);
                        break;
                    }
                    case "3": {
                        String fileName = null;
                        var correct = false;
                        System.out.println("-".repeat(58));
                        while (!correct) {
                            System.out.print("""
                                    Input example: 'file-name.txt'
                                    File contents requirements: a double value on each line
                                    Input files (if) generated by this program that are accepted here:
                                    > 'output-[difficulty]-stripped.txt
                                    > 'output-[tower_name]-[difficulty]-stripped.txt
                                    > """);
                            fileName = sc.next().trim();
                            if (fileName.substring(fileName.length() - 5, fileName.length() - 1).equals(".txt")
                                    && fileName.length() > 4) {
                                correct = true;
                                System.out.println("-".repeat(58));
                                System.out.println(calculateAverage(new File(fileName)));
                                System.out.println("-".repeat(58));
                            } else {
                                System.out.println("Incorrect file name. Example: > output-Easy-stripped.txt");
                                break;
                            }
                        }
                        break;
                    }
                    case "exit": {
                        break;
                    }
                    default: {
                        System.out.println("Invalid option selected!");
                        break;
                    }
                }
                System.out.print("Type 'c' to continue!\n > ");
                select = sc.next().trim().equals(Calculator.RESET) ? Calculator.RESET : "c";
            } else if (!select.equals("exit") && promptForWrongInput) {
                System.out.println("Your inputs seem to be wrong. Do you agree?\n > ");
                select = sc.next().trim().toUpperCase();
                if (select.equals("YES") || select.equals("Y")) {
                    promptForWrongInput = false;
                    System.out.println("OK! You won't see this prompt again!");
                } else {
                    promptForWrongInput = true;
                    System.out.println("Sure!");
                }
                select = Calculator.RESET;
            } else
                select = Calculator.RESET;
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        sc.close();
    }

    /**
     * Transforms a double into a simplified fraction.
     *
     * @param arg0 a double value
     * @return an int array of 2 values: the numerator and the denominator
     */
    private static int[] findFraction(double arg0) {
        for (var i = 0; i < 10000; i++)
            for (var j = 1; j < 10000; j++)
                if ((double) i / (double) j == arg0)
                    return new int[] { i, j };
        return new int[] {};
    }

    /**
     * Parses trough a list and returns a new list containing only the original
     * values found.
     *
     * @param <T>  the type of the values in the list
     * @param list the list of values
     * @return a list with no duplicate values retrieved from the provided list
     */
    private static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
        ArrayList<T> newList = new ArrayList<T>();
        for (T element : list)
            if (!newList.contains(element))
                newList.add(element);
        return newList;
    }

    /**
     * Shows on the screen the cost of all the towers, one by one, and for each
     * tower waits for input of a new cost.
     * <p>
     * At the end of the execution, saves the results of 'new cost'/'tower cost' for
     * each tower in two separate files, one with detailed description, the other
     * with only the values, one on each line.
     *
     * @param difficulty the difficulty the original tower cost is based upon
     * @throws IOException if the results could not be saved in files
     */
    private static void calculatePriceDifferenceTowers(String difficulty) throws IOException {
        System.out.println("-".repeat(58));
        ArrayList<Tower[]> towers = new ArrayList<>(Tower.getDefaultTowers().values());
        ArrayList<Double> result = new ArrayList<>(0);
        result.add(0, 0d); // 'baseCost' aux cariable
        result.add(1, 0d); // 'cost' aux variable
        int initialSize = 0, finalSize = 0;
        for (Tower[] types : towers) {
            for (Tower t : types) {
                result.add(0, (double) t.getCost()); // update 'baseCost'
                System.out.println(t.getName() + ": cost=" + result.get(0)); // print 'baseCost'
                System.out.print(difficulty + ": cost="); // wait for new 'cost' input
                result.add(0, Double.parseDouble(sc.next())); // save input in 'cost'
                result.add(result.get(1) / result.get(0)); // calculate 'cost'/'baseCost' and save result
            }
        }
        result.remove(0); // remove baseCost aux variable
        result.remove(0); // remove cost aux variable
        initialSize = result.size(); // get the number of calculated items
        result = Calculator.removeDuplicates(result); // remove duplicate items
        finalSize = result.size(); // get the number of unique results
        System.out.println("-".repeat(58));
        System.out.println("Entries tested = " + initialSize);
        System.out.println("Unique results = " + finalSize);
        System.out.println("-".repeat(58));
        var fwr = new FileWriter(new File("output-" + difficulty + ".txt"));
        var bwr = new BufferedWriter(fwr);
        String output = null;
        for (Double d : result) {
            int[] elements = findFraction(d);
            output = elements[0] + "/" + elements[1] + " = " + d + "\n";
            System.out.print(output);
            bwr.write(output);
            bwr.newLine();
            bwr.flush();
        }
        System.out.println("-".repeat(58));
        bwr.close();
        fwr.close();
        fwr = new FileWriter(new File("output-" + difficulty + "-stripped.txt"));
        bwr = new BufferedWriter(fwr);
        for (Double d : result) {
            output = Double.toString(d);
            bwr.write(output);
            bwr.newLine();
            bwr.flush();
        }
        bwr.close();
        fwr.close();
    }

    /**
     * Shows on the screen the cost of all the upgrades, one by one, and for each
     * upgrade waits for input of a new cost.
     * <p>
     * At the end of the execution, saves the results of 'new cost'/'upgrade cost'
     * for each upgrade in two separate files, one with detailed description, the
     * other with only the values, one on each line.
     *
     * @param towerName  the tower the upgrades apply to
     * @param difficulty the difficulty the original upgrade cost is based upon
     * @throws IOException if the results could not be saved in files
     */
    private static void calculatePriceDifferenceUpgrades(String towerName, String difficulty) throws Exception {
        System.out.println("-".repeat(58));
        ArrayList<Upgrade[]> upgrades = new ArrayList<>(
                Upgrade.getUpgradesByTowerName(towerName, Upgrade.getDefaultUpgrades()));
        ArrayList<Double> result = new ArrayList<>();
        int initialSize, finalSize;
        double baseCost, cost;
        System.out.println("Upgrades for " + towerName + ": ");
        for (Upgrade[] path : upgrades) {
            for (Upgrade tier : path) {
                baseCost = (double) tier.getCost();
                System.out.println(tier.getName() + ": cost=" + baseCost);
                System.out.print(difficulty + ": cost=");
                cost = Double.parseDouble(sc.next());
                result.add(cost / baseCost);
            }
        }
        initialSize = result.size();
        result = removeDuplicates(result);
        finalSize = result.size();
        System.out.println("-".repeat(58));
        System.out.println("Entries tested = " + initialSize);
        System.out.println("Unique results = " + finalSize);
        System.out.println("-".repeat(58));
        var fwr = new FileWriter(new File("output-" + towerName + "-" + difficulty + ".txt"));
        var bwr = new BufferedWriter(fwr);
        String output;
        for (Double d : result) {
            int[] elements = findFraction(d);
            output = elements[0] + "/" + elements[1] + " = " + d + "\n";
            System.out.print(output);
            bwr.write(output);
            bwr.newLine();
            bwr.flush();
        }
        System.out.println("-".repeat(58));
        bwr.close();
        fwr.close();
        fwr = new FileWriter(new File("output-" + towerName + "-" + difficulty + "-stripped.txt"));
        bwr = new BufferedWriter(fwr);
        for (Double d : result) {
            output = Double.toString(d);
            bwr.write(output);
            bwr.newLine();
            bwr.flush();
        }
        bwr.close();
        fwr.close();
    }

    /**
     * Prompts for a file name input and if a valid file is found, calculates tha
     * average of all the decimal values found. The file should contain only one
     * value on each line, with no preceding or trailing whitespaces.
     *
     * @param input the file where the values can be found at
     * @return a double value representing the average of the decimal numbers found
     *         in the provided file
     * @throws IOException if file cannot be found or accessed
     */
    private static double calculateAverage(File input) throws IOException {
        var fr = new FileReader(input);
        var br = new BufferedReader(fr);
        ArrayList<Double> entries = new ArrayList<>();
        String line;
        var end = false;
        while (!end) {
            line = br.readLine();
            if (line == null)
                end = true;
            else
                entries.add(Double.parseDouble(line));
        }
        br.close();
        fr.close();
        var sum = 0d;
        int count = entries.size();
        for (double entry : entries)
            sum += entry;
        return sum / (double) count;
    }
}
