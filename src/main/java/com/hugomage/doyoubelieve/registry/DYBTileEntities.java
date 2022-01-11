package com.hugomage.doyoubelieve.registry;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.common.tileentities.BulletinBoardTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DYBTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, DoYouBelieve.MOD_ID);

    public static final RegistryObject<TileEntityType<BulletinBoardTileEntity>> BULLETIN_BOARD = TILE_ENTITIES.register("bulletin_board", () -> TileEntityType.Builder.of(BulletinBoardTileEntity::new, DYBBlocks.BULLETIN_BOARD.get()).build(null));
}
