package com.exorath.game.api.countdown;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.regex.Matcher;

import com.exorath.game.api.Callback;
import com.exorath.game.api.SoundInfo;
import com.exorath.game.api.hud.HUDPriority;
import com.exorath.game.api.hud.HUDText;
import com.exorath.game.api.player.GamePlayer;
import com.google.common.collect.Lists;

import me.nickrobson.lib.util.RegexUtil;

/**
 * @author Nick Robson
 */
public class CountdownFrameBuilder {

    /**
     * @author Nick Robson
     */
    private class FrameInvocationHandler implements InvocationHandler {

        private List<Class<? extends CountdownFrame>> clazzs;

        private FrameInvocationHandler(List<Class<? extends CountdownFrame>> clazzs) {
            this.clazzs = clazzs == null ? Lists.newArrayList() : clazzs;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (args == null)
                args = new Object[0];
            boolean ok = false;
            for (Class<?> c : clazzs)
                if (c.getMethod(method.getName(), method.getParameterTypes()) != null)
                    ok = true;
            if (!ok) {
                StringBuilder str = new StringBuilder();
                clazzs.forEach(c -> str.append((str.length() == 0 ? "" : ", ") + c.getName()));
                throw new NoSuchMethodException("There is no " + method.getName() + "() in [" + str.toString() + "]");
            }
            if (method.getName().equals("getDelay") && args.length == 0)
                return delay;
            else if (method.getName().equals("getDuration") && args.length == 0)
                return duration;
            else if (method.getName().equals("getSound") && args.length == 0) {
                if (sound == null)
                    throw new java.lang.NoSuchMethodException("This CountdownFrame does not support Sound.");
                return sound.getSound();
            } else if (method.getName().equals("getPitch") && args.length == 0) {
                if (sound == null)
                    throw new java.lang.NoSuchMethodException("This CountdownFrame does not support Sound.");
                return sound.getPitch();
            } else if (method.getName().equals("getVolume") && args.length == 0) {
                if (sound == null)
                    throw new java.lang.NoSuchMethodException("This CountdownFrame does not support Sound.");
                return sound.getVolume();
            } else if (method.getName().equals("getTitle") && args.length == 0) {
                if (title == null)
                    throw new java.lang.NoSuchMethodException("This CountdownFrame does not support Titles.");
                return title;
            } else if (method.getName().equals("getSubtitle") && args.length == 0) {
                if (subtitle == null)
                    throw new java.lang.NoSuchMethodException("This CountdownFrame does not support Subtitles.");
                return subtitle;
            } else if (method.getName().equals("start") && args.length == 1 && method.getParameterTypes()[0] == GamePlayer.class) {
                if (start != null)
                    start.run(args[0] == null ? null : (GamePlayer) args[0]);
                return null;
            } else if (method.getName().equals("display") && args.length == 1 && method.getParameterTypes()[0] == GamePlayer.class) {
                if (args[0] != null) {
                    GamePlayer gp = (GamePlayer) args[0];
                    if (display != null)
                        display.run(gp);
                    if (title != null)
                        if (gp.getHud().getTitle().containsText("gapi.countdown.title"))
                            gp.getHud().getTitle().getText("gapi.countdown.title").setText(title);
                        else
                            gp.getHud().getTitle().addText("gapi.countdown.title", new HUDText(title, HUDPriority.GAME_API.get()));
                    if (subtitle != null)
                        if (gp.getHud().getSubtitle().containsText("gapi.countdown.subtitle"))
                            gp.getHud().getSubtitle().getText("gapi.countdown.subtitle").setText(subtitle);
                        else
                            gp.getHud().getSubtitle().addText("gapi.countdown.subtitle", new HUDText(subtitle, HUDPriority.GAME_API.get()));
                    if(sound != null)
                        gp.getBukkitPlayer().playSound(gp.getBukkitPlayer().getLocation(), sound.getSound(), sound.getVolume(), sound.getPitch());

                }
                return null;
            } else if (method.getName().equals("end") && args.length == 1 && method.getParameterTypes()[0] == GamePlayer.class) {
                if (end != null)
                    end.run(args[0] == null ? null : (GamePlayer) args[0]);
                return null;
            }
            return null;
        }

    }

    public static CountdownFrameBuilder newBuilder() {
        return new CountdownFrameBuilder();
    }

    public static CountdownFrame blank(long duration) {
        return newBuilder().duration(duration).build();
    }

    private CountdownFrameBuilder() {
    }

    private Long delay, duration;
    private SoundInfo sound;
    private String title, subtitle;
    private Callback<GamePlayer> start, display, end;

    {
        sound = null;
        delay = duration = 0l;
        title = subtitle = null;
        start = display = end = null;
    }

    public CountdownFrameBuilder delay(long delay) {
        this.delay = delay;
        return this;
    }

    public CountdownFrameBuilder duration(long duration) {
        this.duration = duration;
        return this;
    }

    public CountdownFrameBuilder title(String title) {
        this.title = title;
        return this;
    }

    public CountdownFrameBuilder subtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public CountdownFrameBuilder sound(SoundInfo sound) {
        this.sound = sound;
        return this;
    }

    public CountdownFrameBuilder start(Callback<GamePlayer> start) {
        this.start = start;
        return this;
    }

    public CountdownFrameBuilder display(Callback<GamePlayer> display) {
        this.display = display;
        return this;
    }

    public CountdownFrameBuilder end(Callback<GamePlayer> end) {
        this.end = end;
        return this;
    }

    public CountdownFrame build() {
        return build(CountdownFrame.class);
    }

    @SuppressWarnings("unchecked")
    public <T extends CountdownFrame> T build(Class<T> clazz) {
        List<Class<? extends CountdownFrame>> clazzs = Lists.newArrayList();
        if (title != null)
            clazzs.add(TitleCountdownFrame.class);
        if (subtitle != null)
            clazzs.add(SubtitleCountdownFrame.class);
        if (sound != null)
            clazzs.add(SoundCountdownFrame.class);
        if (clazzs.isEmpty())
            clazzs.add(CountdownFrame.class);
        if (clazz != CountdownFrame.class && !clazzs.stream().filter(c -> clazz.isAssignableFrom(c)).findAny().isPresent()) {
            Matcher match = RegexUtil.UPPERCASE_LETTERS.matcher(clazz.getSimpleName());
            StringBuilder str = new StringBuilder(clazz.getSimpleName());
            int offset = 0;
            while (match.find())
                if (match.start() > 0)
                    str.insert(match.start() + offset++, " ");
            throw new IllegalArgumentException("CountdownFrame cannot be built as a " + str.toString());
        }
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), clazzs.toArray(new Class<?>[clazzs.size()]),
                new FrameInvocationHandler(clazzs));
    }

}
