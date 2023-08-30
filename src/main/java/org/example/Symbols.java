package org.example;

public enum Symbols {
    TANK('%'),
    ENEMY_TANK('T'),

    GROUND('='),
    BALLISTIC_CURVE('*'),

    EMPTY_CHAR(' '),

    HIT_AREA('X'),;


    final char symbol;

    Symbols(char symbol) {
        this.symbol = symbol;
    }

}
