package com.exorath.game.api.particles.particleTypes;

import com.exorath.game.api.particles.ParticleType;
import com.exorath.game.lib.particles.ParticleEffectType;
import org.bukkit.Location;

/**
 * Created by TOON on 8/30/2015.
 */
public class SimpleParticle extends ParticleType {
    public static final SimpleParticle EXPLOSION_NORMAL = new SimpleParticle(ParticleEffectType.EXPLOSION_NORMAL);
    public static final SimpleParticle EXPLOSION_LARGE = new SimpleParticle(ParticleEffectType.EXPLOSION_LARGE);
    public static final SimpleParticle EXPLOSION_HUGE = new SimpleParticle(ParticleEffectType.EXPLOSION_HUGE);
    public static final SimpleParticle FIREWORKS_SPARK = new SimpleParticle(ParticleEffectType.FIREWORKS_SPARK);
    public static final SimpleParticle WATER_BUBBLE = new SimpleParticle(ParticleEffectType.WATER_BUBBLE);
    public static final SimpleParticle WATER_SPLASH = new SimpleParticle(ParticleEffectType.WATER_SPLASH);
    public static final SimpleParticle WATER_WAKE = new SimpleParticle(ParticleEffectType.WATER_WAKE);
    public static final SimpleParticle SUSPENDED = new SimpleParticle(ParticleEffectType.SUSPENDED);
    public static final SimpleParticle SUSPENDED_DEPTH = new SimpleParticle(ParticleEffectType.SUSPENDED_DEPTH);
    public static final SimpleParticle CRIT = new SimpleParticle(ParticleEffectType.CRIT);
    public static final SimpleParticle CRIT_MAGIC = new SimpleParticle(ParticleEffectType.CRIT_MAGIC);
    public static final SimpleParticle SMOKE_NORMAL = new SimpleParticle(ParticleEffectType.SMOKE_NORMAL);
    public static final SimpleParticle SMOKE_LARGE = new SimpleParticle(ParticleEffectType.SMOKE_LARGE);
    public static final SimpleParticle SPELL = new SimpleParticle(ParticleEffectType.SPELL);
    public static final SimpleParticle SPELL_INSTANT = new SimpleParticle(ParticleEffectType.SPELL_INSTANT);
    public static final SimpleParticle SPELL_MOB = new SimpleParticle(ParticleEffectType.SPELL_MOB);
    public static final SimpleParticle SPELL_MOB_AMBIENT = new SimpleParticle(ParticleEffectType.SPELL_MOB_AMBIENT);
    public static final SimpleParticle SPELL_WITCH = new SimpleParticle(ParticleEffectType.SPELL_WITCH);
    public static final SimpleParticle DRIP_WATER = new SimpleParticle(ParticleEffectType.DRIP_WATER);
    public static final SimpleParticle DRIP_LAVA = new SimpleParticle(ParticleEffectType.DRIP_LAVA);
    public static final SimpleParticle VILLAGER_ANGRY = new SimpleParticle(ParticleEffectType.VILLAGER_ANGRY);
    public static final SimpleParticle VILLAGER_HAPPY = new SimpleParticle(ParticleEffectType.VILLAGER_HAPPY);
    public static final SimpleParticle TOWN_AURA = new SimpleParticle(ParticleEffectType.TOWN_AURA);
    public static final SimpleParticle NOTE = new SimpleParticle(ParticleEffectType.NOTE);
    public static final SimpleParticle PORTAL = new SimpleParticle(ParticleEffectType.PORTAL);
    public static final SimpleParticle ENCHANTMENT_TABLE = new SimpleParticle(ParticleEffectType.ENCHANTMENT_TABLE);
    public static final SimpleParticle FLAME = new SimpleParticle(ParticleEffectType.FLAME);
    public static final SimpleParticle LAVA = new SimpleParticle(ParticleEffectType.LAVA);
    public static final SimpleParticle FOOTSTEP = new SimpleParticle(ParticleEffectType.FOOTSTEP);
    public static final SimpleParticle CLOUD = new SimpleParticle(ParticleEffectType.CLOUD);
    public static final SimpleParticle REDSTONE = new SimpleParticle(ParticleEffectType.REDSTONE);
    public static final SimpleParticle SNOWBALL = new SimpleParticle(ParticleEffectType.SNOWBALL);
    public static final SimpleParticle SNOW_SHOVEL = new SimpleParticle(ParticleEffectType.SNOW_SHOVEL);
    public static final SimpleParticle SLIME = new SimpleParticle(ParticleEffectType.SLIME);
    public static final SimpleParticle HEART = new SimpleParticle(ParticleEffectType.HEART);
    public static final SimpleParticle BARRIER = new SimpleParticle(ParticleEffectType.BARRIER);
    public static final SimpleParticle ITEM_CRACK = new SimpleParticle(ParticleEffectType.ITEM_CRACK);
    public static final SimpleParticle BLOCK_CRACK = new SimpleParticle(ParticleEffectType.BLOCK_CRACK);
    public static final SimpleParticle BLOCK_DUST = new SimpleParticle(ParticleEffectType.BLOCK_DUST);
    public static final SimpleParticle WATER_DROP = new SimpleParticle(ParticleEffectType.WATER_DROP);
    public static final SimpleParticle ITEM_TAKE = new SimpleParticle(ParticleEffectType.ITEM_TAKE);
    public static final SimpleParticle MOB_APPEARANCE = new SimpleParticle(ParticleEffectType.MOB_APPEARANCE);

    private ParticleEffectType effectType;

    private float offsetX = 0;
    private float offsetY = 0;
    private float offsetZ = 0;

    private float speed = 1;
    private int amount = 1;
    private double range = 64;
    public SimpleParticle(ParticleEffectType effectType){
        this.effectType = effectType;
    }
    public SimpleParticle(ParticleEffectType effectType, float offsetX, float offsetY, float offsetZ, float speed, int amount){
        this.effectType = effectType;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.amount = amount;
    }
    public SimpleParticle(ParticleEffectType effectType, float offsetX, float offsetY, float offsetZ, float speed, int amount, double range){
        this.effectType = effectType;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.amount = amount;
        this.range = range;
    }
    @Override
    public void display(Location loc) {
        effectType.display(offsetX, offsetY,offsetZ, speed, amount, loc, range);
    }
}
