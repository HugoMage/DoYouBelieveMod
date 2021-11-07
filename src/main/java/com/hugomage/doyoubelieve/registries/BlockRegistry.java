package com.hugomage.doyoubelieve.registries;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.block.BigFeetCarpet;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DoYouBelieve.MOD_ID);

    public static final RegistryObject<BigFeetCarpet> BIGFOOT_TRACKS = BLOCKS.register("bigfoot_tracks", BigFeetCarpet::new);
}
