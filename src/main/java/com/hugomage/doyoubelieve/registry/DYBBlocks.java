package com.hugomage.doyoubelieve.registry;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.common.block.BigFeetCarpet;
import com.hugomage.doyoubelieve.common.block.BulletinBoardBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DYBBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DoYouBelieve.MOD_ID);

    public static final RegistryObject<BigFeetCarpet> BIGFOOT_TRACKS = BLOCKS.register("bigfoot_tracks", BigFeetCarpet::new);
    public static final RegistryObject<BulletinBoardBlock> BULLETIN_BOARD = BLOCKS.register("bulletin_board", () -> new BulletinBoardBlock(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(3.0F)));
}
