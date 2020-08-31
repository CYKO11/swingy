package swingy.model;


import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files

public class LoadGame {

    private List<Artifact>        backPack = new ArrayList<>();
    private List<Artifact>        equipped = new ArrayList<>();

    public void load() throws FileNotFoundException {
        ///Stats
        int Armour;
        int AtkDmg;
        int HP;
        int Lvl;
        int Xp;

        File myObj = new File("SavedGame.txt");
        Scanner myReader = new Scanner(myObj);
        String[] array;
        System.out.println("##### Load function #####");
        while (myReader.hasNextLine()){
            String data = myReader.nextLine();
            System.out.println("Reader: "+data);
            array = data.split(" ");
//            System.out.println(array[0]);
            if (array[0].equals("Armour")){
                Armour = Integer.parseInt(array[1]);
                System.out.println("set armor");
            }
            else if (array[0].equals("AtkDmg")){
                AtkDmg = Integer.parseInt(array[1]);
                System.out.println("set atk");
            }
            else if (array[0].equals("HP")){
                HP = Integer.parseInt(array[1]);
                System.out.println("set hp");
            }
            else if (array[0].equals("Lvl")){
                Lvl = Integer.parseInt(array[1]);
                System.out.println("set lv");
            }
            else if (array[0].equals("XP")){
                Xp = Integer.parseInt(array[1]);
                System.out.println("set xp");
            }
            else if (array[0].equals("Backpack")){
                System.out.println("came true");
                while (myReader.hasNextLine()){
                    data = myReader.nextLine();
                    System.out.println("Reader in while loop: "+data);
                    if (data.equals("equipment")){
                        break;
                    }
                    else {
                        this.backPack.add(new Artifact(data));
                    }
                }
                while (myReader.hasNextLine()){
                    data = myReader.nextLine();
                    System.out.println("Reader 2nd loop: "+data);
                    this.equipped.add(new Artifact(data));
                }
            }
        }
        myReader.close();
    }
    public LoadGame() throws FileNotFoundException {
    }
}