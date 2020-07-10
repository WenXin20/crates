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

import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.state.DirectionProperty;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;

import java.util.Map;
import java.util.HashMap;

@CratesModElements.ModElement.Tag
public class CratesPreciseSelectionBox extends CratesModElements.ModElement {
	public static final Map<Direction, VoxelShape> CRATES = new HashMap<>();
	public static final DirectionProperty FACING = DirectionalBlock.FACING;
	// Precise selection box
	static {
		CRATES.put(Direction.NORTH, VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1, 1, 1, 15, 15, 15), VoxelShapes.combineAndSimplify(
				Block.makeCuboidShape(0, 0, 0, 2, 16, 2),
				VoxelShapes.combineAndSimplify(Block.makeCuboidShape(14, 0, 0, 16, 16, 2), VoxelShapes.combineAndSimplify(
						Block.makeCuboidShape(14, 0, 14, 16, 16, 16),
						VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 0, 14, 2, 16, 16), VoxelShapes.combineAndSimplify(
								Block.makeCuboidShape(0, 0, 2, 2, 2, 14),
								VoxelShapes.combineAndSimplify(Block.makeCuboidShape(14, 0, 2, 16, 2, 14), VoxelShapes.combineAndSimplify(
										Block.makeCuboidShape(14, 14, 2, 16, 16, 14),
										VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 14, 2, 2, 16, 14), VoxelShapes.combineAndSimplify(
												Block.makeCuboidShape(2, 0, 14, 14, 2, 16),
												VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 0, 0, 14, 2, 2),
														VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 14, 0, 14, 16, 2),
																VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 14, 14, 14, 16, 16),
																		VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 7, 14, 14, 9, 15.8),
																				VoxelShapes.combineAndSimplify(
																						Block.makeCuboidShape(14, 7, 2, 15.8, 9, 14),
																						VoxelShapes.combineAndSimplify(
																								Block.makeCuboidShape(2, 7, 0.2, 14, 9, 2),
																								Block.makeCuboidShape(0.2, 7, 2, 1, 9, 14),
																								IBooleanFunction.OR),
																						IBooleanFunction.OR),
																				IBooleanFunction.OR),
																		IBooleanFunction.OR),
																IBooleanFunction.OR),
														IBooleanFunction.OR),
												IBooleanFunction.OR), IBooleanFunction.OR),
										IBooleanFunction.OR), IBooleanFunction.OR),
								IBooleanFunction.OR), IBooleanFunction.OR),
						IBooleanFunction.OR), IBooleanFunction.OR),
				IBooleanFunction.OR), IBooleanFunction.OR));
		CRATES.put(Direction.SOUTH, VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1, 1, 1, 15, 15, 15), VoxelShapes.combineAndSimplify(
				Block.makeCuboidShape(0, 0, 0, 2, 16, 2),
				VoxelShapes.combineAndSimplify(Block.makeCuboidShape(14, 0, 0, 16, 16, 2), VoxelShapes.combineAndSimplify(
						Block.makeCuboidShape(14, 0, 14, 16, 16, 16),
						VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 0, 14, 2, 16, 16), VoxelShapes.combineAndSimplify(
								Block.makeCuboidShape(0, 0, 2, 2, 2, 14),
								VoxelShapes.combineAndSimplify(Block.makeCuboidShape(14, 0, 2, 16, 2, 14), VoxelShapes.combineAndSimplify(
										Block.makeCuboidShape(14, 14, 2, 16, 16, 14),
										VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 14, 2, 2, 16, 14), VoxelShapes.combineAndSimplify(
												Block.makeCuboidShape(2, 0, 14, 14, 2, 16),
												VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 0, 0, 14, 2, 2),
														VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 14, 0, 14, 16, 2),
																VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 14, 14, 14, 16, 16),
																		VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 7, 14, 14, 9, 15.8),
																				VoxelShapes.combineAndSimplify(
																						Block.makeCuboidShape(14, 7, 2, 15.8, 9, 14),
																						VoxelShapes.combineAndSimplify(
																								Block.makeCuboidShape(2, 7, 0.2, 14, 9, 2),
																								Block.makeCuboidShape(0.2, 7, 2, 1, 9, 14),
																								IBooleanFunction.OR),
																						IBooleanFunction.OR),
																				IBooleanFunction.OR),
																		IBooleanFunction.OR),
																IBooleanFunction.OR),
														IBooleanFunction.OR),
												IBooleanFunction.OR), IBooleanFunction.OR),
										IBooleanFunction.OR), IBooleanFunction.OR),
								IBooleanFunction.OR), IBooleanFunction.OR),
						IBooleanFunction.OR), IBooleanFunction.OR),
				IBooleanFunction.OR), IBooleanFunction.OR));
		CRATES.put(Direction.EAST, VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1, 1, 1, 15, 15, 15), VoxelShapes.combineAndSimplify(
				Block.makeCuboidShape(0, 14, 0, 2, 16, 16),
				VoxelShapes.combineAndSimplify(Block.makeCuboidShape(14, 14, 0, 16, 16, 16), VoxelShapes.combineAndSimplify(
						Block.makeCuboidShape(14, 0, 0, 16, 2, 16),
						VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 0, 0, 2, 2, 16), VoxelShapes.combineAndSimplify(
								Block.makeCuboidShape(0, 2, 0, 2, 14, 2),
								VoxelShapes.combineAndSimplify(Block.makeCuboidShape(14, 2, 0, 16, 14, 2), VoxelShapes.combineAndSimplify(
										Block.makeCuboidShape(14, 2, 14, 16, 14, 16),
										VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 2, 14, 2, 14, 16), VoxelShapes.combineAndSimplify(
												Block.makeCuboidShape(2, 0, 0, 14, 2, 2),
												VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 14, 0, 14, 16, 2),
														VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 14, 14, 14, 16, 16),
																VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 0, 14, 14, 2, 16),
																		VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 0.2, 7, 14, 2, 9),
																				VoxelShapes.combineAndSimplify(
																						Block.makeCuboidShape(14, 2, 7, 15.8, 14, 9),
																						VoxelShapes.combineAndSimplify(
																								Block.makeCuboidShape(2, 14, 7, 14, 15.8, 9),
																								Block.makeCuboidShape(0.2, 2, 7, 1, 14, 9),
																								IBooleanFunction.OR),
																						IBooleanFunction.OR),
																				IBooleanFunction.OR),
																		IBooleanFunction.OR),
																IBooleanFunction.OR),
														IBooleanFunction.OR),
												IBooleanFunction.OR), IBooleanFunction.OR),
										IBooleanFunction.OR), IBooleanFunction.OR),
								IBooleanFunction.OR), IBooleanFunction.OR),
						IBooleanFunction.OR), IBooleanFunction.OR),
				IBooleanFunction.OR), IBooleanFunction.OR));
		CRATES.put(Direction.WEST, VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1, 1, 1, 15, 15, 15), VoxelShapes.combineAndSimplify(
				Block.makeCuboidShape(0, 14, 0, 2, 16, 16),
				VoxelShapes.combineAndSimplify(Block.makeCuboidShape(14, 14, 0, 16, 16, 16), VoxelShapes.combineAndSimplify(
						Block.makeCuboidShape(14, 0, 0, 16, 2, 16),
						VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 0, 0, 2, 2, 16), VoxelShapes.combineAndSimplify(
								Block.makeCuboidShape(0, 2, 0, 2, 14, 2),
								VoxelShapes.combineAndSimplify(Block.makeCuboidShape(14, 2, 0, 16, 14, 2), VoxelShapes.combineAndSimplify(
										Block.makeCuboidShape(14, 2, 14, 16, 14, 16),
										VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 2, 14, 2, 14, 16), VoxelShapes.combineAndSimplify(
												Block.makeCuboidShape(2, 0, 0, 14, 2, 2),
												VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 14, 0, 14, 16, 2),
														VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 14, 14, 14, 16, 16),
																VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 0, 14, 14, 2, 16),
																		VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 0.2, 7, 14, 2, 9),
																				VoxelShapes.combineAndSimplify(
																						Block.makeCuboidShape(14, 2, 7, 15.8, 14, 9),
																						VoxelShapes.combineAndSimplify(
																								Block.makeCuboidShape(2, 14, 7, 14, 15.8, 9),
																								Block.makeCuboidShape(0.2, 2, 7, 1, 14, 9),
																								IBooleanFunction.OR),
																						IBooleanFunction.OR),
																				IBooleanFunction.OR),
																		IBooleanFunction.OR),
																IBooleanFunction.OR),
														IBooleanFunction.OR),
												IBooleanFunction.OR), IBooleanFunction.OR),
										IBooleanFunction.OR), IBooleanFunction.OR),
								IBooleanFunction.OR), IBooleanFunction.OR),
						IBooleanFunction.OR), IBooleanFunction.OR),
				IBooleanFunction.OR), IBooleanFunction.OR));
		CRATES.put(Direction.UP, VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1, 1, 1, 15, 15, 15), VoxelShapes.combineAndSimplify(
				Block.makeCuboidShape(0, 0, 0, 16, 2, 2),
				VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 14, 0, 16, 16, 2), VoxelShapes.combineAndSimplify(
						Block.makeCuboidShape(0, 14, 14, 16, 16, 16),
						VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 0, 14, 16, 2, 16), VoxelShapes.combineAndSimplify(
								Block.makeCuboidShape(14, 0, 2, 16, 2, 14),
								VoxelShapes.combineAndSimplify(Block.makeCuboidShape(14, 14, 2, 16, 16, 14), VoxelShapes.combineAndSimplify(
										Block.makeCuboidShape(0, 14, 2, 2, 16, 14),
										VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 0, 2, 2, 2, 14), VoxelShapes.combineAndSimplify(
												Block.makeCuboidShape(14, 2, 14, 16, 14, 16),
												VoxelShapes.combineAndSimplify(Block.makeCuboidShape(14, 2, 0, 16, 14, 2), VoxelShapes
														.combineAndSimplify(Block.makeCuboidShape(0, 2, 0, 2, 14, 2), VoxelShapes.combineAndSimplify(
																Block.makeCuboidShape(0, 2, 14, 2, 14, 16),
																VoxelShapes.combineAndSimplify(Block.makeCuboidShape(7, 2, 14, 9, 14, 15.8),
																		VoxelShapes.combineAndSimplify(Block.makeCuboidShape(7, 14, 2, 9, 15.8, 14),
																				VoxelShapes.combineAndSimplify(
																						Block.makeCuboidShape(7, 2, 0.2, 9, 14, 2),
																						Block.makeCuboidShape(7, 0.2, 2, 9, 1, 14),
																						IBooleanFunction.OR),
																				IBooleanFunction.OR),
																		IBooleanFunction.OR),
																IBooleanFunction.OR), IBooleanFunction.OR),
														IBooleanFunction.OR),
												IBooleanFunction.OR), IBooleanFunction.OR),
										IBooleanFunction.OR), IBooleanFunction.OR),
								IBooleanFunction.OR), IBooleanFunction.OR),
						IBooleanFunction.OR), IBooleanFunction.OR),
				IBooleanFunction.OR), IBooleanFunction.OR));
		CRATES.put(Direction.DOWN, VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1, 1, 1, 15, 15, 15), VoxelShapes.combineAndSimplify(
				Block.makeCuboidShape(0, 0, 0, 16, 2, 2),
				VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 14, 0, 16, 16, 2), VoxelShapes.combineAndSimplify(
						Block.makeCuboidShape(0, 14, 14, 16, 16, 16),
						VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 0, 14, 16, 2, 16), VoxelShapes.combineAndSimplify(
								Block.makeCuboidShape(14, 0, 2, 16, 2, 14),
								VoxelShapes.combineAndSimplify(Block.makeCuboidShape(14, 14, 2, 16, 16, 14), VoxelShapes.combineAndSimplify(
										Block.makeCuboidShape(0, 14, 2, 2, 16, 14),
										VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 0, 2, 2, 2, 14), VoxelShapes.combineAndSimplify(
												Block.makeCuboidShape(14, 2, 14, 16, 14, 16),
												VoxelShapes.combineAndSimplify(Block.makeCuboidShape(14, 2, 0, 16, 14, 2), VoxelShapes
														.combineAndSimplify(Block.makeCuboidShape(0, 2, 0, 2, 14, 2), VoxelShapes.combineAndSimplify(
																Block.makeCuboidShape(0, 2, 14, 2, 14, 16),
																VoxelShapes.combineAndSimplify(Block.makeCuboidShape(7, 2, 14, 9, 14, 15.8),
																		VoxelShapes.combineAndSimplify(Block.makeCuboidShape(7, 14, 2, 9, 15.8, 14),
																				VoxelShapes.combineAndSimplify(
																						Block.makeCuboidShape(7, 2, 0.2, 9, 14, 2),
																						Block.makeCuboidShape(7, 0.2, 2, 9, 1, 14),
																						IBooleanFunction.OR),
																				IBooleanFunction.OR),
																		IBooleanFunction.OR),
																IBooleanFunction.OR), IBooleanFunction.OR),
														IBooleanFunction.OR),
												IBooleanFunction.OR), IBooleanFunction.OR),
										IBooleanFunction.OR), IBooleanFunction.OR),
								IBooleanFunction.OR), IBooleanFunction.OR),
						IBooleanFunction.OR), IBooleanFunction.OR),
				IBooleanFunction.OR), IBooleanFunction.OR));
	}
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return CRATES.get(state.get(FACING));
	}

	/**
	 * Do not remove this constructor
	 */
	public CratesPreciseSelectionBox(CratesModElements instance) {
		super(instance, 100);
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
