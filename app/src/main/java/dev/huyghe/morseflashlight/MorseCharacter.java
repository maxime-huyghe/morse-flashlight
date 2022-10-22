package dev.huyghe.morseflashlight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MorseCharacter {
    public enum MorseImpulse {
        Short, Long, Space
    }

    private final List<MorseImpulse> impulses;
    private final char character;

    public static MorseCharacter newFromChar(char c) {
        c = Character.toUpperCase(c);
        List<MorseImpulse> impulses;
        switch (c) {
            case ' ':
                impulses = Collections.singletonList(MorseImpulse.Space);
                break;
            case 'A':
                impulses = Arrays.asList(MorseImpulse.Short, MorseImpulse.Long);
                break;
            case 'B':
                impulses = Arrays.asList(MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short);
                break;
            case 'C':
                impulses = Arrays.asList(MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Short);
                break;
            case 'D':
                impulses = Arrays.asList(MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Short);
                break;
            case 'E':
                impulses = Collections.singletonList(MorseImpulse.Short);
                break;
            case 'F':
                impulses = Arrays.asList(MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Short);
                break;
            case 'G':
                impulses = Arrays.asList(MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Short);
                break;
            case 'H':
                impulses = Arrays.asList(MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short);
                break;
            case 'I':
                impulses = Arrays.asList(MorseImpulse.Short, MorseImpulse.Short);
                break;
            case 'J':
                impulses = Arrays.asList(MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long);
                break;
            case 'K':
                impulses = Arrays.asList(MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Long);
                break;
            case 'L':
                impulses = Arrays.asList(MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Short);
                break;
            case 'M':
                impulses = Arrays.asList(MorseImpulse.Long, MorseImpulse.Long);
                break;
            case 'N':
                impulses = Arrays.asList(MorseImpulse.Long, MorseImpulse.Short);
                break;
            case 'O':
                impulses = Arrays.asList(MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long);
                break;
            case 'P':
                impulses = Arrays.asList(MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Short);
                break;
            case 'Q':
                impulses = Arrays.asList(MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Long);
                break;
            case 'R':
                impulses = Arrays.asList(MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Short);
                break;
            case 'S':
                impulses = Arrays.asList(MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short);
                break;
            case 'T':
                impulses = Collections.singletonList(MorseImpulse.Long);
                break;
            case 'U':
                impulses = Arrays.asList(MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Long);
                break;
            case 'V':
                impulses = Arrays.asList(MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Long);
                break;
            case 'W':
                impulses = Arrays.asList(MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Long);
                break;
            case 'X':
                impulses = Arrays.asList(MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Long);
                break;
            case 'Y':
                impulses = Arrays.asList(MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Long);
                break;
            case 'Z':
                impulses = Arrays.asList(MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Short);
                break;
            case '1':
                impulses = Arrays.asList(MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long);
                break;
            case '2':
                impulses = Arrays.asList(MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long);
                break;
            case '3':
                impulses = Arrays.asList(MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Long);
                break;
            case '4':
                impulses = Arrays.asList(MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Long);
                break;
            case '5':
                impulses = Arrays.asList(MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short);
                break;
            case '6':
                impulses = Arrays.asList(MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short);
                break;
            case '7':
                impulses = Arrays.asList(MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short);
                break;
            case '8':
                impulses = Arrays.asList(MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Short);
                break;
            case '9':
                impulses = Arrays.asList(MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Short);
                break;
            case '0':
                impulses = Arrays.asList(MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long);
                break;
            default:
                throw new IllegalArgumentException("char should match regex /[a-z]|[A-Z]|[0-9]/");
        }
        return new MorseCharacter(impulses, c);
    }

    public static boolean charIsValid(char c) {
        return c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9';
    }

    private MorseCharacter(List<MorseImpulse> is, char c) {
        impulses = is;
        character = c;
    }

    public List<MorseImpulse> getImpulses() {
        return impulses;
    }

    public char getCharacter() {
        return character;
    }
}
