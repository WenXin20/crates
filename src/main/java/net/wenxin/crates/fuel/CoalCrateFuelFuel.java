
package net.wenxin.crates.fuel;

import net.wenxin.crates.block.CoalCrateBlock;
import net.wenxin.crates.CratesModElements;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.item.ItemStack;

@CratesModElements.ModElement.Tag
public class CoalCrateFuelFuel extends CratesModElements.ModElement {
	public CoalCrateFuelFuel(CratesModElements instance) {
		super(instance, 179);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void furnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
		if (event.getItemStack().getItem() == new ItemStack(CoalCrateBlock.block, (int) (1)).getItem())
			event.setBurnTime(6400);
	}
}
