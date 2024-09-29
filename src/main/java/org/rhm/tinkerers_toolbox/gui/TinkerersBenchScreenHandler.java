package org.rhm.tinkerers_toolbox.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class TinkerersBenchScreenHandler extends ScreenHandler {
    private final BlockPos blockPosition;
    private final Inventory inventory;
    private final Slot toolSlot;
    private final Slot outputSlot;
    private final Slot mineralSlot;

    public TinkerersBenchScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, null);
    }

    public TinkerersBenchScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos blockPosition) {
        super(ModScreens.TINKERERS_BENCH, syncId);
        this.blockPosition = blockPosition;
        this.inventory = new SimpleInventory(4);

        playerInventory.onOpen(playerInventory.player);

        toolSlot = new Slot(inventory, 1, 103, 36) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.streamTags().anyMatch((tag) -> tag.id().getPath().equals("tools"));
            }
        };
        outputSlot = new Slot(inventory, 3, 153, 36) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        };
        mineralSlot = new Slot(inventory, 0, 82, 23) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.streamTags().anyMatch((tag) -> tag.id().getPath().equals("mineral"));
            }
        };

        this.addSlot(mineralSlot);
        this.addSlot(toolSlot);
        this.addSlot(new Slot(inventory, 2, 82, 50));
        this.addSlot(outputSlot);

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

    public Slot getOutputSlot() {
        return outputSlot;
    }

    public Slot getMineralSlot() {
        return mineralSlot;
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

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);

        if (blockPosition != null) {
            for (int i = 0; i < inventory.size(); i++) {
                ItemStack stack = inventory.getStack(i);
                if (stack != null) player.dropItem(stack, true);
            }
        }
    }
}