package swingy;

import swingy.model.Artifact;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Artifact drop = Artifact.getArtifact();
        System.out.println(drop.getType());
        System.out.println(drop.getName() + ", rarity :" + drop.getRarity());
        System.out.println("armour :" + drop.getArmour());
        System.out.println("attack :" + drop.getDamage());
        System.out.println("hp :" + drop.getHp());
    }
}
