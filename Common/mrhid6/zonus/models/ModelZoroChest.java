package mrhid6.zonus.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelZoroChest extends ModelBase {

	/** The model of the bottom of the chest. */
	public ModelRenderer chestBelow;

	/** The chest's knob in the chest model. */
	public ModelRenderer chestKnob;

	/** The chest lid in the chest's model. */
	public ModelRenderer chestLid = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);

	public ModelZoroChest() {
		chestLid.addBox(0.0F, -5.0F, -16.0F, 16, 5, 16, 0.0F);
		chestLid.rotationPointX = 0.0F;
		chestLid.rotationPointY = 5.0F;
		chestLid.rotationPointZ = 16.0F;
		chestKnob = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
		chestKnob.addBox(-1.0F, -2.0F, -16.0F, 2, 4, 1, 0.0F);
		chestKnob.rotationPointX = 8.0F;
		chestKnob.rotationPointY = 6.0F;
		chestKnob.rotationPointZ = 15.0F;
		chestBelow = (new ModelRenderer(this, 0, 21)).setTextureSize(64, 64);
		chestBelow.addBox(0.0F, 0.0F, 0.0F, 16, 11, 16, 0.0F);
		chestBelow.rotationPointX = 0.0F;
		chestBelow.rotationPointY = 5.0F;
		chestBelow.rotationPointZ = 0.0F;
	}

	/**
	 * This method renders out all parts of the chest model.
	 */
	public void renderAll() {
		chestKnob.rotateAngleX = chestLid.rotateAngleX;
		chestLid.render(0.0625F);
		chestKnob.render(0.0625F);
		chestBelow.render(0.0625F);
	}
}
