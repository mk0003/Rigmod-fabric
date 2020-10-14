package com.mitat.rigmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Random;

public class RandomItemGeneratorBlock extends Block {

	public RandomItemGeneratorBlock() {
		super(Block.Settings.of(Material.METAL).sounds(BlockSoundGroup.STONE).strength(0.5f));
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!world.isClient) {

			if (player.isCreative()) {
				super.onBreak(world, pos, state, player);
				return;
			}

			ItemStack mainHandTool = player.getMainHandStack();

			boolean hasSilkTouch = false;
			int fortuneLevel = 0;

			if (mainHandTool.getItem().getClass().isAssignableFrom(PickaxeItem.class)) {
				Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(mainHandTool);
				for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
					if (entry.getKey() == Enchantments.SILK_TOUCH) {
						hasSilkTouch = true;
					} else if (entry.getKey() == Enchantments.FORTUNE) {
						fortuneLevel = entry.getValue();
					}
				}
			}

			if (!hasSilkTouch) {
				Random rnd = new Random();

				int fortuneCount = calculateFortune(fortuneLevel);

				for (int i = 0; i <= fortuneCount; i++) {
					Item randomItem = Registry.ITEM.getRandom(rnd);

					ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(randomItem));
					world.spawnEntity(itemEntity);
				}

				return;
			} else {
				Item thisItem = Item.BLOCK_ITEMS.get(this).asItem();
				if (thisItem != null) {
					ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(thisItem));
					world.spawnEntity(itemEntity);
				}

				super.onBreak(world, pos, state, player);
				return;
			}
		}

		super.onBreak(world, pos, state, player);
		return;
	}

	private int calculateFortune(int fortuneLevel) {
		if (fortuneLevel < 1)
			return 0;

		double partNumber = Math.random() * (fortuneLevel + 2);

		int result = 0;
		if (partNumber >= 2) {
			result = (int) (partNumber - 1);
		}

		return result;
	}
}
