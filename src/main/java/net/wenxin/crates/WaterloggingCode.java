/**
 * This mod element is always locked. Enter your code in the methods below.
 * If you don't need some of these methods, you can remove them as they
 * are overrides of the base class CratesModElements.ModElement.
 *
 * You can register new events in this class too.
 *
 * As this class is loaded into mod element list, it NEEDS to extend
 * ModElement class. If you remove this extend statement or remove the
 * constructor, the compilation will fail.
 *
 * If you want to make a plain independent class, create it in
 * "Workspace" -> "Source" menu.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
*/
package net.wenxin.crates;

import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import net.minecraft.block.IWaterLoggable;

@CratesModElements.ModElement.Tag
public class WaterloggingCode extends CratesModElements.ModElement implements IWaterLoggable {
	// net.minecraft.block.IWaterLoggable;
	// net.minecraft.state.properties.BlockStateProperties;
	// net.minecraft.state.BooleanProperty;
	// net.minecraft.fluid.IFluidState;
	// net.minecraft.fluid.Fluids;
	// net.minecraft.tags.FluidTags;
	// public static final BooleanProperty WATERLOGGED =
	// BlockStateProperties.WATERLOGGED;
	// public CustomBlock() {
	// super(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(5f,
	// 15f).lightValue(0).notSolid());
	// this.setDefaultState(this.stateContainer.getBaseState()
	// .with(FACING, Direction.SOUTH)
	// .with(WATERLOGGED, false));
	// setRegistryName("vertical_azure_crate");
	// }
	//
	// @Override
	// protected void fillStateContainer(StateContainer.Builder<Block, BlockState>
	// builder) {
	// builder.add(FACING, WATERLOGGED);
	// }
	// @Override
	// public BlockState getStateForPlacement(BlockItemUseContext context) {
	// IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
	// Direction facing = context.getFace();
	// if (facing == Direction.WEST || facing == Direction.EAST)
	// facing = Direction.UP;
	// else if (facing == Direction.NORTH || facing == Direction.SOUTH)
	// facing = Direction.EAST;
	// else
	// facing = Direction.SOUTH;
	// return this.getDefaultState().with(FACING, facing).with(WATERLOGGED,
	// ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8);
	// }
	//
	// @SuppressWarnings("deprecation")
	// public IFluidState getFluidState(BlockState state) {
	// return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) :
	// super.getFluidState(state);
	// }
	/**
	 * Do not remove this constructor
	 */
	public WaterloggingCode(CratesModElements instance) {
		super(instance, 70);
	}

	@Override
	public void initElements() {
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
	}

	@Override
	public void serverLoad(FMLServerStartingEvent event) {
	}
}
