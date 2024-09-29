package org.rhm.tinkerers_toolbox.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.rhm.tinkerers_toolbox.ModMain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModItems {
    private static final List<Item> modItems = new ArrayList<>();

    public static final Item TEST_TEMPLATE  = register(
            new Item(new Item.Settings()),
            "test_template"
    );

    public static Item register(Item item, String name) {
        modItems.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(ModMain.MOD_ID, name), item);
    }

    public static void initialize() { // Im lazy
        ItemGroupEvents.modifyEntriesEvent(ModMain.ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.addAll(modItems.stream().map(Item::getDefaultStack).collect(Collectors.toList()));
        });
    }
}
