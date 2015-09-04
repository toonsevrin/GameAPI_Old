package com.exorath.game.api.hud.locations;

import com.exorath.game.api.hud.HUDDisplay;
import com.exorath.game.api.hud.HUDText;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.lib.hud.title.TitleBase;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by TOON on 8/11/2015.
 * SYSTEM FLAW: Can't fade away Title and Subtitle together: One will be removed, other will fade
 */
public abstract class Title_SubTitleBase extends HUDDisplay {
    Title_SubTitleBase otherLocation;

    public Title_SubTitleBase(GamePlayer player, int maxChars) {
        super(player, maxChars);
    }

    @Override
    public void displayText(HUDText text) {
        if (otherLocation.getCurrentText() != null) {//There is text on the other location
            send(text);
        } else {//Other location empty
            sendWithFadeIn(text);
        }
    }

    @Override
    public void removeCurrent() {
        if (otherLocation.getCurrentText() != null) {//There is text on the other location
            removeSelf();
        } else {//Other location empty
            TitleBase.sendTimings(player.getBukkitPlayer(), 0, 0, 40);
        }
    }

    //** Methods for implementation **//
    public abstract void sendWithFadeIn(HUDText text);

    public abstract void send(HUDText text);

    public abstract void removeSelf();

    public static String getJSON(String title) {
        char colorChar = ChatColor.COLOR_CHAR;

        String template = "{text:\"TEXT\",color:COLOR,bold:BOLD,underlined:UNDERLINED,italic:ITALIC,strikethrough:STRIKETHROUGH,obfuscated:OBFUSCATED,extra:[EXTRA]}";
        String json = "";

        java.util.List<String> parts = new ArrayList<String>();

        int first = 0;
        int last = 0;

        while ((first = title.indexOf(colorChar, last)) != -1) {
            int offset = 2;
            while ((last = title.indexOf(colorChar, first + offset)) - 2 == first)
                offset += 2;

            if (last == -1) {
                parts.add(title.substring(first));
                break;
            }
            parts.add(title.substring(first, last));
        }

        if (parts.isEmpty())
            parts.add(title);

        Pattern colorFinder = Pattern.compile("(" + colorChar + "([a-f0-9]))");
        for (String part : parts) {
            json = json.isEmpty() ? template : json.replace("EXTRA", template);

            Matcher matcher = colorFinder.matcher(part);
            ChatColor color = matcher.find() ? ChatColor.getByChar(matcher.group().charAt(1)) : ChatColor.WHITE;

            json = json.replace("COLOR", color.name().toLowerCase());
            json = json.replace("BOLD", String.valueOf(part.contains(ChatColor.BOLD.toString())));
            json = json.replace("ITALIC", String.valueOf(part.contains(ChatColor.ITALIC.toString())));
            json = json.replace("UNDERLINED", String.valueOf(part.contains(ChatColor.UNDERLINE.toString())));
            json = json.replace("STRIKETHROUGH", String.valueOf(part.contains(ChatColor.STRIKETHROUGH.toString())));
            json = json.replace("OBFUSCATED", String.valueOf(part.contains(ChatColor.MAGIC.toString())));

            json = json.replace("TEXT", part.replaceAll("(" + colorChar + "([a-z0-9]))", ""));
        }

        json = json.replace(",extra:[EXTRA]", "");

        return json;
    }
}
