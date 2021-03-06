
package net.wenxin.crates.block;

import net.wenxin.crates.procedures.CrateOpenSoundProcedure;
import net.wenxin.crates.itemgroup.CratesTabItemGroup;
import net.wenxin.crates.gui.CrateGUI2Gui;
import net.wenxin.crates.CratesPreciseSelectionBox;
import net.wenxin.crates.CratesModElements;

import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Rotation;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Hand;
import net.minecraft.util.Direction;
import net.minecraft.util.ActionResultType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.StateContainer;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.BooleanProperty;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.NetworkManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.BlockItem;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.fluid.IFluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;
import net.minecraft.advancements.CriteriaTriggers;

import javax.annotation.Nullable;

import java.util.stream.IntStream;
import java.util.List;
import java.util.Collections;

import io.netty.buffer.Unpooled;

@CratesModElements.ModElement.Tag
public class HoneyCrateBlock extends CratesModElements.ModElement implements IWaterLoggable {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	@ObjectHolder("crates:honey_crate")
	public static final Block block = null;
	@ObjectHolder("crates:honey_crate")
	public static final TileEntityType<CustomTileEntity> tileEntityType = null;
	public HoneyCrateBlock(CratesModElements instance) {
		super(instance, 21);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items.add(() -> new BlockItem(block, new Item.Properties().group(CratesTabItemGroup.tab)).setRegistryName(block.getRegistryName()));
	}

