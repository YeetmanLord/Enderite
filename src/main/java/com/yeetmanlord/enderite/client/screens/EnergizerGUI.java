package com.yeetmanlord.enderite.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yeetmanlord.enderite.Enderite;
import com.yeetmanlord.enderite.common.containers.EnergizerContainer;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnergizerGUI extends ContainerScreen<EnergizerContainer>
{
	
	   private static final ResourceLocation ENERGIZER_GUI_TEXTURES = new ResourceLocation(Enderite.MOD_ID, "textures/gui/container/energizer.png");
	   private final ResourceLocation guiTexture;

	public EnergizerGUI(EnergizerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
	{
		  super(screenContainer, inv, titleIn);
	      this.guiTexture = ENERGIZER_GUI_TEXTURES;
	}
	
	@Override
	public void init() 
	{
	      super.init();
	      this.titleX = (this.xSize - this.font.getStringPropertyWidth(this.title)) / 2;
	   }
	@Override
	   public void tick() {
	   }
	@Override
	public void render(MatrixStack stack, int x, int y, float partialTicks) {
		this.renderBackground(stack);
		super.render(stack, x, y, partialTicks);
		this.renderHoveredTooltip(stack, x, y);
		this.drawGuiContainerBackgroundLayer(stack, partialTicks, x, y);
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		this.minecraft.getTextureManager().bindTexture(this.guiTexture);
		this.blit(matrixStack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	      if (this.container.isBurning()) {
	         int k = this.container.getBurnLeftScaled();
	         this.blit(matrixStack, this.guiLeft + 56, this.guiTop + 36 + 12 - k, 176, 12 - k, 14, k + 1);
	      }

	      int l = this.container.getCookProgressionScaled();
	      this.blit(matrixStack, this.guiLeft + 79, this.guiTop + 34, 176, 14, l + 1, 16);
	}
}
