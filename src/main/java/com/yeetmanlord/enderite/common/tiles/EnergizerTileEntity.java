package com.yeetmanlord.enderite.common.tiles;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yeetmanlord.enderite.Enderite;
import com.yeetmanlord.enderite.common.containers.EnergizerContainer;
import com.yeetmanlord.enderite.common.objects.blocks.EnergizerBlock;
import com.yeetmanlord.enderite.core.init.ModRecipeTypes;
import com.yeetmanlord.enderite.core.init.ModTileEntityTypes;
import com.yeetmanlord.enderite.core.recipes.AbstractEnergizerRecipe;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class EnergizerTileEntity extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity
{
	public EnergizerTileEntity(TileEntityType<?> typeIn, IRecipeType<? extends AbstractEnergizerRecipe> recipeTypeIn) 
	{
		super(typeIn);
		this.recipeType = recipeTypeIn;
	}
	
	public EnergizerTileEntity()
	{
		this(ModTileEntityTypes.TILE_ENERGIZED.get(),ModRecipeTypes.Types.ENERGIZING);
	}
	
	
	private static final int[] SLOTS_UP = new int[]{0};
	   private static final int[] SLOTS_DOWN = new int[]{2, 1};
	   private static final int[] SLOTS_HORIZONTAL = new int[]{1};
	   protected NonNullList<ItemStack> contents = NonNullList.withSize(3, ItemStack.EMPTY);
	   private IItemHandlerModifiable items = createHandler();
	   private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);
	   private int burnTime;
	   private int burnTimeTotal;
	   private int cookTime;
	   private int cookTimeTotal = 1000;
	   protected final IIntArray energizerData = new IIntArray() {
	      public int get(int index) {
	         switch(index) {
	         case 0:
	            return EnergizerTileEntity.this.burnTime;
	         case 1:
	            return EnergizerTileEntity.this.burnTimeTotal;
	         case 2:
	            return EnergizerTileEntity.this.cookTime;
	         case 3:
	            return EnergizerTileEntity.this.cookTimeTotal;
	         default:
	            return 0;
	         }
	      }

	      public void set(int index, int value) {
	         switch(index) {
	         case 0:
	        	 EnergizerTileEntity.this.burnTime = value;
	            break;
	         case 1:
	        	 EnergizerTileEntity.this.burnTimeTotal = value;
	            break;
	         case 2:
	        	 EnergizerTileEntity.this.cookTime = value;
	            break;
	         case 3:
	        	 EnergizerTileEntity.this.cookTimeTotal = value;
	         }

	      }

	      public int size() {
	         return 4;
	      }
	   };
	   private final Object2IntOpenHashMap<ResourceLocation> recipes = new Object2IntOpenHashMap<>();
	   protected final IRecipeType<? extends AbstractEnergizerRecipe> recipeType;

	   
	   @Deprecated //Forge - get burn times by calling ForgeHooks#getBurnTime(ItemStack)
	   public static Map<Item, Integer> getBurnTimes() {
	      Map<Item, Integer> map = Maps.newLinkedHashMap();
	      addItemBurnTime(map, Items.NETHER_STAR, 50000);
	      addItemBurnTime(map, Items.LAVA_BUCKET, 20000);
	      addItemBurnTime(map, Blocks.REDSTONE_BLOCK, 16000);
	      addItemBurnTime(map, Items.BLAZE_ROD, 2400);
	      addItemBurnTime(map, Items.REDSTONE, 1600);
	      return map;
	   }

	   private static boolean isNonFlammable(Item item) {
	      return ItemTags.NON_FLAMMABLE_WOOD.contains(item);
	   }

	   private static void addItemBurnTime(Map<Item, Integer> map, IItemProvider itemProvider, int burnTimeIn) {
	      Item item = itemProvider.asItem();
	      if (isNonFlammable(item)) {
	         if (SharedConstants.developmentMode) {
	            throw (IllegalStateException)Util.pauseDevMode(new IllegalStateException("A developer tried to explicitly make fire resistant item " + item.getDisplayName((ItemStack)null).getString() + " a energizer fuel. That will not work!"));
	         }
	      } else {
	         map.put(item, burnTimeIn);
	      }
	   }
	   
	   
	   private boolean isBurning() {
	      return this.burnTime > 0;
	   }
	   
	   @Override
	   public void read(BlockState state, CompoundNBT nbt) { 
		   super.read(state, nbt);
		  ItemStackHelper.saveAllItems(nbt, this.contents);
	      this.burnTime = nbt.getInt("BurnTime");
	      this.cookTime = nbt.getInt("CookTime");
	      this.cookTimeTotal = nbt.getInt("CookTimeTotal");
	      this.burnTimeTotal = this.getBurnTime(this.contents .get(1));
	      CompoundNBT compoundnbt = nbt.getCompound("RecipesUsed");

	      for(String s : compoundnbt.keySet()) {
	         this.recipes.put(new ResourceLocation(s), compoundnbt.getInt(s));
	      }

	   }
	   
	   @Override
	   public CompoundNBT write(CompoundNBT compound) {
	      super.write(compound);
	      compound.putInt("BurnTime", this.burnTime);
	      compound.putInt("CookTime", this.cookTime);
	      compound.putInt("CookTimeTotal", this.cookTimeTotal);
	      ItemStackHelper.saveAllItems(compound, this.contents, false);
	      return compound;
	   }

	   
	@Override 
	@SuppressWarnings({ "unchecked", "unused" })
	public void tick() {
	      boolean flag = this.isBurning();
	      boolean flag1 = false;
	      if (this.isBurning()) {
	         --this.burnTime;
	      }

	      if (!this.world.isRemote) {
	         ItemStack itemstack = this.contents.get(1);
	         if (this.isBurning() || !itemstack.isEmpty() && !this.contents.get(0).isEmpty()) {
	            IRecipe<?> irecipe = this.world.getRecipeManager().getRecipe((IRecipeType<AbstractEnergizerRecipe>)this.recipeType, this, this.world).orElse(null);
	            if (!this.isBurning() && this.canSmelt(irecipe)) {
	               this.burnTime = this.getBurnTime(itemstack);
	               this.burnTimeTotal = this.burnTime;
	               if (this.isBurning()) {
	                  flag1 = true;
	                  if (itemstack.hasContainerItem())
	                      this.contents.set(1, itemstack.getContainerItem());
	                  else
	                  if (!itemstack.isEmpty()) {
	                     Item item = itemstack.getItem();
	                     itemstack.shrink(1);
	                     if (itemstack.isEmpty()) {
	                        this.contents.set(1, itemstack.getContainerItem());
	                     }
	                  }
	               }
	            }

	            if (this.isBurning() && this.canSmelt(irecipe)) {
	               ++this.cookTime;
	               if (this.cookTime == this.cookTimeTotal) {
	                  this.cookTime = 0;
	                  this.cookTimeTotal = this.getCookTime();
	                  this.energize(irecipe);
	                  flag1 = true;
	               }
	            } else {
	               this.cookTime = 0;
	            }
	         } else if (!this.isBurning() && this.cookTime > 0) {
	            this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
	         }

	         if (flag != this.isBurning()) {
	            flag1 = true;
	            this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(EnergizerBlock.LIT, Boolean.valueOf(this.isBurning())), 3);
	         }
	      }

	      if (flag1) {
	         this.markDirty();
	      }

	   }
	
	   protected boolean canSmelt(@Nullable IRecipe<?> recipeIn) {
	      if (!this.contents.get(0).isEmpty() && recipeIn != null) {
	         ItemStack itemstack = recipeIn.getRecipeOutput();
	         if (itemstack.isEmpty()) {
	            return false;
	         } else {
	            ItemStack itemstack1 = this.contents.get(2);
	            if (itemstack1.isEmpty()) {
	               return true;
	            } else if (!itemstack1.isItemEqual(itemstack)) {
	               return false;
	            } else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make energizer respect stack sizes in energizer recipes
	               return true;
	            } else {
	               return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make energizer respect stack sizes in energizer recipes
	            }
	         }
	      } else {
	         return false;
	      }
	   }
	   
	   private void energize(@Nullable IRecipe<?> recipe) {
	      if (recipe != null && this.canSmelt(recipe)) {
	         ItemStack itemstack = this.contents.get(0);
	         ItemStack itemstack1 = recipe.getRecipeOutput();
	         ItemStack itemstack2 = this.contents.get(2);
	         if (itemstack2.isEmpty()) {
	            this.contents.set(2, itemstack1.copy());
	         } else if (itemstack2.getItem() == itemstack1.getItem()) {
	            itemstack2.grow(itemstack1.getCount());
	         }

	         if (!this.world.isRemote) {
	            this.setRecipeUsed(recipe);
	         }

	         if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.contents.get(1).isEmpty() && this.contents.get(1).getItem() == Items.BUCKET) {
	            this.contents.set(1, new ItemStack(Items.WATER_BUCKET));
	         }

	         itemstack.shrink(1);
	      }
	   }

	   @SuppressWarnings("unused")
	protected int getBurnTime(ItemStack fuel) {
	      if (fuel.isEmpty()) {
	         return 0;
	      } else {
	         Item item = fuel.getItem();
	         return net.minecraftforge.common.ForgeHooks.getBurnTime(fuel);
	      }
	   }

	   @SuppressWarnings("unchecked")
	protected int getCookTime() {
	      return this.world.getRecipeManager().getRecipe((IRecipeType<AbstractEnergizerRecipe>)this.recipeType, this, this.world).map(AbstractEnergizerRecipe::getCookTime).orElse(1000);
	   }

	   public static boolean isFuel(ItemStack stack) {
	      return net.minecraftforge.common.ForgeHooks.getBurnTime(stack) > 0;
	   }

	   public int[] getSlotsForFace(Direction side) {
	      if (side == Direction.DOWN) {
	         return SLOTS_DOWN;
	      } else {
	         return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
	      }
	   }

	   /**
	    * Returns true if automation can insert the given item in the given slot from the given side.
	    */
	   @Override
	   public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
	      return this.isItemValidForSlot(index, itemStackIn);
	   }

	   /**
	    * Returns true if automation can extract the given item in the given slot from the given side.
	    */
	   @Override
	   public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
	      if (direction == Direction.DOWN && index == 1) {
	         Item item = stack.getItem();
	         if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
	            return false;
	         }
	      }

	      return true;
	   }

	   /**
	    * Returns the number of slots in the inventory.
	    */
	   @Override
	   public int getSizeInventory() {
	      return this.contents.size();
	   }
	   
	   @Override
	   public boolean isEmpty() {
	      for(ItemStack itemstack : this.contents) {
	         if (!itemstack.isEmpty()) {
	            return false;
	         }
	      }

	      return true;
	   }

	   /**
	    * Returns the stack in the given slot.
	    */
	   @Override
	   public ItemStack getStackInSlot(int index) {
	      return this.contents.get(index);
	   }

	   /**
	    * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	    */
	   @Override
	   public ItemStack decrStackSize(int index, int count) {
	      return ItemStackHelper.getAndSplit(this.contents, index, count);
	   }

	   /**
	    * Removes a stack from the given slot and returns it.
	    */
	   @Override
	   public ItemStack removeStackFromSlot(int index) {
	      return ItemStackHelper.getAndRemove(this.contents, index);
	   }

	   /**
	    * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	    */
	   @Override
	   public void setInventorySlotContents(int index, ItemStack stack) {
	      ItemStack itemstack = this.contents.get(index);
	      boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
	      this.contents.set(index, stack);
	      if (stack.getCount() > this.getInventoryStackLimit()) {
	         stack.setCount(this.getInventoryStackLimit());
	      }

	      if (index == 0 && !flag) {
	         this.cookTimeTotal = this.getCookTime();
	         this.cookTime = 0;
	         this.markDirty();
	      }

	   }
	   
	   
	   
	   /**
	    * Don't rename this method to canInteractWith due to conflicts with Container
	    */
	   @Override
	   public boolean isUsableByPlayer(PlayerEntity player) {
	      if (this.world.getTileEntity(this.pos) != this) {
	         return false;
	      } else {
	         return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	      }
	   }

	   /**
	    * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
	    * guis use Slot.isItemValid
	    */
	   @Override
	   public boolean isItemValidForSlot(int index, ItemStack stack) {
	      if (index == 2) {
	         return false;
	      } else if (index != 1) {
	         return true;
	      } else {
	         ItemStack itemstack = this.contents.get(1);
	         return isFuel(stack) || stack.getItem() == Items.BUCKET && itemstack.getItem() != Items.BUCKET;
	      }
	   }

	   public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
	      if (recipe != null) {
	         ResourceLocation resourcelocation = recipe.getId();
	         this.recipes.addTo(resourcelocation, 1);
	      }

	   }
	   
	   @Nullable
	   public IRecipe<?> getRecipeUsed() {
	      return null;
	   }

	   public void onCrafting(PlayerEntity player) {
	   }
	   
	   @Override
		public void updateContainingBlockInfo()
		{
			super.updateContainingBlockInfo();
			if(this.itemHandler != null)
			{
				this.itemHandler.invalidate();
				this.itemHandler = null;
			}
		}
		
		@Override
		public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nonnull Direction side) 
		{
			if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			{
				return itemHandler.cast();
			}
			return super.getCapability(cap, side);
		}
	   
	   public List<IRecipe<?>> grantStoredRecipeExperience(World world, Vector3d pos) {
	      List<IRecipe<?>> list = Lists.newArrayList();

	      for(Entry<ResourceLocation> entry : this.recipes.object2IntEntrySet()) {
	         world.getRecipeManager().getRecipe(entry.getKey()).ifPresent((recipe) -> {
	            list.add(recipe);
	            splitAndSpawnExperience(world, pos, entry.getIntValue(), ((AbstractEnergizerRecipe)recipe).getExperience());
	         });
	      }

	      return list;
	   }
	   
	   private static void splitAndSpawnExperience(World world, Vector3d pos, int craftedAmount, float experience) {
	      int i = MathHelper.floor((float)craftedAmount * experience);
	      float f = MathHelper.frac((float)craftedAmount * experience);
	      if (f != 0.0F && Math.random() < (double)f) {
	         ++i;
	      }

	      while(i > 0) {
	         int j = ExperienceOrbEntity.getXPSplit(i);
	         i -= j;
	         world.addEntity(new ExperienceOrbEntity(world, pos.x, pos.y, pos.z, j));
	      }

	   }
	   
	   @Override
	   public void fillStackedContents(RecipeItemHelper helper) {
	      for(ItemStack itemstack : this.contents) {
	         helper.accountStack(itemstack);
	      }

	   }

	   net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
	           net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

	   @Override
	   protected void invalidateCaps() {
	      super.invalidateCaps();
	      for (int x = 0; x < handlers.length; x++)
	        handlers[x].invalidate();
	   }
	   
	   @Override
		   protected ITextComponent getDefaultName() {
		      return new TranslationTextComponent("container.energizer");
		   }
		   
		   @Override
		   protected Container createMenu(int id, PlayerInventory player) {
		      return new EnergizerContainer(id, player);
		   }
		   
		   private IItemHandlerModifiable createHandler() 
			{
				return new InvWrapper(this);
			}
		   
			@Override
			public void remove() 
			{
				super.remove();
				if(itemHandler != null)
				{
					itemHandler.invalidate();
				}
			}

			@Override
			public void clear() 
			{
				Enderite.LOGGER.info("Cleared Inventory");
				this.contents.clear();
			}
	}
