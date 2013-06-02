
package mrhid6.zonus.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBottle extends ModelBase
{
  //fields
    ModelRenderer cap;
    ModelRenderer base1;
    ModelRenderer base2;
    ModelRenderer base3;
    ModelRenderer base4;
    ModelRenderer base5;
    ModelRenderer liquid;
  
  public ModelBottle()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      cap = new ModelRenderer(this, 0, 0);
      cap.addBox(-1F, 0F, -1F, 2, 2, 2);
      cap.setRotationPoint(0F, 11F, 0F);
      cap.setTextureSize(128, 64);
      cap.mirror = true;
      setRotation(cap, 0F, 0F, 0F);
      base1 = new ModelRenderer(this, 32, 0);
      base1.addBox(0F, 0F, 0F, 4, 4, 4);
      base1.setRotationPoint(-2F, 12F, -2F);
      base1.setTextureSize(128, 64);
      base1.mirror = true;
      setRotation(base1, 0F, 0F, 0F);
      base2 = new ModelRenderer(this, 0, 14);
      base2.addBox(-3F, 0F, -3F, 6, 1, 6);
      base2.setRotationPoint(0F, 13F, 0F);
      base2.setTextureSize(128, 64);
      base2.mirror = true;
      setRotation(base2, 0F, 0F, 0F);
      base3 = new ModelRenderer(this, 0, 14);
      base3.addBox(-3F, 0F, -3F, 6, 1, 6);
      base3.setRotationPoint(0F, 16F, 0F);
      base3.setTextureSize(128, 64);
      base3.mirror = true;
      setRotation(base3, 0F, 0F, 0F);
      base4 = new ModelRenderer(this, 0, 0);
      base4.addBox(-4F, 0F, -4F, 8, 6, 8);
      base4.setRotationPoint(0F, 17F, 0F);
      base4.setTextureSize(128, 64);
      base4.mirror = true;
      setRotation(base4, 0F, 0F, 0F);
      base5 = new ModelRenderer(this, 0, 14);
      base5.addBox(-3F, 0F, -3F, 6, 1, 6);
      base5.setRotationPoint(0F, 23F, 0F);
      base5.setTextureSize(128, 64);
      base5.mirror = true;
      setRotation(base5, 0F, 0F, 0F);
      liquid = new ModelRenderer(this, 32, 8);
      liquid.addBox(0F, 0F, 0F, 4, 5, 4);
      liquid.setRotationPoint(-2F, 18F, -2F);
      liquid.setTextureSize(128, 64);
      liquid.mirror = true;
      setRotation(liquid, 0F, 0F, 0F);
  }
  
  public void render()
  {

    cap.render(0.0625F);
    base1.render(0.0625F);
    base2.render(0.0625F);
    base3.render(0.0625F);
    base4.render(0.0625F);
    base5.render(0.0625F);
    liquid.render(0.0625F);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

}
