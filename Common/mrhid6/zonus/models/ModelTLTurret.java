package mrhid6.zonus.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelTLTurret extends ModelBase {

	ModelRenderer Axle;
	// fields
	public ModelRenderer Base;
	ModelRenderer LaserBase;
	ModelRenderer LaserBase1;
	ModelRenderer LaserEnd;
	ModelRenderer LaserExtra1;
	ModelRenderer LaserExtra2;
	ModelRenderer LaserTurret;
	ModelRenderer Stand1;
	ModelRenderer Stand2;

	public ModelTLTurret() {
		textureWidth = 128;
		textureHeight = 64;

		Base = new ModelRenderer(this, 0, 0);
		Base.addBox(-7F, -2F, -7F, 14, 2, 14);
		Base.setRotationPoint(0F, 24F, 0F);
		Base.setTextureSize(128, 64);
		Base.mirror = true;
		setRotation(Base, 0F, 0F, 0F);
		Stand1 = new ModelRenderer(this, 0, 16);
		Stand1.addBox(-0.5F, -9F, -2F, 1, 9, 4);
		Stand1.setRotationPoint(5F, 22F, 0F);
		Stand1.setTextureSize(128, 64);
		Stand1.mirror = true;
		setRotation(Stand1, 0F, 0F, 0F);
		Stand2 = new ModelRenderer(this, 0, 16);
		Stand2.addBox(-0.5F, -9F, -2F, 1, 9, 4);
		Stand2.setRotationPoint(-5F, 22F, 0F);
		Stand2.setTextureSize(128, 64);
		Stand2.mirror = true;
		setRotation(Stand2, 0F, 0F, 0F);
		LaserBase = new ModelRenderer(this, 10, 16);
		LaserBase.addBox(-4F, -4F, -3F, 8, 8, 6);
		LaserBase.setRotationPoint(0F, 15F, 0F);
		LaserBase.setTextureSize(128, 64);
		LaserBase.mirror = true;
		setRotation(LaserBase, 0F, 0F, 0F);
		Axle = new ModelRenderer(this, 38, 27);
		Axle.addBox(-7F, -1F, -1F, 14, 2, 2);
		Axle.setRotationPoint(0F, 15F, 0F);
		Axle.setTextureSize(128, 64);
		Axle.mirror = true;
		setRotation(Axle, 0F, 0F, 0F);
		LaserBase1 = new ModelRenderer(this, 38, 16);
		LaserBase1.addBox(-3F, -3F, -8F, 6, 6, 5);
		LaserBase1.setRotationPoint(0F, 15F, 0F);
		LaserBase1.setTextureSize(128, 64);
		LaserBase1.mirror = true;
		setRotation(LaserBase1, 0F, 0F, 0F);
		LaserTurret = new ModelRenderer(this, 56, 0);
		LaserTurret.addBox(-1F, -1F, -21F, 2, 2, 13);
		LaserTurret.setRotationPoint(0F, 15F, 0F);
		LaserTurret.setTextureSize(128, 64);
		LaserTurret.mirror = true;
		setRotation(LaserTurret, 0F, 0F, 0F);
		LaserEnd = new ModelRenderer(this, 0, 29);
		LaserEnd.addBox(-1.5F, -1.5F, -24F, 3, 3, 3);
		LaserEnd.setRotationPoint(0F, 15F, 0F);
		LaserEnd.setTextureSize(128, 64);
		LaserEnd.mirror = true;
		setRotation(LaserEnd, 0F, 0F, 0F);
		LaserExtra2 = new ModelRenderer(this, 0, 35);
		LaserExtra2.addBox(-2F, -2F, -16F, 4, 4, 1);
		LaserExtra2.setRotationPoint(0F, 15F, 0F);
		LaserExtra2.setTextureSize(128, 64);
		LaserExtra2.mirror = true;
		setRotation(LaserExtra2, 0F, 0F, 0F);
		LaserExtra1 = new ModelRenderer(this, 0, 40);
		LaserExtra1.addBox(-2.5F, -2.5F, -11F, 5, 5, 1);
		LaserExtra1.setRotationPoint(0F, 15F, 0F);
		LaserExtra1.setTextureSize(128, 64);
		LaserExtra1.mirror = true;
		setRotation(LaserExtra1, 0F, 0F, 0F);
	}

	public void renderBase() {

		Base.render(0.0625F);
		Stand1.render(0.0625F);
		Stand2.render(0.0625F);
	}

	public void renderTurret( float f ) {
		LaserBase.render(0.0625F);
		Axle.render(0.0625F);
		LaserBase1.render(0.0625F);
		LaserTurret.render(0.0625F);
		LaserEnd.render(0.0625F);
		LaserExtra2.render(0.0625F);
		LaserExtra1.render(0.0625F);
	}

	public void setRotation( ModelRenderer model, float x, float y, float z ) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
