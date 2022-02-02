package com.hugomage.doyoubelieve.common.item;

import com.hugomage.doyoubelieve.DoYouBelieve;
import net.minecraft.item.Item;

public class DYBClueItem extends Item {
    private final DYBClues clueData;

    public DYBClueItem(DYBClues clueID) {
        super(new Properties().tab(DoYouBelieve.clueGroup));
        this.clueData = clueID;
    }

    public DYBClues getClueData() {
        return clueData;
    }
}
