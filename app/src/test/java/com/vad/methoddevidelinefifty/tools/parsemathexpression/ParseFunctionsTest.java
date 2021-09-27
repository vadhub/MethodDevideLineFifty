package com.vad.methoddevidelinefifty.tools.parsemathexpression;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class ParseFunctionsTest {

    @Test
    void lexAnalise_sum() {
        List<Lexeme> lexemes = ParseFunctions.lexAnalise("2+2");

        Lexeme expect = lexemes.get(0);
        assertEquals(LexemeType.NUMBER, expect.lexemeType);
        assertEquals("2", expect.value);

        expect = lexemes.get(1);
        assertEquals(LexemeType.OP_PLUS, expect.lexemeType);
        assertEquals("+", expect.value);

        expect = lexemes.get(2);
        assertEquals(LexemeType.NUMBER, expect.lexemeType);
        assertEquals("2", expect.value);
    }

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
    void parseExpression_functions() {
        ParseFunctions parseFunctions = new ParseFunctions();
        float expect = parseFunctions.parseExpression("pow(2,2)");
        assertEquals(4, expect);

        expect = parseFunctions.parseExpression("sin(60)");
        assertEquals((float)Math.sin(Math.toRadians(60)), expect);

        expect = parseFunctions.parseExpression("ln(10)");
        assertEquals((float)Math.log(10), expect);
    }
}