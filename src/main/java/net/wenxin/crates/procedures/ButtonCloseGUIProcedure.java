package net.wenxin.crates.procedures;

import net.wenxin.crates.CratesModElements;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import java.util.Map;

@CratesModElements.ModElement.Tag
public class ButtonCloseGUIProcedure extends CratesModElements.ModElement {
	public ButtonCloseGUIProcedure(CratesModElements instance) {
		super(instance, 177);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				System.err.println("Failed to load dependency entity for procedure ButtonCloseGUI!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (entity instanceof PlayerEntity)
			((PlayerEntity) entity).closeScreen();
	}
}
