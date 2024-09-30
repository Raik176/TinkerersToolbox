package org.rhm.tinkerers_toolbox.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import org.rhm.tinkerers_toolbox.ModDataGenerator;

public class TinkerersBenchScreenHandler extends ScreenHandler {
    private final ScreenHandlerContext context;
    private final Inventory inventory;
    private final Slot toolSlot;
    private final Slot outputSlot;
    private final Slot mineralSlot;
    private final Slot patternSlot;
    private final Slot recycleSlot;

    public TinkerersBenchScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, null);
    }

    public TinkerersBenchScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreens.TINKERERS_BENCH, syncId);
        this.context = context;
        this.inventory = new SimpleInventory(5) {
            @Override
            public void markDirty() {
                onContentChanged(this);
                super.markDirty();
            }
        };

        playerInventory.onOpen(playerInventory.player);

        toolSlot = new Slot(inventory, 1, 67, 36) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof ToolItem;
                //return stack.streamTags().anyMatch((tag) -> tag.id().getPath().equals("tools"));
            }
        };
        outputSlot = new Slot(inventory, 3, 117, 36) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                super.onTakeItem(player, stack);
                craftFinished();
            }
        };
        mineralSlot = new Slot(inventory, 0, 46, 23) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.streamTags().anyMatch((tag) -> tag.id().getPath().equals("mineral"));
            }
        };
        patternSlot = new Slot(inventory, 2, 46, 50) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.streamTags().anyMatch((tag) -> tag == ModDataGenerator.TagProvider.PATTERN_TAG);
            }
        };
        recycleSlot = new Slot(inventory, 4, 134, 55) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        };

        this.addSlot(mineralSlot);
        this.addSlot(toolSlot);
        this.addSlot(patternSlot);
        this.addSlot(outputSlot);
        this.addSlot(recycleSlot);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 89 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 147));
        }
    }

    public Slot getToolSlot() {
        return toolSlot;
    }

    public Slot getMineralSlot() {
        return mineralSlot;
    }

    public Slot getPatternSlot() {
        return patternSlot;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;

        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    public void craftFinished() {
        mineralSlot.takeStack(1);
        toolSlot.takeStack(1);
        outputSlot.takeStack(1);
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        if (context != null) {
            context.run((world, blockPos) -> {
                if (mineralSlot.hasStack() && toolSlot.hasStack() && patternSlot.hasStack() && !outputSlot.hasStack()) {
                    ItemStack tool = toolSlot.getStack();
                    ItemStack mineral = mineralSlot.getStack();
                    ItemStack pattern = patternSlot.getStack();
                    ItemStack result = tool.copy();

                    //yummy hardcoding


                    // result.set(ModComponents.TEST_COMPONENT, Registries.ITEM.getId(mineral.getItem()).toString());

                    outputSlot.setStack(result);
                }
                if ((!mineralSlot.hasStack() || !toolSlot.hasStack())) {
                    if (outputSlot.hasStack()) outputSlot.setStack(ItemStack.EMPTY);
                    if (recycleSlot.hasStack()) recycleSlot.setStack(ItemStack.EMPTY);
                }
            });
        }
        /*
        super.onContentChanged(inventory);
        context.run((world, blockPos) -> {

        });
         */
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);

        for (int i = 0; i < inventory.size() - 2; i++) {
            ItemStack stack = inventory.getStack(i);
            if (!player.giveItemStack(stack)) player.dropItem(stack, false);
        }
    }
}