package org.example;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Game game = new Game();
    public static void main( String[] args ) {
        game.start(40, 226);
    }
}
