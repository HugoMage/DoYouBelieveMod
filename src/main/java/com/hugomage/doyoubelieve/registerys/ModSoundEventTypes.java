package com.hugomage.doyoubelieve.registerys;

import com.hugomage.doyoubelieve.DoYouBelieve;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSoundEventTypes
{
 public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DoYouBelieve.MOD_ID);

 public static final RegistryObject<SoundEvent> BIGFOOT_AMBIENT = SOUND_EVENTS.register("entity.bigfoot.ambient", () ->
         new SoundEvent(new ResourceLocation(DoYouBelieve.MOD_ID, "entity.bigfoot.ambient"))
 );
 public static final RegistryObject<SoundEvent> BIGFOOT_HURT = SOUND_EVENTS.register("entity.bigfoot.hurt", () ->
         new SoundEvent(new ResourceLocation(DoYouBelieve.MOD_ID, "entity.bigfoot.hurt"))
 );
 public static final RegistryObject<SoundEvent> BIGFOOT_DEATH = SOUND_EVENTS.register("entity.bigfoot.death", () ->
         new SoundEvent(new ResourceLocation(DoYouBelieve.MOD_ID, "entity.bigfoot.death"))
 );
 public static final RegistryObject<SoundEvent> BIGFOOT_ATTACK = SOUND_EVENTS.register("entity.bigfoot.attack", () ->
         new SoundEvent(new ResourceLocation(DoYouBelieve.MOD_ID, "entity.bigfoot.attack"))
 );







}
