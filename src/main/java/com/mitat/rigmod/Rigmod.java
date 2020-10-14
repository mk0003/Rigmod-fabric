package com.mitat.rigmod;

import com.mitat.rigmod.block.RandomItemGeneratorBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Rigmod implements ModInitializer {

	public static final String MOD_ID="rigmod";

	public static final RandomItemGeneratorBlock RANDOM_ITEM_GENERATOR_BLOCK = new RandomItemGeneratorBlock();
	public static final ItemGroup RIGMOD_ITEM_GROUP = FabricItemGroupBuilder.build(
			  new Identifier(MOD_ID, "all"),
			  () -> new ItemStack(RANDOM_ITEM_GENERATOR_BLOCK));

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "random_item_generator_block"), RANDOM_ITEM_GENERATOR_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "random_item_generator_block"), new BlockItem(RANDOM_ITEM_GENERATOR_BLOCK, new Item.Settings().group(RIGMOD_ITEM_GROUP)));
	}
}
