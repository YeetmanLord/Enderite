package com.yeetmanlord.enderite.common.objects.blocks;

import java.util.Random;

import com.yeetmanlord.enderite.common.tiles.EnergizerTileEntity;
import com.yeetmanlord.enderite.core.init.ModTileEntityTypes;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class EnergizerBlock extends ContainerBlock
{
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	   public static final BooleanProperty LIT = BlockStateProperties.LIT;

	   public EnergizerBlock(AbstractBlock.Properties properties) {
	      super(properties);
	      this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(LIT, Boolean.valueOf(false)));
	   }
	   
	   @Override
	   public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
	   	TileEntity te = worldIn.getTileEntity(pos);
	   	if(te instanceof EnergizerTileEntity) {
	   		if(!worldIn.isRemote) {
	   				NetworkHooks.openGui((ServerPlayerEntity) player, (EnergizerTileEntity) te, pos);
	   				PiglinTasks.func_234478_a_(player, true);
	   				return ActionResultType.SUCCESS;
	   		}
	   	}
	   	return ActionResultType.FAIL;
	   }
	   
	   @Override
	   public BlockState getStateForPlacement(BlockItemUseContext context) {
	      return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	      
	   }

	   /**
	    * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	    */
	   @Override
	   public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
	      if (stack.hasDisplayName()) {
	         TileEntity tileentity = worldIn.getTileEntity(pos);
	         if (tileentity instanceof EnergizerTileEntity) {
	            ((EnergizerTileEntity)tileentity).setCustomName(stack.getDisplayName());
	         }
	      }

	   }
	   @Override
	   @SuppressWarnings("deprecation")
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
	      if (state.getBlock() != newState.getBlock()) {
	         TileEntity tileentity = worldIn.getTileEntity(pos);
	         if (tileentity instanceof EnergizerTileEntity) {
	            InventoryHelper.dropInventoryItems(worldIn, pos, (EnergizerTileEntity)tileentity);
	            ((EnergizerTileEntity)tileentity).grantStoredRecipeExperience(worldIn, Vector3d.copyCentered(pos));
	            worldIn.updateComparatorOutputLevel(pos, this);
	         }

	         super.onReplaced(state, worldIn, pos, newState, isMoving);
	      }
	   }

	   /**
	    * @deprecated call via {@link IBlockState#hasComparatorInputOverride()} whenever possible. Implementing/overriding
	    * is fine.
	    */
	   @Override
	   public boolean hasComparatorInputOverride(BlockState state) {
	      return true;
	   }

	   /**
	    * @deprecated call via {@link IBlockState#getComparatorInputOverride(World,BlockPos)} whenever possible.
	    * Implementing/overriding is fine.
	    */
	   @Override
	   public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
	      return Container.calcRedstone(worldIn.getTileEntity(pos));
	   }

	   /**
	    * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
	    * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
	    * @deprecated call via {@link IBlockState#getRenderType()} whenever possible. Implementing/overriding is fine.
	    */
	   @Override
	   public BlockRenderType getRenderType(BlockState state) {
	      return BlockRenderType.MODEL;
	   }

	   /**
	    * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
	    * blockstate.
	    * @deprecated call via {@link IBlockState#withRotation(Rotation)} whenever possible. Implementing/overriding is
	    * fine.
	    */
	   @Override
	   public BlockState rotate(BlockState state, Rotation rot) {
	      return state.with(FACING, rot.rotate(state.get(FACING)));
	   }

	   /**
	    * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
	    * blockstate.
	    * @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever possible. Implementing/overriding is fine.
	    */
	   @Override
	   public BlockState mirror(BlockState state, Mirror mirrorIn) {
	      return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	   }
	   
	   @Override
	   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
	      builder.add(FACING, LIT);
	   }
	   
	   @Override
	   public TileEntity createNewTileEntity(IBlockReader worldIn) 
	   {
	      return ModTileEntityTypes.TILE_ENERGIZED.get().create();
	   }

	   /**
	    * Interface for handling interaction with blocks that impliment EnergizerTileEntity. Called in onBlockActivated
	    * inside EnergizerTileEntity.
	    */
	   
	   public void interactWith(World worldIn, BlockPos pos, PlayerEntity player) {
	      TileEntity tileentity = worldIn.getTileEntity(pos);
	      if (tileentity instanceof EnergizerTileEntity) {
	         player.openContainer((INamedContainerProvider)tileentity);
	      }
	   }
	   /**
	    * Called periodically clientside on blocks near the player to show effects (like energizer fire particles). Note that
	    * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
	    * of whether the block can receive random update ticks
	    */
	@Override
	@OnlyIn(Dist.CLIENT)
	   public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
	      if (stateIn.get(LIT)) {
	         double d0 = (double)pos.getX() + 0.5D;
	         double d1 = (double)pos.getY();
	         double d2 = (double)pos.getZ() + 0.5D;

	         Direction direction = stateIn.get(FACING);
	         Direction.Axis direction$axis = direction.getAxis();
	         double d3 = 0.52D;
	         double d4 = rand.nextDouble() * 0.6D - 0.3D;
	         double d5 = direction$axis == Direction.Axis.X ? (double)direction.getXOffset() * d3 : d4;
	         double d6 = rand.nextDouble() * 6.0D / 16.0D;
	         double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getZOffset() * d3 : d4;
	         worldIn.addParticle(ParticleTypes.END_ROD, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
	      }
	   }

}
