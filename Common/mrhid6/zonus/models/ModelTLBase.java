package mrhid6.zonus.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelTLBase extends ModelBase {

	ModelRenderer Base;
	ModelRenderer InnerBox;
	// fields
	ModelRenderer Leg1;
	ModelRenderer Leg2;
	ModelRenderer Leg3;
	ModelRenderer Leg4;
	ModelRenderer TopBase;
	ModelRenderer TopTop;

	public ModelTLBase() {
		textureWidth = 128;
		textureHeight = 64;

		Leg1 = new ModelRenderer(this, 0, 30);
		Leg1.addBox(-2F, 0F, -2F, 4, 12, 4);
		Leg1.setRotationPoint(4F, 11F, 4F);
		Leg1.setTextureSize(128, 64);
		Leg1.mirror = true;
		setRotation(Leg1, 0F, 0F, 0F);
		Leg2 = new ModelRenderer(this, 0, 30);
		Leg2.addBox(-2F, 0F, -2F, 4, 12, 4);
		Leg2.setRotationPoint(-4F, 11F, 4F);
		Leg2.setTextureSize(128, 64);
		Leg2.mirror = true;
		setRotation(Leg2, 0F, 0F, 0F);
		Leg3 = new ModelRenderer(this, 0, 30);
		Leg3.addBox(-2F, 0F, -2F, 4, 12, 4);
		Leg3.setRotationPoint(-4F, 11F, -4F);
		Leg3.setTextureSize(128, 64);
		Leg3.mirror = true;
		setRotation(Leg3, 0F, 0F, 0F);
		Leg4 = new ModelRenderer(this, 0, 30);
		Leg4.addBox(-2F, 0F, -2F, 4, 12, 4);
		Leg4.setRotationPoint(4F, 11F, -4F);
		Leg4.setTextureSize(128, 64);
		Leg4.mirror = true;
		setRotation(Leg4, 0F, 0F, 0F);
		Base = new ModelRenderer(this, 0, 15);
		Base.addBox(-8F, -1F, -8F, 14, 1, 14);
		Base.setRotationPoint(1F, 24F, 1F);
		Base.setTextureSize(128, 64);
		Base.mirror = true;
		setRotation(Base, 0F, 0F, 0F);
		TopBase = new ModelRenderer(this, 0, 0);
		TopBase.addBox(-8F, -1F, -8F, 14, 1, 14);
		TopBase.setRotationPoint(1F, 11F, 1F);
		TopBase.setTextureSize(128, 64);
		TopBase.mirror = true;
		setRotation(TopBase, 0F, 0F, 0F);
		TopTop = new ModelRenderer(this, 56, 0);
		TopTop.addBox(-4F, -2F, -4F, 8, 2, 8);
		TopTop.setRotationPoint(0F, 10F, 0F);
		TopTop.setTextureSize(128, 64);
		TopTop.mirror = true;
		setRotation(TopTop, 0F, 0F, 0F);
		InnerBox = new ModelRenderer(this, 56, 10);
		InnerBox.addBox(-3F, -6F, -3F, 6, 12, 6);
		InnerBox.setRotationPoint(0F, 17F, 0F);
		InnerBox.setTextureSize(128, 64);
		InnerBox.mirror = true;
		setRotation(InnerBox, 0F, 0F, 0F);
	}

	public void render() {
		Leg1.render(0.0625F);
		Leg2.render(0.0625F);
		Leg3.render(0.0625F);
		Leg4.render(0.0625F);
		Base.render(0.0625F);
		TopBase.render(0.0625F);
		TopTop.render(0.0625F);
		InnerBox.render(0.0625F);
	}

	private void setRotation( ModelRenderer model, float x, float y, float z ) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
