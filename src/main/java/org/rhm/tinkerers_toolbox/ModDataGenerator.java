package org.rhm.tinkerers_toolbox;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.rhm.tinkerers_toolbox.block.ModBlocks;
import org.rhm.tinkerers_toolbox.item.ModItems;
import org.rhm.tinkerers_toolbox.item.PatternItem;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ModDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModelProvider::new);
        pack.addProvider(BlockLootTableProvider::new);
        pack.addProvider(RecipeProvider::new);

        //Tags
        pack.addProvider(ItemTagProvider::new);
        pack.addProvider(BlockTagProvider::new);

        //Languages
        pack.addProvider((dataOutput, registryLookup) -> new LanguageProvider(dataOutput, "en_us", registryLookup) {
            @Override
            public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder builder) {
                super.generateTranslations(wrapperLookup, builder);
                builder.add(ModBlocks.TINKERERS_BENCH, "Tinkerer's Bench");
                builder.add(ItemTagProvider.MINERAL_TAG, "Mineral");
                builder.add(ItemTagProvider.PATTERN_TAG, "Tinker Pattern");
                builder.add(ModMain.MOD_ID + ".itemGroup", "Tinker's Toolbox");
                builder.add(ModMain.MOD_ID + ".gui.tool_placeholder", "Any Tool");
                builder.add(ModMain.MOD_ID + ".gui.mineral_placeholder", "Any Mineral");
            }
        });
    }


    public static class RecipeProvider extends FabricRecipeProvider {
        public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        public void generate(RecipeExporter exporter) {
            ModItems.getItems().stream().filter(i -> i instanceof PatternItem)
                    .forEach(i -> ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, i)
                            .pattern("AAA").pattern(" B ").pattern("CDC")
                            .input('A', Items.STICK).input('B', Items.FLINT)
                            .input('C', Items.IRON_INGOT).input('D', Items.OBSIDIAN)
                            .criterion(FabricRecipeProvider.hasItem(Items.OBSIDIAN),
                                    FabricRecipeProvider.conditionsFromItem(Items.OBSIDIAN))
                            .offerTo(exporter));
            ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.TINKERERS_BENCH)
                    .pattern("AAA").pattern("BCB").pattern("DED")
                    .input('A', ItemTags.PLANKS).input('B', Items.OBSIDIAN)
                    .input('C', Items.CRAFTING_TABLE).input('D', ItemTags.STONE_BRICKS)
                    .input('E', Items.GOLD_INGOT)
                    .criterion(FabricRecipeProvider.hasItem(Items.OBSIDIAN),
                            FabricRecipeProvider.conditionsFromItem(Items.OBSIDIAN))
                    .offerTo(exporter);
        }
    }

    public static class BlockTagProvider extends FabricTagProvider<Block> {
        private static final RegistryKey<Registry<Block>> BLOCK_REGISTRY = RegistryKey.ofRegistry(Identifier.of("block"));

        public BlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, BLOCK_REGISTRY, registriesFuture);
        }

        protected static TagKey<Block> createTag(String name) {
            return TagKey.of(BLOCK_REGISTRY, Identifier.of(ModMain.MOD_ID, name));
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                    .add(ModBlocks.TINKERERS_BENCH)
                    .setReplace(false);
            getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                    .add(ModBlocks.TINKERERS_BENCH)
                    .setReplace(false);
        }
    }

    public static class ItemTagProvider extends FabricTagProvider<Item> {
        private static final RegistryKey<Registry<Item>> ITEM_REGISTRY = RegistryKey.ofRegistry(Identifier.of("item"));

        public static final TagKey<Item> MINERAL_TAG = createTag("mineral");
        public static final TagKey<Item> PATTERN_TAG = createTag("tinker_pattern");

        public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, ITEM_REGISTRY, registriesFuture);
        }

        protected static TagKey<Item> createTag(String name) {
            return TagKey.of(ITEM_REGISTRY, Identifier.of(ModMain.MOD_ID, name));
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(MINERAL_TAG)
                    .addOptionalTag(ItemTags.COALS)
                    .addOptionalTag(ItemTags.TRIM_MATERIALS)
                    .add(Items.COPPER_INGOT);
            getOrCreateTagBuilder(PATTERN_TAG)
                    .add(ModItems.TEST_TEMPLATE);
        }
    }

    private static class ModelProvider extends FabricModelProvider {
        public ModelProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
            blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TINKERERS_BENCH);
        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        }
    }

    private abstract static class LanguageProvider extends FabricLanguageProvider {
        public final String LANGUAGE_CODE;

        protected LanguageProvider(FabricDataOutput dataOutput, String language, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, language, registryLookup);
            LANGUAGE_CODE = language;
        }

        @Override
        public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder builder) {
            Optional<Path> path = dataOutput.getModContainer().findPath("assets/" + ModMain.MOD_ID + "/lang/" + LANGUAGE_CODE + ".existing.json");
            if (path.isPresent()) {
                try {
                    builder.add(path.get());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static class BlockLootTableProvider extends FabricBlockLootTableProvider {
        protected BlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            /*
            This looks really weird (and even more so when generated)
            but it works (I think) so DON'T TOUCH IT
             */
            addDrop(ModBlocks.TINKERERS_BENCH, drops(ModBlocks.TINKERERS_BENCH,MatchToolLootCondition.builder(ItemPredicate.Builder
                    .create()
                    .items(
                            Items.STONE_PICKAXE,
                            Items.IRON_PICKAXE,
                            Items.DIAMOND_PICKAXE,
                            Items.NETHERITE_PICKAXE
                    )), ItemEntry.builder(Blocks.AIR)));
        }
    }
}