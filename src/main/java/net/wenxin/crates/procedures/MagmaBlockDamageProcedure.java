package net.wenxin.crates.procedures;

import net.wenxin.crates.CratesModElements;

import net.minecraft.util.DamageSource;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.Entity;

import java.util.Map;

@CratesModElements.ModElement.Tag
public class MagmaBlockDamageProcedure extends CratesModElements.ModElement {
	public MagmaBlockDamageProcedure(CratesModElements instance) {
		super(instance, 125);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			System.err.println("Failed to load dependency entity for procedure MagmaBlockDamage!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (((!(entity.isSneaking())) && (!(entity instanceof ItemEntity)))) {
			entity.attackEntityFrom(DamageSource.HOT_FLOOR, (float) 1);
		}
	}
}
