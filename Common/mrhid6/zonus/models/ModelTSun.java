package mrhid6.zonus.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelTSun extends ModelBase {

	ModelRenderer Laser1, Laser2, Laser3, Laser4, Laser5, Laser6;
	// fields
	ModelRenderer Shape1;

	ModelRenderer Shape2;

	public ModelTSun() {
		textureWidth = 128;
		textureHeight = 64;

		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(-8F, -2F, -8F, 16, 16, 16);
		Shape1.setRotationPoint(0F, 10F, 0F);
		Shape1.setTextureSize(128, 64);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 0);
		Shape2.addBox(-2F, -2F, -2F, 4, 4, 4);
		Shape2.setRotationPoint(0F, 16F, 0F);
		Shape2.setTextureSize(128, 64);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);

		Laser1 = new ModelRenderer(this, 0, 8);
		Laser1.addBox(0F, -0.5F, -0.5F, 7, 1, 1);
		Laser1.setRotationPoint(0F, 16F, 0F);
		Laser1.setTextureSize(128, 64);
		Laser1.mirror = true;
		setRotation(Laser1, 0F, 0F, 0F);
		Laser2 = new ModelRenderer(this, 0, 8);
		Laser2.addBox(-7F, -0.5F, -0.5F, 7, 1, 1);
		Laser2.setRotationPoint(0F, 16F, 0F);
		Laser2.setTextureSize(128, 64);
		Laser2.mirror = true;
		setRotation(Laser2, 0F, 0F, 0F);
		Laser3 = new ModelRenderer(this, 48, 0);
		Laser3.addBox(-0.5F, 0F, -0.5F, 1, 7, 1);
		Laser3.setRotationPoint(0F, 16F, 0F);
		Laser3.setTextureSize(128, 64);
		Laser3.mirror = true;
		setRotation(Laser3, 0F, 0F, 0F);
		Laser4 = new ModelRenderer(this, 48, 0);
		Laser4.addBox(-0.5F, -7F, -0.5F, 1, 7, 1);
		Laser4.setRotationPoint(0F, 16F, 0F);
		Laser4.setTextureSize(128, 64);
		Laser4.mirror = true;
		setRotation(Laser4, 0F, 0F, 0F);
		Laser5 = new ModelRenderer(this, 48, 8);
		Laser5.addBox(-0.5F, -0.5F, 0F, 1, 1, 7);
		Laser5.setRotationPoint(0F, 16F, 0F);
		Laser5.setTextureSize(128, 64);
		Laser5.mirror = true;
		setRotation(Laser5, 0F, 0F, 0F);
		Laser6 = new ModelRenderer(this, 48, 8);
		Laser6.addBox(-0.5F, -0.5F, -7F, 1, 1, 7);
		Laser6.setRotationPoint(0F, 16F, 0F);
		Laser6.setTextureSize(128, 64);
		Laser6.mirror = true;
		setRotation(Laser6, 0F, 0F, 0F);
	}

	public void render() {
		Shape1.render(0.0625F);
		// renderLasers(sides, active);
	}

	public void renderInner() {
		Shape2.render(0.0625F);
	}

	public void renderLasers( boolean[] sides, boolean active ) {
		if (!active) {
			return;
		}

		if (sides[0]) {
			Laser4.render(0.0625F);
		}
		if (sides[1]) {
			Laser3.render(0.0625F);
		}
		if (sides[2]) {
			Laser6.render(0.0625F);
		}
		if (sides[3]) {
			Laser5.render(0.0625F);
		}
		if (sides[4]) {
			Laser2.render(0.0625F);
		}
		if (sides[5]) {
			Laser1.render(0.0625F);
		}
	}

	private void setRotation( ModelRenderer model, float x, float y, float z ) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
