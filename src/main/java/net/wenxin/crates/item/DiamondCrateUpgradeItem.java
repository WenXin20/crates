
package net.wenxin.crates.item;

import net.wenxin.crates.itemgroup.CratesTabItemGroup;
import net.wenxin.crates.CratesModElements;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.BlockState;

import java.util.List;

@CratesModElements.ModElement.Tag
public class DiamondCrateUpgradeItem extends CratesModElements.ModElement {
	@ObjectHolder("crates:diamond_crate_upgrade")
	public static final Item block = null;
	public DiamondCrateUpgradeItem(CratesModElements instance) {
		super(instance, 55);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(CratesTabItemGroup.tab).maxStackSize(1).rarity(Rarity.COMMON));
			setRegistryName("diamond_crate_upgrade");
		}

		@Override
		public int getItemEnchantability() {
			return 0;
		}

		@Override
		public int getUseDuration(ItemStack itemstack) {
			return 0;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}

		@Override
		public void addInformation(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.addInformation(itemstack, world, list, flag);
			list.add(new StringTextComponent("\u00A7bFrom gold to diamond"));
		}
	}
}
