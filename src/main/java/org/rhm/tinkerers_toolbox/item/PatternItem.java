package org.rhm.tinkerers_toolbox.item;

import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

// Maybe needed later idk;
public class PatternItem extends Item {
    public PatternItem(Settings settings) {
        super(settings
                .maxCount(1) // ik im lazy
                .rarity(Rarity.UNCOMMON)
        );
    }
}
