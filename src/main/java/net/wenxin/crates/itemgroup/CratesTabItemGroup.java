
package net.wenxin.crates.itemgroup;

import net.wenxin.crates.block.OakCrateBlock;
import net.wenxin.crates.CratesModElements;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;

@CratesModElements.ModElement.Tag
public class CratesTabItemGroup extends CratesModElements.ModElement {
	public CratesTabItemGroup(CratesModElements instance) {
		super(instance, 98);
	}

	@Override
	public void initElements() {
		tab = new ItemGroup("tabcrates_tab") {
			@OnlyIn(Dist.CLIENT)
			@Override
			public ItemStack createIcon() {
				return new ItemStack(OakCrateBlock.block, (int) (1));
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
	}
	public static ItemGroup tab;
}
