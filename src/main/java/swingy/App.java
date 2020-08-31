package swingy;

import org.graalvm.compiler.lir.RedundantMoveElimination;
import swingy.controller.Engine;
import swingy.model.Artifact;
import swingy.model.GameData;
import swingy.view.Renderer;

import java.io.IOException;

/**
 * Hello world!
 *
*/
public class App {
    public static void main( String[] args ) throws IOException {
        new Engine().init();
    }
}
