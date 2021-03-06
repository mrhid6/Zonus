// Date: 18/05/2013 08:36:47
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package mrhid6.zonus.models;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCrystalHolder extends ModelBase
{
	//fields
	ModelRenderer top4;
	ModelRenderer top1;
	ModelRenderer top3;
	ModelRenderer top2;
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;

	public ModelCrystalHolder()
	{
		textureWidth = 128;
		textureHeight = 64;

		top4 = new ModelRenderer(this, 0, 0);
		top4.addBox(0F, 0F, 0F, 3, 1, 3);
		top4.setRotationPoint(5F, 8F, 5F);
		top4.setTextureSize(128, 64);
		top4.mirror = true;
		setRotation(top4, 0F, 0F, 0F);
		top1 = new ModelRenderer(this, 0, 0);
		top1.addBox(0F, 0F, 0F, 3, 1, 3);
		top1.setRotationPoint(5F, 8F, -8F);
		top1.setTextureSize(128, 64);
		top1.mirror = true;
		setRotation(top1, 0F, 0F, 0F);
		top3 = new ModelRenderer(this, 0, 0);
		top3.addBox(0F, 0F, 0F, 3, 1, 3);
		top3.setRotationPoint(-8F, 8F, 5F);
		top3.setTextureSize(128, 64);
		top3.mirror = true;
		setRotation(top3, 0F, 0F, 0F);
		top2 = new ModelRenderer(this, 0, 0);
		top2.addBox(0F, 0F, 0F, 3, 1, 3);
		top2.setRotationPoint(-8F, 8F, -8F);
		top2.setTextureSize(128, 64);
		top2.mirror = true;
		setRotation(top2, 0F, 0F, 0F);
		Shape1 = new ModelRenderer(this, 0, 22);
		Shape1.addBox(0F, 0F, 0F, 16, 6, 16);
		Shape1.setRotationPoint(-8F, 18F, -8F);
		Shape1.setTextureSize(128, 64);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 0);
		Shape2.addBox(0F, 0F, 0F, 16, 6, 16);
		Shape2.setRotationPoint(-8F, 9F, -8F);
		Shape2.setTextureSize(128, 64);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 48, 0);
		Shape3.addBox(0F, 0F, 0F, 10, 3, 3);
		Shape3.setRotationPoint(-5F, 15F, 5F);
		Shape3.setTextureSize(128, 64);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 54, 12);
		Shape4.addBox(0F, 0F, 0F, 3, 3, 10);
		Shape4.setRotationPoint(-8F, 15F, -5F);
		Shape4.setTextureSize(128, 64);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 54, 12);
		Shape5.addBox(0F, 0F, 0F, 3, 3, 10);
		Shape5.setRotationPoint(5F, 15F, -5F);
		Shape5.setTextureSize(128, 64);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 48, 0);
		Shape6.addBox(0F, 0F, 0F, 10, 3, 3);
		Shape6.setRotationPoint(-5F, 15F, -8F);
		Shape6.setTextureSize(128, 64);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0F, 0F);
	}

	public void render()
	{

		top4.render(0.0625F);
		top1.render(0.0625F);
		top3.render(0.0625F);
		top2.render(0.0625F);
		Shape1.render(0.0625F);
		Shape2.render(0.0625F);
		GL11.glPushMatrix();
		{
			GL11.glTranslatef(0, -0.01002F, 0);
			GL11.glScalef(1, 1.01F, 1);
			Shape3.render(0.0625F);
			Shape4.render(0.0625F);
			Shape5.render(0.0625F);
			Shape6.render(0.0625F);
			GL11.glScalef(1, 1, 1);
		}
		GL11.glPopMatrix();
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}


}
