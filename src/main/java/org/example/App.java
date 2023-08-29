package org.example;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final ConsoleGenerator generator = new ConsoleGenerator();
    public static void main( String[] args ) throws IOException {
        generator.generate(40, 226);
    }
}
