package com.hugomage.doyoubelieve.registry;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.common.block.BigFeetCarpet;
import com.hugomage.doyoubelieve.common.block.BulletinBoardBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DYBBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DoYouBelieve.MOD_ID);

    public static final RegistryObject<BigFeetCarpet> BIGFOOT_TRACKS = BLOCKS.register("bigfoot_tracks", BigFeetCarpet::new);
    public static final RegistryObject<BulletinBoardBlock> BULLETIN_BOARD = BLOCKS.register("bulletin_board", BulletinBoardBlock::new);
}
