package mrhid6.zonus.gui;

import org.lwjgl.opengl.GL11;

public class GuiStearilliumReactor extends GuiMain {

	public ContainerStearilliumReactor container;

	public GuiStearilliumReactor( ContainerStearilliumReactor containerZoroChest ) {
		super(containerZoroChest);
		container = containerZoroChest;
		xSize = 176;
		ySize = 202;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer( float par1, int par2, int par3 ) {
		// draw your Gui here, only thing you need to change is the path
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		mc.renderEngine.bindTexture("/mods/zonus/textures/gui/reactorgui.png");
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		drawGaugeBg(x, y, 16, 128);
		if (container.tileEntity.getScaledCoolant(58) > 0) {
			displayGauge(x, y, 16, 128, container.tileEntity.getScaledCoolant(58), container.tileEntity.getCoolant());
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int param1, int param2 ) {
		// fontRenderer.drawString("Zoro Chest", 12, 12, 4210752);
		// fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"),
		// 8, ySize - 96 + 2, 4210752);
		super.drawGuiContainerForegroundLayer(param1, param2);
	}

	@Override
	protected void drawTooltips() {
		if (isHovering(126, 145, 14, 75)) {
			drawToolTip(container.tileEntity.getCoolant().amount+" / 10000 Mb");

		}
	}

	public boolean handleMouseClicked( int x, int y, int mouseButton ) {

		return true;
	}

	@Override
	protected void mouseClicked( int x, int y, int mouseButton ) {
		super.mouseClicked(x, y, mouseButton);
		handleMouseClicked(mousex, mousey, mouseButton);
	}

}