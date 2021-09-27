package com.vad.methoddevidelinefifty.tools.parsemathexpression;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ParseFunctionsTest {

    @Test
    void parseExpression_plus() {
        ParseFunctions parseFunctions = new ParseFunctions();
        float expect = parseFunctions.parseExpression("1+2");
        assertEquals(3, expect);
    }

    @Test
    void parseExpression_minus() {
        ParseFunctions parseFunctions = new ParseFunctions();
        float expect = parseFunctions.parseExpression("1-2");
        assertEquals(-1, expect);
    }

    @Test
    void parseExpression_sub() {
        ParseFunctions parseFunctions = new ParseFunctions();
        float expect = parseFunctions.parseExpression("3*2");
        assertEquals(6, expect);
    }

    @Test
    void parseExpression_div() {
        ParseFunctions parseFunctions = new ParseFunctions();
        float expect = parseFunctions.parseExpression("6/2");
        assertEquals(3, expect);
    }

    @Test
    void parseExpression_bracket_opr() {
        ParseFunctions parseFunctions = new ParseFunctions();
        float expect = parseFunctions.parseExpression("2*(1+2)");
        assertEquals(6, expect);
    }

    @Test
    void parseExpression_doubleBracket() {
        ParseFunctions parseFunctions = new ParseFunctions();
        float expect = parseFunctions.parseExpression("2*(1+2*(1-2))");
        assertEquals(-2, expect);
    }

    @Test
    void lexAnalise() {
    }
}