	@SubscribeEvent
	public void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event) {
		event.getRegistry().register(TileEntityType.Builder.create(CustomTileEntity::new, block).build(null).setRegistryName("honey_crate"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void clientLoad(FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(block, RenderType.getTranslucent());
	}
	public static class CustomBlock extends Block implements IWaterLoggable {
		public static final DirectionProperty FACING = DirectionalBlock.FACING;
		public CustomBlock() {
			super(Block.Properties.create(Material.MISCELLANEOUS).sound(SoundType.field_226947_m_).hardnessAndResistance(2f, 2f).lightValue(0)
					.notSolid());
			this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.SOUTH).with(WATERLOGGED, false));
			setRegistryName("honey_crate");
		}

		public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
			entityIn.playSound(SoundEvents.BLOCK_HONEY_BLOCK_SLIDE, 1.0F, 1.0F);
			if (!worldIn.isRemote) {
				worldIn.setEntityState(entityIn, (byte) 54);
			}
			if (entityIn.onLivingFall(fallDistance, 0.2F)) {
				entityIn.playSound(this.soundType.getFallSound(), this.soundType.getVolume() * 0.5F, this.soundType.getPitch() * 0.75F);
			}
		}

		public void onLanded(IBlockReader worldIn, Entity entityIn) {
			super.onLanded(worldIn, entityIn);
		}

		@Override
		public boolean isStickyBlock(BlockState state) {
			return true;
		}

		public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
			double d0 = Math.abs(entityIn.getMotion().y);
			if (d0 < 0.1D && !entityIn.isSteppingCarefully()) {
				double d1 = 0.4D + d0 * 0.2D;
				entityIn.setMotion(entityIn.getMotion().mul(d1, 1.0D, d1));
			}
			super.onEntityWalk(worldIn, pos, entityIn);
		}

		public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
			if (this.entityPos(pos, entityIn)) {
				this.playerAdv(entityIn, pos);
				this.entityMotion(entityIn);
				this.entitySound(worldIn, entityIn);
			}
			super.onEntityCollision(state, worldIn, pos, entityIn);
		}

		private static boolean entityType(Entity entity) {
			return entity instanceof LivingEntity || entity instanceof AbstractMinecartEntity || entity instanceof TNTEntity
					|| entity instanceof BoatEntity;
		}

		private boolean entityPos(BlockPos pos, Entity entityIn) {
			if (entityIn.onGround) {
				return false;
			} else if (entityIn.getPosY() > (double) pos.getY() + 0.9375D - 1.0E-7D) {
				return false;
			} else if (entityIn.getMotion().y >= -0.08D) {
				return false;
			} else {
				double d0 = Math.abs((double) pos.getX() + 0.5D - entityIn.getPosX());
				double d1 = Math.abs((double) pos.getZ() + 0.5D - entityIn.getPosZ());
				double d2 = 0.4375D + (double) (entityIn.getWidth() / 2.0F);
				return d0 + 1.0E-7D > d2 || d1 + 1.0E-7D > d2;
			}
		}

		private void playerAdv(Entity entityIn, BlockPos pos) {
			if (entityIn instanceof ServerPlayerEntity && entityIn.world.getGameTime() % 20L == 0L) {
				CriteriaTriggers.field_229864_K_.func_227152_a_((ServerPlayerEntity) entityIn, entityIn.world.getBlockState(pos));
			}
		}

		private void entityMotion(Entity enitityIn) {
			Vec3d vec3d = enitityIn.getMotion();
			if (vec3d.y < -0.13D) {
				double d0 = -0.05D / vec3d.y;
				enitityIn.setMotion(new Vec3d(vec3d.x * d0, -0.05D, vec3d.z * d0));
			} else {
				enitityIn.setMotion(new Vec3d(vec3d.x, -0.05D, vec3d.z));
			}
			enitityIn.fallDistance = 0.0F;
		}

		private void entitySound(World world, Entity entityIn) {
			if (entityType(entityIn)) {
				if (world.rand.nextInt(5) == 0) {
					entityIn.playSound(SoundEvents.BLOCK_HONEY_BLOCK_SLIDE, 1.0F, 1.0F);
				}
				if (!world.isRemote && world.rand.nextInt(5) == 0) {
					world.setEntityState(entityIn, (byte) 53);
				}
			}
		}

		@OnlyIn(Dist.CLIENT)
		public static void entityPos3(Entity entityIn) {
			entityPos2(entityIn, 5);
		}

		@OnlyIn(Dist.CLIENT)
		public static void entityPos4(Entity entityIn) {
			entityPos2(entityIn, 10);
		}

		@OnlyIn(Dist.CLIENT)
		private static void entityPos2(Entity entityIn, int ent) {
			if (entityIn.world.isRemote) {
				BlockState blockstate = Blocks.HONEY_BLOCK.getDefaultState();
				for (int i = 0; i < ent; ++i) {
					entityIn.world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, blockstate), entityIn.getPosX(), entityIn.getPosY(),
							entityIn.getPosZ(), 0.0D, 0.0D, 0.0D);
				}
			}
		}

		@Override
		public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
			return false;
		}

		@Override
		public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
			return true;
		}

		@Override
		public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
			return CratesPreciseSelectionBox.CRATES.get(state.get(FACING));
		}

		@Override
		protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
			builder.add(FACING, WATERLOGGED);
		}

		@Override
		public BlockState rotate(BlockState state, Rotation rot) {
			if (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) {
				if ((Direction) state.get(FACING) == Direction.WEST || (Direction) state.get(FACING) == Direction.EAST) {
					return state.with(FACING, Direction.UP);
				} else if ((Direction) state.get(FACING) == Direction.UP || (Direction) state.get(FACING) == Direction.DOWN) {
					return state.with(FACING, Direction.WEST);
				}
			}
			return state;
		}

		@Override
		public BlockState getStateForPlacement(BlockItemUseContext context) {
			IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
			Direction facing = context.getFace();
			if (facing == Direction.WEST || facing == Direction.EAST)
				facing = Direction.UP;
			else if (facing == Direction.NORTH || facing == Direction.SOUTH)
				facing = Direction.EAST;
			else
				facing = Direction.SOUTH;
			return this.getDefaultState().with(FACING, facing).with(WATERLOGGED,
					ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8);
		}

		public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos,
				BlockPos facingPos) {
			if (stateIn.get(WATERLOGGED)) {
				worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
			}
			return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		}

		@SuppressWarnings("deprecation")
		public IFluidState getFluidState(BlockState state) {
			return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
		}

		@Override
		public MaterialColor getMaterialColor(BlockState state, IBlockReader blockAccess, BlockPos pos) {
			return MaterialColor.YELLOW_TERRACOTTA;
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty())
				return dropsOriginal;
			return Collections.singletonList(new ItemStack(this, 1));
		}

		@Override
		public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity entity, Hand hand,
				BlockRayTraceResult hit) {
			super.onBlockActivated(state, world, pos, entity, hand, hit);
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			if (entity instanceof ServerPlayerEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) entity, new INamedContainerProvider() {
					@Override
					public ITextComponent getDisplayName() {
						return new StringTextComponent("Honey Crate");
					}

					@Override
					public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
						return new CrateGUI2Gui.GuiContainerMod(id, inventory,
								new PacketBuffer(Unpooled.buffer()).writeBlockPos(new BlockPos(x, y, z)));
					}
				}, new BlockPos(x, y, z));
			}
			Direction direction = hit.getFace();
			{
				java.util.HashMap<String, Object> $_dependencies = new java.util.HashMap<>();
				$_dependencies.put("entity", entity);
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				CrateOpenSoundProcedure.executeProcedure($_dependencies);
			}
			return ActionResultType.SUCCESS;
		}

		@Override
		public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			return tileEntity instanceof INamedContainerProvider ? (INamedContainerProvider) tileEntity : null;
		}

		@Override
		public boolean hasTileEntity(BlockState state) {
			return true;
		}

		@Override
		public TileEntity createTileEntity(BlockState state, IBlockReader world) {
			return new CustomTileEntity();
		}

		@Override
		public boolean eventReceived(BlockState state, World world, BlockPos pos, int eventID, int eventParam) {
			super.eventReceived(state, world, pos, eventID, eventParam);
			TileEntity tileentity = world.getTileEntity(pos);
			return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
		}

		@Override
		public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
			if (state.getBlock() != newState.getBlock()) {
				TileEntity tileentity = world.getTileEntity(pos);
				if (tileentity instanceof CustomTileEntity) {
					InventoryHelper.dropInventoryItems(world, pos, (CustomTileEntity) tileentity);
					world.updateComparatorOutputLevel(pos, this);
				}
				super.onReplaced(state, world, pos, newState, isMoving);
			}
		}

		@Override
		public boolean hasComparatorInputOverride(BlockState state) {
			return true;
		}

		@Override
		public int getComparatorInputOverride(BlockState blockState, World world, BlockPos pos) {
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity instanceof CustomTileEntity)
				return Container.calcRedstoneFromInventory((CustomTileEntity) tileentity);
			else
				return 0;
		}
	}

	public static class CustomTileEntity extends LockableLootTileEntity implements ISidedInventory {
		private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(33, ItemStack.EMPTY);
		protected CustomTileEntity() {
			super(tileEntityType);
		}

		@Override
		public void read(CompoundNBT compound) {
			super.read(compound);
			if (!this.checkLootAndRead(compound)) {
				this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
			}
			ItemStackHelper.loadAllItems(compound, this.stacks);
		}

		@Override
		public CompoundNBT write(CompoundNBT compound) {
			super.write(compound);
			if (!this.checkLootAndWrite(compound)) {
				ItemStackHelper.saveAllItems(compound, this.stacks);
			}
			return compound;
		}

		@Override
		public SUpdateTileEntityPacket getUpdatePacket() {
			return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
		}

		@Override
		public CompoundNBT getUpdateTag() {
			return this.write(new CompoundNBT());
		}

		@Override
		public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
			this.read(pkt.getNbtCompound());
		}

		@Override
		public int getSizeInventory() {
			return stacks.size();
		}

		@Override
		public boolean isEmpty() {
			for (ItemStack itemstack : this.stacks)
				if (!itemstack.isEmpty())
					return false;
			return true;
		}

		@Override
		public ITextComponent getDefaultName() {
			return new StringTextComponent("honey_crate");
		}

		@Override
		public int getInventoryStackLimit() {
			return 64;
		}

		@Override
		public Container createMenu(int id, PlayerInventory player) {
			return new CrateGUI2Gui.GuiContainerMod(id, player, new PacketBuffer(Unpooled.buffer()).writeBlockPos(this.getPos()));
		}

		@Override
		public ITextComponent getDisplayName() {
			return new StringTextComponent("Honey Crate");
		}

		@Override
		protected NonNullList<ItemStack> getItems() {
			return this.stacks;
		}

		@Override
		protected void setItems(NonNullList<ItemStack> stacks) {
			this.stacks = stacks;
		}

		@Override
		public boolean isItemValidForSlot(int index, ItemStack stack) {
			return true;
		}

		@Override
		public int[] getSlotsForFace(Direction side) {
			return IntStream.range(0, this.getSizeInventory()).toArray();
		}

		@Override
		public boolean canInsertItem(int index, ItemStack stack, @Nullable Direction direction) {
			return this.isItemValidForSlot(index, stack);
		}

		@Override
		public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
			return true;
		}
		private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());
		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
			if (!this.removed && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
				return handlers[facing.ordinal()].cast();
			return super.getCapability(capability, facing);
		}

		@Override
		public void remove() {
			super.remove();
			for (LazyOptional<? extends IItemHandler> handler : handlers)
				handler.invalidate();
		}
	}
}
