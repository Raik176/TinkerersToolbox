package org.rhm.tinkerers_toolbox.item;

import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Rarity;

public class CustomPickaxeItem extends PickaxeItem {
    public CustomPickaxeItem(ToolMaterial material, Item.Settings settings) {
        super(material, settings
            .maxCount(1)
            .rarity(Rarity.UNCOMMON)
        );
    }
}
