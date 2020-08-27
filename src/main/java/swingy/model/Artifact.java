package swingy.model;


public class Artifact {
    private static Artifact artifact = new Artifact();
    private static String type;
    private static String name;
    private static int damage;
    private static int hp;
    private static int armour;

    Artifact(){
        String[] drop = Item.item().getDrop();
        name = drop[0];
        damage = Integer.parseInt(drop[1]);
        armour = Integer.parseInt(drop[2]);
        hp = Integer.parseInt(drop[3]);
        type = drop[5];
    }

    public static Artifact getArtifact(){
        return (artifact);
    }
    public String getName(){
        return name;
    }
    public int getDamage(){
        return damage;
    }
    public int getArmour(){
        return armour;
    }
    public int getHp(){
        return hp;
    }
    public String getType(){
        return type;
    }
}

class Item {
    private static Item item = new Item();
    private static String[][] items = {
            // name , attack , armour , hp , rarity , class
            {"gunblade","100","0","0","5","weapon"},
            {"shotgun","100","0","0","5","weapon"},
            {"meth","32","0","-20","2", "weapon"},
            {"ludens echo","13","0","0","2","weapon"},
            {"kevins heart","16","0","13","2","weapon"},
            {"wooden stick","9","0","0","1","weapon"},
            {"Armour1","0","10","0","3","armour"},
            {"Armour2","0","14","0","1","armour"},
            {"helm1","0","0","16","3","helm"},
            {"helm2","0","0","4","2","helm"}
    };
    public static Item item(){
        return (item);
    }
    public String[] getDrop() {
        int rarity = rollRarity();
        while (countWeapons(rarity) == 0){
            rarity = rollRarity();
        }
        int weapon = rollItem(rarity);
        return items[weapon];
    }

    public int rollItem(int rarity) {
        int[] drops = new int[countWeapons(rarity)];
        int len = items.length - 1;
        int dropPos = 0;
        while (len >= 0){
            if (Integer.parseInt(items[len][4]) == rarity) {
                drops[dropPos] = len;
                dropPos++;
            }
            len--;
        }
        return drops[(int) (Math.random() * ((drops.length - 1) + 1))];
    }

    public int rollRarity(){
        // r5 1% chance
        // r4 5% chance
        // r3 14% chance
        // r2 30% chance
        // r1 50% chance
        double chance = Math.random();
        if (chance <= 0.01) return 5;
        if (chance <= 0.06) return 4;
        if (chance <= 0.2) return 3;
        if (chance <= 0.5) return 2;
        return 1;
    }
    public int countWeapons(int rarity){
        int len = items.length - 1;
        int weapons = 0;
        while (len >= 0){
            if (Integer.parseInt(items[len][4]) == rarity)
                weapons++;
            len--;
        }
        return weapons;
    }
}
