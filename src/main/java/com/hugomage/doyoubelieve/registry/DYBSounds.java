package com.hugomage.doyoubelieve.registry;

import com.hugomage.doyoubelieve.DoYouBelieve;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DYBSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DoYouBelieve.MOD_ID);

    public static final RegistryObject<SoundEvent> BIGFOOT_AMBIENT = SOUNDS.register("entity.bigfoot.ambient",
            () -> new SoundEvent(new ResourceLocation(DoYouBelieve.MOD_ID, "entity.bigfoot.ambient")));
    public static final RegistryObject<SoundEvent> BIGFOOT_HURT = SOUNDS.register("entity.bigfoot.hurt",
            () -> new SoundEvent(new ResourceLocation(DoYouBelieve.MOD_ID, "entity.bigfoot.hurt")));
    public static final RegistryObject<SoundEvent> BIGFOOT_DEATH = SOUNDS.register("entity.bigfoot.death",
            () -> new SoundEvent(new ResourceLocation(DoYouBelieve.MOD_ID, "entity.bigfoot.death")));
    public static final RegistryObject<SoundEvent> BIGFOOT_ATTACK = SOUNDS.register("entity.bigfoot.attack",
            () -> new SoundEvent(new ResourceLocation(DoYouBelieve.MOD_ID, "entity.bigfoot.attack")));
}
