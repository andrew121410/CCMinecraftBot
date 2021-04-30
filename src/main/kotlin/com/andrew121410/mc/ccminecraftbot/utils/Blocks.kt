package com.andrew121410.mc.ccminecraftbot.utils;

import com.andrew121410.mc.ccminecraftbot.objects.Item;

import java.util.Locale;

public class Blocks {

    public static boolean isFood(int id) {
        Item item = ResourceManager.INSTANCE.getItems().get(id);
        if (item == null) return false;
        String displayName = item.getDisplayName().toLowerCase(Locale.ROOT);
        switch (displayName) {
            case "enchanted golden apple":
            case "golden apple":
            case "golden carrot":
            case "cooked mutton":
            case "cooked porkchop":
            case "cooked salmon":
            case "steak":
            case "baked potato":
            case "bread":
            case "cooked chicken":
            case "melon":
            case "cooked cod":
                return true;
            default:
                return false;
        }
    }
}
