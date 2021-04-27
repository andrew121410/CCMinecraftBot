package com.andrew121410.mc.ccminecraftbot.utils;

import com.andrew121410.mc.ccminecraftbot.objects.Item;

import java.util.Locale;

public class Blocks {

    public static boolean isFood(int id) {
        Item item = ResourceManager.INSTANCE.getItems().get(id);
        if (item == null) return false;
        switch (item.getDisplayName().toLowerCase(Locale.ROOT)) {
            case "potato":
            case "baked potato":
            case "steak":
            case "cooked chicken":
            case "cooked porkchop":
            case "golden apple":
            case "enchanted golden apple":
                return true;
            default:
                return false;
        }
    }
}
