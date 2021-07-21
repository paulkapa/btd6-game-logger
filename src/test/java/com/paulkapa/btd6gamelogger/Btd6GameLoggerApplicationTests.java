package com.paulkapa.btd6gamelogger;

import com.paulkapa.btd6gamelogger.database.game.GameContainer;
import com.paulkapa.btd6gamelogger.models.game.Map;
import com.paulkapa.btd6gamelogger.models.game.Tower;
import com.paulkapa.btd6gamelogger.models.game.Upgrade;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Btd6GameLoggerApplicationTests {

    @Test
    void contextLoads() throws Exception {
        assert GameContainer.getDefaultMaps() != null : "Could not initiate test case";
        // Testing read default models from storage
        System.out.println(GameContainer.getDefaultMaps().getClass()
                + "----------------------------------------------------------------------------------------");
        System.out.println(GameContainer.getDefaultTowers().getClass()
                + "----------------------------------------------------------------------------------------");
        System.out.println(GameContainer.getDefaultUpgrades().getClass()
                + "----------------------------------------------------------------------------------------");

        // Testing get by criteria methods
        System.out.println("-----------------------------------------------getMapByName(----------------------------");
        try {
            // test error
            System.out.println(Map.getMapByName("unknown_map", GameContainer.getDefaultMaps()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------getMapByName(----------------------------");
        try {
            // test success
            System.out.println(Map.getMapByName("#ouch", GameContainer.getDefaultMaps()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------getTowerByName(--------------------------");
        try {
            // test error
            System.out.println(Tower.getTowerByName("unknown_tower", GameContainer.getDefaultTowers()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------getTowerByName(--------------------------");
        try {
            // test success
            System.out.println(Tower.getTowerByName("Super Monkey", GameContainer.getDefaultTowers()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------getTowerWithHighestSellValue(------------");
        try {
            // possibly only error
            System.out.println(Tower.getTowerWithHighestSellValue(GameContainer.getDefaultTowers()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------getTowerWithMostCashGenerated(-----------");
        try {
            // possibly only error
            System.out.println(Tower.getTowerWithMostCashGenerated(GameContainer.getDefaultTowers()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------getTowerWithMostPops(--------------------");
        try {
            // definetely only error
            System.out.println(Tower.getTowerWithMostPops(GameContainer.getDefaultTowers()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------getTowersByCost(-------------------------");
        try {
            // test error
            System.out.println(Tower.getTowersByCost(0, 0, GameContainer.getDefaultTowers()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------getTowersByCost(-------------------------");
        try {
            // test success
            System.out.println(Tower.getTowersByCost(0, 500, GameContainer.getDefaultTowers()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------getTowersByType(-------------------------");
        try {
            // test error
            System.out
                    .println(Tower.getTowersByType("unknown_tower_type", GameContainer.getDefaultTowers()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------getTowersByType(-------------------------");
        try {
            // test success
            System.out.println(Tower.getTowersByType("Hero", GameContainer.getDefaultTowers()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------getTowersNames(--------------------------");
        try {
            // test success
            System.out.println(Tower.getTowersNames(GameContainer.getDefaultTowers()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------countAppliedUpgrades(--------------------");
        try {
            // possibly only error
            System.out.println(Upgrade.countAppliedUpgrades(GameContainer.getDefaultUpgrades()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------countUnlockedUpgrades(-------------------");
        try {
            // possibly only error
            System.out.println(Upgrade.countUnlockedUpgrades(GameContainer.getDefaultUpgrades()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------getUpgradesByTowerName(------------------");
        try {
            // test error
            System.out.println(
                    Upgrade.getUpgradesByTowerName("unknown_tower", GameContainer.getDefaultUpgrades()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------getUpgradesByTowerName(------------------");
        try {
            // test success
            System.out.println(
                    Upgrade.getUpgradesByTowerName("Super Monkey", GameContainer.getDefaultUpgrades()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("----------------------------------------------------------------------------------------");
    }

}
