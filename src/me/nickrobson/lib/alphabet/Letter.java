package me.nickrobson.lib.alphabet;

import me.nickrobson.lib.reflect.Reflection;

/**
 * @author Nick Robson
 */
public class Letter {

    public static final int MAX_WIDTH = 5;
    public static final int MAX_HEIGHT = 7;

    private int[][] bits;

    private Letter(int[][] bits) {
        this.bits = bits;
    }

    public int getWidth() {
        int len = 0;
        for (int i = 0; i < bits.length; i++)
            len = Math.max(len, bits[i].length);
        return len == 0 ? MAX_WIDTH : len;
    }

    public int getHeight() {
        return bits.length;
    }

    public boolean has(int row, int col) {
        try {
            return bits[row][col] == 1;
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
    }

    public static final Letter getLetter(char c) {
        c = Character.toUpperCase(c);
        String s = "" + c;
        if (c >= '0' && c <= '9')
            s = "N_" + c;
        if (c == ' ')
            return SPACE;
        if (c == '?')
            return QUESTION_MARK;
        if (c == '!')
            return EXCLAMATION_MARK;
        if (c == '.')
            return FULL_STOP;
        if (c == ',')
            return COMMA;
        if (c == '\'')
            return SINGLE_QUOTE;
        if (c == '"')
            return DOUBLE_QUOTE;
        if (c == ':')
            return COLON;
        if (c == ';')
            return SEMICOLON;
        if (c == '$')
            return DOLLAR_SIGN;
        if (c == '#')
            return HASH;
        if (c == '%')
            return PERCENT;
        if (c == '^')
            return CARET;
        if (c == '(')
            return LEFT_PARENTHESIS;
        if (c == ')')
            return RIGHT_PARENTHESIS;
        if (c == '[')
            return LEFT_BRACKET;
        if (c == ']')
            return RIGHT_BRACKET;
        if (c == '{')
            return LEFT_BRACE;
        if (c == '}')
            return RIGHT_BRACE;
        if (c == '<')
            return LEFT_ANGLE;
        if (c == '>')
            return RIGHT_ANGLE;
        if (c == '=')
            return EQUALS;
        if (c == '+')
            return PLUS;
        if (c == '-')
            return MINUS;
        if (c == '_')
            return UNDERSCORE;
        if (c == '/')
            return FORWARD_SLASH;
        if (c == '\\')
            return BACKWARD_SLASH;
        if (c == '|')
            return VERTICAL_BAR;
        if (c == '*')
            return ASTERISK;
        try {
            return (Letter) Reflection.getField(Letter.class, s).get(null).getObject();
        } catch (Exception ex) {}
        return null;
    }

    /**
     * @formatter:off
     */

    public static final Letter A = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 1, 0, 0, 0, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 0, 0, 0, 1 },
                { 1, 0, 0, 0, 1 },
            }
            );

    public static final Letter B = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 0 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 1, 1, 0 },
            }
            );

    public static final Letter C = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 0 },
                { 1, 1, 0, 0, 0 },
                { 1, 1, 0, 0, 0 },
                { 1, 1, 1, 1, 1 },
            }
            );

    public static final Letter D = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 0 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 1, 1, 0 },
            }
            );

    public static final Letter E = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 0 },
                { 1, 1, 1, 1, 0 },
                { 1, 1, 0, 0, 0 },
                { 1, 1, 1, 1, 1 },
            }
            );

    public static final Letter F = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 0 },
                { 1, 1, 1, 1, 0 },
                { 1, 1, 0, 0, 0 },
                { 1, 1, 0, 0, 0 },
            }
            );

    public static final Letter G = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 0 },
                { 1, 1, 0, 1, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 1, 1, 1 },
            }
            );

    public static final Letter H = new Letter(
            new int[][] {
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 0, 1 },
            }
            );

    public static final Letter I = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 0, 0, 1, 0, 0 },
                { 0, 0, 1, 0, 0 },
                { 0, 0, 1, 0, 0 },
                { 1, 1, 1, 1, 1 },
            }
            );

    public static final Letter J = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 0, 0, 1, 1, 0 },
                { 0, 0, 1, 1, 0 },
                { 1, 0, 1, 1, 0 },
                { 0, 1, 1, 0, 0 },
            }
            );

    public static final Letter K = new Letter(
            new int[][] {
                { 1, 1, 0, 1, 1 },
                { 1, 1, 1, 1, 0 },
                { 1, 1, 1, 0, 0 },
                { 1, 1, 1, 1, 0 },
                { 1, 1, 0, 1, 1 },
            }
            );

    public static final Letter L = new Letter(
            new int[][] {
                { 1, 1, 0, 0, 0 },
                { 1, 1, 0, 0, 0 },
                { 1, 1, 0, 0, 0 },
                { 1, 1, 0, 0, 0 },
                { 1, 1, 1, 1, 1 },
            }
            );

    public static final Letter M = new Letter(
            new int[][] {
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 0, 1 },
            }
            );

    public static final Letter N = new Letter(
            new int[][] {
                { 1, 1, 0, 0, 1 },
                { 1, 1, 1, 0, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 1, 1 },
                { 1, 1, 0, 0, 1 },
            }
            );

    public static final Letter O = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 1, 1, 1 },
            }
            );

    public static final Letter P = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 0 },
                { 1, 1, 0, 0, 0 },
            }
            );

    public static final Letter Q = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 1, 1, 1 },
                { 0, 0, 0, 1, 0 },
            }
            );

    public static final Letter R = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 1, 1, 0 },
                { 1, 1, 0, 1, 1 },
                { 1, 1, 0, 0, 1 },
            }
            );

    public static final Letter S = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 1, 1, 1 },
                { 0, 0, 0, 1, 1 },
                { 1, 1, 1, 1, 1 },
            }
            );

    public static final Letter T = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 0, 0, 1, 0, 0 },
                { 0, 0, 1, 0, 0 },
                { 0, 0, 1, 0, 0 },
                { 0, 0, 1, 0, 0 },
            }
            );

    public static final Letter U = new Letter(
            new int[][] {
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 1, 1, 1 },
            }
            );

    public static final Letter V = new Letter(
            new int[][] {
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 1, 1 },
                { 0, 1, 1, 1, 0 },
            }
            );

    public static final Letter W = new Letter(
            new int[][] {
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 1, 0, 1 },
                { 0, 1, 0, 1, 0 },
            }
            );
    public static final Letter X = new Letter(
            new int[][] {
                { 1, 0, 0, 0, 1 },
                { 1, 1, 0, 1, 0 },
                { 0, 1, 1, 0, 0 },
                { 1, 1, 0, 1, 0 },
                { 1, 0, 0, 0, 1 },
            }
            );

    public static final Letter Y = new Letter(
            new int[][] {
                { 1, 0, 0, 0, 1 },
                { 1, 1, 0, 1, 0 },
                { 0, 1, 1, 0, 0 },
                { 0, 0, 1, 0, 0 },
                { 0, 0, 1, 0, 0 },
            }
            );
    public static final Letter Z = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 0, 0, 1, 1, 0 },
                { 0, 1, 1, 0, 0 },
                { 1, 1, 0, 0, 0 },
                { 1, 1, 1, 1, 1 },
            }
            );

    public static final Letter N_0 = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 0, 1, 0, 1 },
                { 1, 0, 0, 1, 1 },
                { 1, 1, 1, 1, 1 },
            }
            );

    public static final Letter N_1 = new Letter(
            new int[][] {
                { 0, 0, 1, 1, 0 },
                { 0, 1, 1, 1, 0 },
                { 0, 0, 1, 1, 0 },
                { 0, 0, 1, 1, 0 },
                { 0, 1, 1, 1, 1 },
            }
            );

    public static final Letter N_2 = new Letter(
            new int[][] {
                { 0, 1, 1, 1, 0 },
                { 1, 1, 1, 1, 1 },
                { 1, 0, 0, 1, 1 },
                { 0, 0, 1, 1, 0 },
                { 1, 1, 1, 1, 1 },
            }
            );

    public static final Letter N_3 = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 0, 0, 0, 1, 1 },
                { 0, 1, 1, 1, 1 },
                { 0, 0, 0, 1, 1 },
                { 1, 1, 1, 1, 1 },
            }
            );

    public static final Letter N_4 = new Letter(
            new int[][] {
                { 1, 1, 0, 1, 1 },
                { 1, 1, 0, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 0, 0, 0, 1, 1 },
                { 0, 0, 0, 1, 1 },
            }
            );

    public static final Letter N_5 = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 0 },
                { 1, 1, 1, 1, 0 },
                { 0, 0, 0, 1, 1 },
                { 1, 1, 1, 1, 0 },
            }
            );

    public static final Letter N_6 = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 0 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 1, 1, 1 },
            }
            );

    public static final Letter N_7 = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 0, 0, 0, 1, 1 },
                { 0, 0, 1, 1, 1 },
                { 0, 0, 0, 1, 1 },
                { 0, 0, 0, 1, 1 },
            }
            );

    public static final Letter N_8 = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 1 },
                { 1, 1, 1, 1, 1 },
            }
            );

    public static final Letter N_9 = new Letter(
            new int[][] {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 0, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 0, 0, 0, 1, 1 },
                { 0, 0, 0, 1, 1 },
            }
            );

    public static final Letter SPACE = new Letter(
            new int[][] {
                { 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0 },
            }
            );

    public static final Letter EXCLAMATION_MARK = new Letter(
            new int[][] {
                { 1, 1 },
                { 1, 1 },
                { 1, 1 },
                { 0, 0 },
                { 1, 1 },
            }
            );

    public static final Letter QUESTION_MARK = new Letter(
            new int[][] {
                { 0, 1, 1, 0 },
                { 1, 0, 0, 1 },
                { 0, 0, 1, 0 },
                { 0, 0, 0, 0 },
                { 0, 0, 1, 0 },
            }
            );

    public static final Letter FULL_STOP = new Letter(
            new int[][] {
                { 0 },
                { 0 },
                { 0 },
                { 0 },
                { 1 },
            }
            );

    public static final Letter COMMA = new Letter(
            new int[][] {
                { 0, 0 },
                { 0, 0 },
                { 0, 0 },
                { 0, 0 },
                { 0, 1 },
                { 1, 0 },
            }
            );

    public static final Letter ASTERISK = new Letter(
            new int[][] {
                { 0 },
                { 1 },
                { 0 },
                { 0 },
                { 0 },
            }
            );

    public static final Letter SINGLE_QUOTE = new Letter(
            new int[][] {
                { 1 },
                { 0 },
                { 0 },
                { 0 },
                { 0 },
            }
            );

    public static final Letter DOUBLE_QUOTE = new Letter(
            new int[][] {
                { 1, 0, 1 },
                { 0, 0, 0 },
                { 0, 0, 0 },
                { 0, 0, 0 },
                { 0, 0, 0 },
            }
            );

    public static final Letter COLON = new Letter(
            new int[][] {
                { 0 },
                { 1 },
                { 0 },
                { 1 },
                { 0 },
            }
            );

    public static final Letter SEMICOLON = new Letter(
            new int[][] {
                { 0, 0 },
                { 0, 1 },
                { 0, 0 },
                { 0, 1 },
                { 1, 0 },
            }
            );

    public static final Letter DOLLAR_SIGN = new Letter(
            new int[][] {
                { 0, 0, 1, 0, 0 },
                { 1, 1, 1, 1, 1 },
                { 1, 0, 1, 0, 1 },
                { 1, 1, 1, 1, 1 },
                { 0, 0, 1, 0, 1 },
                { 1, 1, 1, 1, 1 },
                { 0, 0, 1, 0, 0 },
            }
            );

    public static final Letter HASH = new Letter(
            new int[][] {
                { 0, 1, 0, 1, 0 },
                { 1, 1, 1, 1, 1 },
                { 0, 1, 0, 1, 0 },
                { 1, 1, 1, 1, 1 },
                { 0, 1, 0, 1, 0 },
            }
            );

    public static final Letter PERCENT = new Letter(
            new int[][] {
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 1, 0 },
                { 0, 1, 1, 0, 0 },
                { 0, 1, 0, 1, 1 },
                { 1, 0, 0, 1, 1 },
            }
            );

    public static final Letter CARET = new Letter(
            new int[][] {
                { 0, 1, 0 },
                { 1, 0, 1 },
                { 0, 0, 0 },
                { 0, 0, 0 },
                { 0, 0, 0 },
            }
            );

    public static final Letter LEFT_PARENTHESIS = new Letter(
            new int[][] {
                { 0, 1 },
                { 1, 0 },
                { 1, 0 },
                { 1, 0 },
                { 0, 1 },
            }
            );

    public static final Letter RIGHT_PARENTHESIS = new Letter(
            new int[][] {
                { 1, 0 },
                { 0, 1 },
                { 0, 1 },
                { 0, 1 },
                { 1, 0 },
            }
            );

    public static final Letter LEFT_BRACKET = new Letter(
            new int[][] {
                { 1, 1 },
                { 1, 0 },
                { 1, 0 },
                { 1, 0 },
                { 1, 1 },
            }
            );

    public static final Letter RIGHT_BRACKET = new Letter(
            new int[][] {
                { 1, 1 },
                { 0, 1 },
                { 0, 1 },
                { 0, 1 },
                { 1, 1 },
            }
            );

    public static final Letter LEFT_BRACE = new Letter(
            new int[][] {
                { 0, 0, 1 },
                { 0, 1, 0 },
                { 1, 1, 0 },
                { 0, 1, 0 },
                { 0, 0, 1 },
            }
            );

    public static final Letter RIGHT_BRACE = new Letter(
            new int[][] {
                { 1, 0, 0 },
                { 0, 1, 0 },
                { 0, 1, 1 },
                { 0, 1, 0 },
                { 1, 0, 0 },
            }
            );

    public static final Letter LEFT_ANGLE = new Letter(
            new int[][] {
                { 0, 0, 0, 0 },
                { 0, 0, 1, 0 },
                { 0, 1, 0, 0 },
                { 0, 0, 1, 0 },
                { 0, 0, 0, 0 },
            }
            );

    public static final Letter RIGHT_ANGLE = new Letter(
            new int[][] {
                { 0, 0, 0, 0 },
                { 0, 1, 0, 0 },
                { 0, 0, 1, 0 },
                { 0, 1, 0, 0 },
                { 0, 0, 0, 0 },
            }
            );

    public static final Letter EQUALS = new Letter(
            new int[][] {
                { 0, 0 },
                { 1, 1 },
                { 0, 0 },
                { 1, 1 },
            }
            );

    public static final Letter PLUS = new Letter(
            new int[][] {
                { 0, 0, 0 },
                { 0, 1, 0 },
                { 1, 1, 1 },
                { 0, 1, 0 },
            }
            );

    public static final Letter MINUS = new Letter(
            new int[][] {
                { 0, 0 },
                { 0, 0 },
                { 1, 1 },
                { 0, 0 },
            }
            );

    public static final Letter UNDERSCORE = new Letter(
            new int[][] {
                { 0, 0 },
                { 0, 0 },
                { 0, 0 },
                { 0, 0 },
                { 0, 0 },
                { 1, 1 },
            }
            );

    public static final Letter FORWARD_SLASH = new Letter(
            new int[][] {
                { 0, 0, 1 },
                { 0, 0, 1 },
                { 0, 1, 0 },
                { 0, 1, 0 },
                { 1, 0, 0 },
                { 1, 0, 0 },
            }
            );

    public static final Letter BACKWARD_SLASH = new Letter(
            new int[][] {
                { 1, 0, 0 },
                { 1, 0, 0 },
                { 0, 1, 0 },
                { 0, 1, 0 },
                { 0, 0, 1 },
                { 0, 0, 1 },
            }
            );

    public static final Letter VERTICAL_BAR = new Letter(
            new int[][] {
                { 0, 1, 0 },
                { 0, 1, 0 },
                { 0, 1, 0 },
                { 0, 1, 0 },
                { 0, 1, 0 },
                { 0, 1, 0 },
            }
            );

}
