package dev.huyghe.morseflashlight;

import java.util.ArrayList;
import java.util.List;

public class MorseCharacter {
    public enum MorseImpulse {
        Short, Long, Space
    }

    private MorseImpulse[] impulses;
    private char character;

    public static List<MorseCharacter> listFromString(String s) {
        ArrayList<MorseCharacter> list = new ArrayList<>();
        for (int i = 0; i < s.length(); ++i) {
            list.add(MorseCharacter.newFromChar(s.charAt(i)));
        }
        return list;
    }

    public static MorseCharacter newFromChar(char c) {
        c = Character.toUpperCase(c);
        MorseImpulse[] impulses;
        switch (c) {
            case ' ':
                impulses = new MorseImpulse[]{MorseImpulse.Space};
                break;
            case 'A':
                impulses = new MorseImpulse[]{MorseImpulse.Short, MorseImpulse.Long};
                break;
            case 'B':
                impulses = new MorseImpulse[]{MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short};
                break;
            case 'C':
                impulses = new MorseImpulse[]{MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Short};
                break;
            case 'D':
                impulses = new MorseImpulse[]{MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Short};
                break;
            case 'E':
                impulses = new MorseImpulse[]{MorseImpulse.Short};
                break;
            case 'F':
                impulses = new MorseImpulse[]{MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Short};
                break;
            case 'G':
                impulses = new MorseImpulse[]{MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Short};
                break;
            case 'H':
                impulses = new MorseImpulse[]{MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short};
                break;
            case 'I':
                impulses = new MorseImpulse[]{MorseImpulse.Short, MorseImpulse.Short};
                break;
            case 'J':
                impulses = new MorseImpulse[]{MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long};
                break;
            case 'K':
                impulses = new MorseImpulse[]{MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Long};
                break;
            case 'L':
                impulses = new MorseImpulse[]{MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Short};
                break;
            case 'M':
                impulses = new MorseImpulse[]{MorseImpulse.Long, MorseImpulse.Long};
                break;
            case 'N':
                impulses = new MorseImpulse[]{MorseImpulse.Long, MorseImpulse.Short};
                break;
            case 'O':
                impulses = new MorseImpulse[]{MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long};
                break;
            case 'P':
                impulses = new MorseImpulse[]{MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Short};
                break;
            case 'Q':
                impulses = new MorseImpulse[]{MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Long};
                break;
            case 'R':
                impulses = new MorseImpulse[]{MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Short};
                break;
            case 'S':
                impulses = new MorseImpulse[]{MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short};
                break;
            case 'T':
                impulses = new MorseImpulse[]{MorseImpulse.Long};
                break;
            case 'U':
                impulses = new MorseImpulse[]{MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Long};
                break;
            case 'V':
                impulses = new MorseImpulse[]{MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Long};
                break;
            case 'W':
                impulses = new MorseImpulse[]{MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Long};
                break;
            case 'X':
                impulses = new MorseImpulse[]{MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Long};
                break;
            case 'Y':
                impulses = new MorseImpulse[]{MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Long};
                break;
            case 'Z':
                impulses = new MorseImpulse[]{MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Short};
                break;
            case '1':
                impulses = new MorseImpulse[]{MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long};
                break;
            case '2':
                impulses = new MorseImpulse[]{MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long};
                break;
            case '3':
                impulses = new MorseImpulse[]{MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Long, MorseImpulse.Long};
                break;
            case '4':
                impulses = new MorseImpulse[]{MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Long};
                break;
            case '5':
                impulses = new MorseImpulse[]{MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short};
                break;
            case '6':
                impulses = new MorseImpulse[]{MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short};
                break;
            case '7':
                impulses = new MorseImpulse[]{MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Short, MorseImpulse.Short};
                break;
            case '8':
                impulses = new MorseImpulse[]{MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Short, MorseImpulse.Short};
                break;
            case '9':
                impulses = new MorseImpulse[]{MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Short};
                break;
            case '0':
                impulses = new MorseImpulse[]{MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long, MorseImpulse.Long};
                break;
            default:
                throw new IllegalArgumentException("char should match regex /[a-z]|[A-Z]|[0-9]/");
        }
        return new MorseCharacter(impulses, c);
    }

    public static boolean charIsValid(char c) {
        return c >= 'A' && c <= 'Z' && c >= 'a' && c <= 'z' && c >= '0' && c <= '9';
    }

    private MorseCharacter(MorseImpulse[] is, char c) {
        impulses = is;
        character = c;
    }

    public MorseImpulse[] getImpulses() {
        return impulses;
    }

    public char getCharacter() {
        return character;
    }
}
