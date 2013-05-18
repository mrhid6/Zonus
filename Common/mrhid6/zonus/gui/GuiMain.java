package mrhid6.zonus.gui;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraftforge.liquids.LiquidStack;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class GuiMain extends GuiContainer {

	protected int mousex;
	protected int mousey;

	public GuiMain( Container par1Container ) {
		super(par1Container);
	}

	protected void displayGauge( int j, int k, int line, int col, int squaled, LiquidStack liquid ) {

		if (liquid == null) {
			return;
		}
		int start = 0;

		Icon liquidIcon;
		String textureSheet;
		if (liquid.canonical().getRenderingIcon() != null) {
			textureSheet = liquid.canonical().getTextureSheet();
			liquidIcon = liquid.canonical().getRenderingIcon();
		} else {
			if (liquid.itemID < Block.blocksList.length && Block.blocksList[liquid.itemID].blockID > 0) {
				liquidIcon = Block.blocksList[liquid.itemID].getBlockTextureFromSide(0);
				textureSheet = "/terrain.png";
			} else {
				liquidIcon = Item.itemsList[liquid.itemID].getIconFromDamage(liquid.itemMeta);
				textureSheet = "/gui/items.png";
			}
		}
		mc.renderEngine.bindTexture(textureSheet);

		while (true) {
			int x = 0;

			if (squaled > 16) {
				x = 16;
				squaled -= 16;
			} else {
				x = squaled;
				squaled = 0;
			}

			drawTexturedModelRectFromIcon(j + col, k + line + 58 - x - start, liquidIcon, 16, 16 - (16 - x));
			start = start + 16;

			if (x == 0 || squaled == 0) {
				break;
			}
		}

		mc.renderEngine.bindTexture("/mods/zonus/textures/gui/tankgui.png");
		drawTexturedModalRect(j + col, k + line - 1, 18, 0, 16, 60);
	}

	public void drawColouredIcon( String texture, int x, int y, boolean hover, int color ) {
		drawIcon(texture, 0, x, y, hover);
		drawIcon(texture, 240 + color, x, y, false);
	}

	protected void drawGaugeBg( int j, int k, int line, int col ) {
		mc.renderEngine.bindTexture("/mods/zonus/textures/gui/tankgui.png");
		drawTexturedModalRect(j + col - 1, k + line - 1, 0, 0, 18, 60);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer( float var1, int var2, int var3 ) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void drawGuiContainerForegroundLayer( int par1, int par2 ) {

		GL11.glDisable(2896);
		GL11.glDisable(2929);

		drawTooltips();

		GL11.glEnable(2896);
		GL11.glEnable(2929);
	}

	public void drawIcon( String texture, int iconIndex, int x, int y, boolean hover ) {
		if (hover) {
			iconIndex++;
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		mc.renderEngine.bindTexture(texture);
		int textureRow = iconIndex >> 4;
		int textureColumn = iconIndex - 16 * textureRow;

		drawTexturedModalRect(x, y, 16 * textureColumn, 16 * textureRow, 16, 16);
	}

	public void drawToolTip( String text ) {
		drawCreativeTabHoveringText(text, mousex, mousey);
	}

	protected abstract void drawTooltips();

	@Override
	public void handleMouseInput() {

		int x = Mouse.getEventX() * width / mc.displayWidth;
		int y = height - Mouse.getEventY() * height / mc.displayHeight - 1;

		mousex = (x - (width - xSize) / 2);
		mousey = (y - (height - ySize) / 2);

		super.handleMouseInput();
	}

	public boolean isHovering( int minX, int maxX, int minY, int maxY ) {
		return (mousex > minX && mousex < maxX && mousey > minY && mousey < maxY);
	}

}
