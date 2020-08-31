package swingy.model;

import java.io.File;  // Import the File class
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors
import java.lang.reflect.Array;

public class SaveGame {

    public void saveData(Hero ObjHero){
        try {
//        File myObj = new File("SavedGame.txt");
            int i = 7;
            FileWriter myWriter = new FileWriter("SavedGame.txt");
            String[] data = new String[56];

            data[0] = String.valueOf(ObjHero.getStats().getArmor());
            data[1] = String.valueOf(ObjHero.getStats().getAtkDmg());
            data[2] = String.valueOf(ObjHero.getStats().getHP());
            data[3] = String.valueOf(ObjHero.getStats().getLevel());
            data[4] = String.valueOf(ObjHero.getStats().getXp());
            data[5] = String.valueOf(ObjHero.getStats().getXpBar());
            data[5] = ObjHero.getName();
            data[6] = ObjHero.getHeroClass();

            data[i++] = "Backpack";
            if (!ObjHero.getBackPack().isEmpty()){
                for (String element : ObjHero.getBackPack()) {
                    System.out.println(element);
                    data[i++] = element;
                }
            }
            data[i++] = "equipment";
            if (!ObjHero.getEquipped().isEmpty()){
                for (String element : ObjHero.getEquipped()) {
                    System.out.println(element);
                    data[i++] = element;
                }
            }

            i = 0;
            System.out.println("checking here "+data[i]);
            while (data[i] != null){
                System.out.println(data[i]);
                myWriter.write(data[i]+"\n");
                i++;
            }
            myWriter.close();

        } catch (IOException e) {
            System.out.println("Well shit son");
            e.printStackTrace();
        }
    }

}
