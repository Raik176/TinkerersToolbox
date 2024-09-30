package org.rhm.tinkerers_toolbox;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.rhm.tinkerers_toolbox.block.ModBlocks;
import org.rhm.tinkerers_toolbox.item.ModItems;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class ModDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModelProvider::new);
        pack.addProvider(LanguageProvider::new);
        pack.addProvider(LootTableProvider::new);
        pack.addProvider(TagProvider::new);
    }


    public static class TagProvider extends FabricTagProvider<Item> {
        private static final RegistryKey<Registry<Item>> ITEM_REGISTRY = RegistryKey.ofRegistry(Identifier.of("item"));

        public static final TagKey<Item> MINERAL_TAG = createTag("mineral");
        public static final TagKey<Item> PATTERN_TAG = createTag("tinker_pattern");

        public TagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, ITEM_REGISTRY, registriesFuture);
        }

        protected static TagKey<Item> createTag(String name) {
            return TagKey.of(ITEM_REGISTRY, Identifier.of(ModMain.MOD_ID, name));
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(MINERAL_TAG)
                    .addOptionalTag(ItemTags.COALS)
                    .addOptionalTag(ItemTags.TRIM_MATERIALS);
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

    private static class LanguageProvider extends FabricLanguageProvider {
        protected LanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, "en_us", registryLookup);
        }

        @Override
        public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
            translationBuilder.add(ModBlocks.TINKERERS_BENCH, "Tinkerer's Bench");
            translationBuilder.add(TagProvider.MINERAL_TAG, "Mineral");
            translationBuilder.add(TagProvider.PATTERN_TAG, "Pattern");

            try {
                Path existingFilePath = dataOutput.getModContainer().findPath("assets/" + ModMain.MOD_ID + "/lang/en_us.existing.json").get();
                translationBuilder.add(existingFilePath);
            } catch (Exception e) {
                throw new RuntimeException("Failed to add existing language file!", e);
            }
        }
    }

    private static class LootTableProvider extends FabricBlockLootTableProvider {
        protected LootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            addDrop(ModBlocks.TINKERERS_BENCH, drops(ModBlocks.TINKERERS_BENCH));
        }
    }
}