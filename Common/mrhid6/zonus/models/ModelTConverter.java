package mrhid6.zonus.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelTConverter extends ModelBase {

	// fields
	// fields
	ModelRenderer Base;
	ModelRenderer Core1;
	ModelRenderer Core2;
	ModelRenderer Core3;
	ModelRenderer Core4;
	ModelRenderer Glass;
	ModelRenderer IS1a;
	ModelRenderer IS1b;
	ModelRenderer IS1c;
	ModelRenderer IS1d;
	ModelRenderer IS2a;
	ModelRenderer IS2b;
	ModelRenderer IS2c;
	ModelRenderer IS2d;
	ModelRenderer IS3a;
	ModelRenderer IS3b;
	ModelRenderer IS3c;
	ModelRenderer IS3d;
	ModelRenderer IS4a;
	ModelRenderer IS4b;
	ModelRenderer IS4c;
	ModelRenderer IS4d;

	public ModelTConverter() {

		textureWidth = 64;
		textureHeight = 64;

		textureWidth = 64;
		textureHeight = 64;

		Base = new ModelRenderer(this, 0, 0);
		Base.addBox(-8F, 0F, -8F, 16, 1, 16);
		Base.setRotationPoint(0F, 23F, 0F);
		Base.setTextureSize(64, 64);
		Base.mirror = true;
		setRotation(Base, 0F, 0F, 0F);
		Glass = new ModelRenderer(this, 0, 17);
		Glass.addBox(-6F, 0F, -6F, 12, 13, 12);
		Glass.setRotationPoint(0F, 10F, 0F);
		Glass.setTextureSize(64, 64);
		Glass.mirror = true;
		setRotation(Glass, 0F, 0F, 0F);

		IS4c = new ModelRenderer(this, 48, 17);
		IS4c.addBox(0F, 0F, 0F, 1, 6, 1);
		IS4c.setRotationPoint(2F, 13F, 7F);
		IS4c.setTextureSize(64, 64);
		IS4c.mirror = true;
		setRotation(IS4c, 0F, 0F, 0F);
		IS4b = new ModelRenderer(this, 48, 24);
		IS4b.addBox(0F, 0F, 0F, 4, 1, 1);
		IS4b.setRotationPoint(-2F, 18F, 7F);
		IS4b.setTextureSize(64, 64);
		IS4b.mirror = true;
		setRotation(IS4b, 0F, 0F, 0F);
		IS4a = new ModelRenderer(this, 48, 17);
		IS4a.addBox(0F, 0F, 0F, 1, 6, 1);
		IS4a.setRotationPoint(-3F, 13F, 7F);
		IS4a.setTextureSize(64, 64);
		IS4a.mirror = true;
		setRotation(IS4a, 0F, 0F, 0F);
		IS4d = new ModelRenderer(this, 48, 24);
		IS4d.addBox(0F, 0F, 0F, 4, 1, 1);
		IS4d.setRotationPoint(-2F, 13F, 7F);
		IS4d.setTextureSize(64, 64);
		IS4d.mirror = true;
		setRotation(IS4d, 0F, 0F, 0F);
		IS1a = new ModelRenderer(this, 48, 17);
		IS1a.addBox(0F, 0F, 0F, 1, 6, 1);
		IS1a.setRotationPoint(7F, 13F, -3F);
		IS1a.setTextureSize(64, 64);
		IS1a.mirror = true;
		setRotation(IS1a, 0F, 0F, 0F);
		IS1b = new ModelRenderer(this, 52, 18);
		IS1b.addBox(0F, 0F, 0F, 1, 1, 4);
		IS1b.setRotationPoint(7F, 18F, -2F);
		IS1b.setTextureSize(64, 64);
		IS1b.mirror = true;
		setRotation(IS1b, 0F, 0F, 0F);
		IS1c = new ModelRenderer(this, 48, 17);
		IS1c.addBox(0F, 0F, 0F, 1, 6, 1);
		IS1c.setRotationPoint(7F, 13F, 2F);
		IS1c.setTextureSize(64, 64);
		IS1c.mirror = true;
		setRotation(IS1c, 0F, 0F, 0F);
		IS1d = new ModelRenderer(this, 52, 18);
		IS1d.addBox(0F, 0F, 0F, 1, 1, 4);
		IS1d.setRotationPoint(7F, 13F, -2F);
		IS1d.setTextureSize(64, 64);
		IS1d.mirror = true;
		setRotation(IS1d, 0F, 0F, 0F);
		IS2d = new ModelRenderer(this, 52, 18);
		IS2d.addBox(0F, 0F, 0F, 1, 1, 4);
		IS2d.setRotationPoint(-8F, 13F, -2F);
		IS2d.setTextureSize(64, 64);
		IS2d.mirror = true;
		setRotation(IS2d, 0F, 0F, 0F);
		IS2c = new ModelRenderer(this, 48, 17);
		IS2c.addBox(0F, 0F, 0F, 1, 6, 1);
		IS2c.setRotationPoint(-8F, 13F, -3F);
		IS2c.setTextureSize(64, 64);
		IS2c.mirror = true;
		setRotation(IS2c, 0F, 0F, 0F);
		IS2b = new ModelRenderer(this, 52, 18);
		IS2b.addBox(0F, 0F, 0F, 1, 1, 4);
		IS2b.setRotationPoint(-8F, 18F, -2F);
		IS2b.setTextureSize(64, 64);
		IS2b.mirror = true;
		setRotation(IS2b, 0F, 0F, 0F);
		IS2a = new ModelRenderer(this, 48, 17);
		IS2a.addBox(0F, 0F, 0F, 1, 6, 1);
		IS2a.setRotationPoint(-8F, 13F, 2F);
		IS2a.setTextureSize(64, 64);
		IS2a.mirror = true;
		setRotation(IS2a, 0F, 0F, 0F);
		IS3c = new ModelRenderer(this, 48, 17);
		IS3c.addBox(0F, 0F, 0F, 1, 6, 1);
		IS3c.setRotationPoint(2F, 13F, -8F);
		IS3c.setTextureSize(64, 64);
		IS3c.mirror = true;
		setRotation(IS3c, 0F, 0F, 0F);
		IS3b = new ModelRenderer(this, 48, 24);
		IS3b.addBox(0F, 0F, 0F, 4, 1, 1);
		IS3b.setRotationPoint(-2F, 18F, -8F);
		IS3b.setTextureSize(64, 64);
		IS3b.mirror = true;
		setRotation(IS3b, 0F, 0F, 0F);
		IS3a = new ModelRenderer(this, 48, 17);
		IS3a.addBox(0F, 0F, 0F, 1, 6, 1);
		IS3a.setRotationPoint(-3F, 13F, -8F);
		IS3a.setTextureSize(64, 64);
		IS3a.mirror = true;
		setRotation(IS3a, 0F, 0F, 0F);
		IS3d = new ModelRenderer(this, 48, 24);
		IS3d.addBox(0F, 0F, 0F, 4, 1, 1);
		IS3d.setRotationPoint(-2F, 13F, -8F);
		IS3d.setTextureSize(64, 64);
		IS3d.mirror = true;
		setRotation(IS3d, 0F, 0F, 0F);

		Core1 = new ModelRenderer(this, 0, 42);
		Core1.addBox(0F, 0F, -2F, 9, 4, 4);
		Core1.setRotationPoint(-2F, 14F, 0F);
		Core1.setTextureSize(64, 64);
		Core1.mirror = true;
		setRotation(Core1, 0F, 0F, 0F);
		Core2 = new ModelRenderer(this, 0, 42);
		Core2.addBox(-9F, 0F, -2F, 9, 4, 4);
		Core2.setRotationPoint(2F, 14F, 0F);
		Core2.setTextureSize(64, 64);
		Core2.mirror = true;
		setRotation(Core2, 0F, 0F, 0F);
		Core3 = new ModelRenderer(this, 26, 42);
		Core3.addBox(-2F, 0F, -9F, 4, 4, 9);
		Core3.setRotationPoint(0F, 14F, 2F);
		Core3.setTextureSize(64, 64);
		Core3.mirror = true;
		setRotation(Core3, 0F, 0F, 0F);
		Core4 = new ModelRenderer(this, 26, 42);
		Core4.addBox(-2F, 0F, 0F, 4, 4, 9);
		Core4.setRotationPoint(0F, 14F, -2F);
		Core4.setTextureSize(64, 64);
		Core4.mirror = true;
		setRotation(Core4, 0F, 0F, 0F);

	}

	public void render() {
		Base.render(0.0625F);
		Glass.render(0.0625F);

	}

	public void renderAll() {
		render();
		renderNorth();
		renderSouth();
		renderEast();
		renderWest();
	}

	public void renderEast() {
		IS1a.render(0.0625F);
		IS1b.render(0.0625F);
		Core1.render(0.0625F);
		IS1c.render(0.0625F);
		IS1d.render(0.0625F);
	}

	public void renderNorth() {
		IS4c.render(0.0625F);
		IS4b.render(0.0625F);
		Core4.render(0.0625F);
		IS4a.render(0.0625F);
		IS4d.render(0.0625F);
	}

	public void renderSouth() {
		Core3.render(0.0625F);
		IS3c.render(0.0625F);
		IS3b.render(0.0625F);
		IS3a.render(0.0625F);
		IS3d.render(0.0625F);
	}

	public void renderTop() {

	}

	public void renderWest() {
		IS2d.render(0.0625F);
		IS2c.render(0.0625F);
		IS2b.render(0.0625F);
		IS2a.render(0.0625F);
		Core2.render(0.0625F);
	}

	private void setRotation( ModelRenderer model, float x, float y, float z ) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
