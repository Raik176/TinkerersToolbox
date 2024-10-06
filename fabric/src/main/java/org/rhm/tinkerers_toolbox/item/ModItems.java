package org.rhm.tinkerers_toolbox.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.rhm.tinkerers_toolbox.ModMain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ModItems {
    private static List<Item> modItems = new ArrayList<>();

    public static final Item SHAFT_PATTERN = register(
        new PatternItem(new Item.Settings()),
        "shaft_pattern"
    );
    public static final Item BINDING_PATTERN = register(
        new PatternItem(new Item.Settings()),
        "binding_pattern"
    );
    public static final Item HEAD_PATTERN = register(
        new PatternItem(new Item.Settings()),
        "head_pattern"
    );

    public static List<Item> getItems() {
        return modItems;
    }

    public static Item register(Item item, String name) {
        modItems.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(ModMain.MOD_ID, name), item);
    }

    public static void initialize() { // Im lazy
        modItems = Collections.unmodifiableList(modItems.reversed());
        ItemGroupEvents.modifyEntriesEvent(ModMain.ITEM_GROUP_KEY).register(itemGroup ->
            itemGroup.addAll(modItems.stream().map(Item::getDefaultStack).collect(Collectors.toList())));
    }
}
