package swingy.view;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import com.sun.source.tree.BreakTree;
import swingy.controller.WorldGeneration;
import swingy.model.MapData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class TextRenderer {
    public String render(String out, String[] options, int inputCheckToggle) throws IOException {
        System.out.println(out);
        System.out.print(">>> ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String in = reader.readLine();
        int pos = 0;
        while (pos < options.length){
            if (inputCheckToggle == 0) {
                if (in.length() < 2){
                    System.out.println("\n INPUT TOO SHORT");
                    return render(out, options, inputCheckToggle);
                }
                return in;
            }
            else if (in.toLowerCase().equals(options[pos].toLowerCase())) {
                final String s = in.toLowerCase();
                return s;
            }
            pos++;
        }
        System.out.println("\n INVALID INPUT");
        return render(out, options, inputCheckToggle);
    }
    public void out(String in){
        System.out.println(in);
    }
    public void outAwait(String in) throws IOException {
        System.out.println(in);
        System.in.read();
    }
    public void renderMap(WorldGeneration world){
        int y = world.boundsY;
        int x = 0;
        while (y >= 0){
            x = 0;
            while (x <= world.boundsX){
                switch (getOccupants(x, y, world.exportWorld())){
                    case 0  : System.out.print("   "); break;
                    case 1  : System.out.print("X  "); break;
                    case 2  : System.out.print("H  "); break;
                }
                x++;
            }
            System.out.println("|");
            y--;
        }
    }
    private int getOccupants(int x, int y, List<MapData> mapData){
        for (MapData point : mapData){
            if (point.getCoords()[0] == x && point.getCoords()[1] == y){
                return point.getOccupant();
            }
        }
        return 0;
    }
}