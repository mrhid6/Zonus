package mrhid6.zonus.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelGrinder extends ModelBase {

	ModelRenderer blade1, blade2, blade3, blade4;
	// fields
	ModelRenderer Shape1, Shape2;

	public ModelGrinder() {
		textureWidth = 128;
		textureHeight = 64;

		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(-8F, -16F, -8F, 16, 16, 16);
		Shape1.setRotationPoint(0F, 24F, 0F);
		Shape1.setTextureSize(128, 64);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 32);
		Shape2.addBox(-8F, 0F, -8F, 16, 0, 16);
		Shape2.setRotationPoint(0F, 23.5F, 0F);
		Shape2.setTextureSize(128, 64);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		blade1 = new ModelRenderer(this, 65, 32);
		blade1.addBox(0F, 0F, -0.5F, 5, 0, 1);
		blade1.setRotationPoint(0F, 8.5F, 0F);
		blade1.setTextureSize(128, 64);
		blade1.mirror = true;
		setRotation(blade1, 0F, 0F, 0F);
		blade2 = new ModelRenderer(this, 65, 32);
		blade2.addBox(-5F, 0F, -0.5F, 5, 0, 1);
		blade2.setRotationPoint(0F, 8.5F, 0F);
		blade2.setTextureSize(128, 64);
		blade2.mirror = true;
		setRotation(blade2, 0F, 0F, 0F);
		blade3 = new ModelRenderer(this, 65, 26);
		blade3.addBox(-0.5F, 0F, 0F, 1, 0, 5);
		blade3.setRotationPoint(0F, 8.52F, 0F);
		blade3.setTextureSize(128, 64);
		blade3.mirror = true;
		setRotation(blade3, 0F, 0F, 0F);
		blade4 = new ModelRenderer(this, 65, 26);
		blade4.addBox(-0.5F, 0F, -5F, 1, 0, 5);
		blade4.setRotationPoint(0F, 8.52F, 0F);
		blade4.setTextureSize(128, 64);
		blade4.mirror = true;
		setRotation(blade4, 0F, 0F, 0F);
	}

	public void render( boolean isActive ) {
		Shape1.render(0.0625F);
		Shape2.render(0.0625F);
	}

	public void renderBlades() {
		blade1.render(0.0625F);
		blade2.render(0.0625F);
		blade3.render(0.0625F);
		blade4.render(0.0625F);
	}

	private void setRotation( ModelRenderer model, float x, float y, float z ) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
