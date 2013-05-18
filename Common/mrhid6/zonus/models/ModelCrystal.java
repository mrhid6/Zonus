package mrhid6.zonus.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCrystal extends ModelBase
{
	//fields
	ModelRenderer Crystal;

	public ModelCrystal()
	{
		textureWidth = 64;
		textureHeight = 32;

		Crystal = new ModelRenderer(this, 0, 0);
		Crystal.addBox(-16F, -16F, 0F, 16, 16, 16);
		Crystal.setRotationPoint(0F, 32F, 0F);
		Crystal.setTextureSize(64, 32);
		Crystal.mirror = true;
		setRotation(Crystal, 0.7071F, 0F, 0.7071F);
	}
	
	public void render(){
		Crystal.render(0.0625F);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
