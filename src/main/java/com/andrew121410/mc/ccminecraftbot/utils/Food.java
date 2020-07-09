package com.andrew121410.mc.ccminecraftbot.utils;

public class Food {

    public static boolean isFood(int id) {
        switch (id) {
            case 739: //Raw beef
            case 740: //Cooked beef
            case 741: //Raw chicken
            case 742: //Cooked chicken

            case 829: //Carrot
            case 830: //Potato
            case 831: //Baked potato
            case 834: //Golden carrot
                return true;
        }
        return false;
    }
}
