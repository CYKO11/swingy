package swingy.view;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import com.sun.source.tree.BreakTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Renderer {
    public String terminalRender(String out, String[] options, int inputCheckToggle) throws IOException {
        System.out.println(out);
        System.out.print(">>> ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String in = reader.readLine();
        int pos = 0;
        while (pos < options.length){
            if (inputCheckToggle == 0) {
                if (in.length() < 2){
                    System.out.println("\n INPUT TOO SHORT");
                    return terminalRender(out, options, inputCheckToggle);
                }
                return in;
            }
            else if (in.equals(options[pos])) {
                final String s = in.toLowerCase();
                return s;
            }
            pos++;
        }
        System.out.println("\n INVALID INPUT");
        return terminalRender(out, options, inputCheckToggle);
    }
}