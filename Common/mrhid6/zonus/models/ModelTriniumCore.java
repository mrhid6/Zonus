package mrhid6.zonus.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelTriniumCore extends ModelBase {

	ModelRenderer Base;
	ModelRenderer Inner;

	public ModelTriniumCore() {
		textureWidth = 128;
		textureHeight = 64;

		Base = new ModelRenderer(this, 0, 0);
		Base.addBox(-8F, 0, -8F, 16, 16, 16);
		Base.setRotationPoint(0F, 8F, 0F);
		Base.setTextureSize(128, 64);
		Base.mirror = true;
		setRotation(Base, 0F, 0F, 0F);
		Inner = new ModelRenderer(this, 0, 32);
		Inner.addBox(-8F, 0, -8F, 16, 16, 16);
		Inner.setRotationPoint(0F, 8F, 0F);
		Inner.setTextureSize(128, 64);
		Inner.mirror = true;
		setRotation(Inner, 0F, 0F, 0F);
	}

	public void render() {
		Base.render(0.0625F);
	}

	public void renderInner() {
		Inner.render(0.0625F);
	}

	private void setRotation( ModelRenderer model, float x, float y, float z ) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
