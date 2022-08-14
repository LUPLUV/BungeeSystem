package de.lupu.system.utils;

public enum Color {

    RED,
    GOLD,
    BLUE,
    YELLOW,
    GREEN,
    LIME,
    PURPLE,
    LIGHT_BLUE;

    public String toColorCode(){
        switch (this){

            case RED: return "§c";
            case GOLD: return "§6";
            case BLUE: return "§9";
            case YELLOW: return "§e";
            case GREEN: return "§2";
            case LIME: return "§a";
            case PURPLE: return "§5";
            case LIGHT_BLUE: return "§b";
        }
        return null;
    }

}
