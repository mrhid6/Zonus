package mrhid6.zonus.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelMiner extends ModelBase {

	ModelRenderer blade1;

	ModelRenderer blade2;
	ModelRenderer blade3;
	ModelRenderer blade4;
	// fields
	ModelRenderer Shape1;

	public ModelMiner() {
		textureWidth = 128;
		textureHeight = 64;

		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(-3F, 0F, -3F, 6, 16, 6);
		Shape1.setRotationPoint(0F, 8F, 0F);
		Shape1.setTextureSize(128, 64);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);

		blade1 = new ModelRenderer(this, 0, 0);
		blade1.addBox(-3F, 15F, -48F, 6, 1, 48);
		blade1.setRotationPoint(0F, 8F, 0F);
		blade1.setTextureSize(128, 64);
		blade1.mirror = true;
		setRotation(blade1, 0F, 0F, 0F);

		blade2 = new ModelRenderer(this, 0, 0);
		blade2.addBox(0F, 15F, -3F, 48, 1, 6);
		blade2.setRotationPoint(0F, 8F, 0F);
		blade2.setTextureSize(128, 64);
		blade2.mirror = true;
		setRotation(blade2, 0F, 0F, 0F);

		blade3 = new ModelRenderer(this, 0, 0);
		blade3.addBox(-48F, 15F, -3F, 48, 1, 6);
		blade3.setRotationPoint(0F, 8F, 0F);
		blade3.setTextureSize(128, 64);
		blade3.mirror = true;
		setRotation(blade3, 0F, 0F, 0F);

		blade4 = new ModelRenderer(this, 0, 0);
		blade4.addBox(-3F, 15F, 0F, 6, 1, 48);
		blade4.setRotationPoint(0F, 8F, 0F);
		blade4.setTextureSize(128, 64);
		blade4.mirror = true;
		setRotation(blade4, 0F, 0F, 0F);
	}

	@Override
	public void render( Entity entity, float f, float f1, float f2, float f3, float f4, float f5 ) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		Shape1.render(0.0625F);
		blade1.render(0.0625F);
		blade2.render(0.0625F);
		blade3.render(0.0625F);
		blade4.render(0.0625F);
	}

	public void renderArm( double x, double y, double z ) {

		GL11.glTranslated(x + 0.5F, y - 1.5F, z + 0.5F);
		Shape1.render(0.0625F);
	}

	public void renderBlades( double x, double y, double z ) {

		GL11.glTranslated(x + 0.5F, y - 1.5F, z + 0.5F);
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
