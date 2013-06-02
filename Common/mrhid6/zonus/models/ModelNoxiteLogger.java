package mrhid6.zonus.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;



public class ModelNoxiteLogger extends ModelBase
{
  //fields
    ModelRenderer collumn;
    ModelRenderer top1;
    ModelRenderer top2;
    ModelRenderer bottom3;
    ModelRenderer bottom2;
    ModelRenderer bottom1;
    ModelRenderer glass;
    ModelRenderer diskbottom;
    ModelRenderer diskMain;
    ModelRenderer diskTop;
    ModelRenderer diskleft;
    ModelRenderer diskRight;
  
  public ModelNoxiteLogger()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      collumn = new ModelRenderer(this, 64, 0);
      collumn.addBox(-2F, 0F, -2F, 4, 8, 4);
      collumn.setRotationPoint(0F, 13F, 0F);
      collumn.setTextureSize(128, 64);
      collumn.mirror = true;
      setRotation(collumn, 0F, 0F, 0F);
      top1 = new ModelRenderer(this, 0, 29);
      top1.addBox(-6F, 0F, -6F, 12, 1, 12);
      top1.setRotationPoint(0F, 11F, 0F);
      top1.setTextureSize(128, 64);
      top1.mirror = true;
      setRotation(top1, 0F, 0F, 0F);
      top2 = new ModelRenderer(this, 36, 29);
      top2.addBox(-5F, 0F, -5F, 10, 1, 10);
      top2.setRotationPoint(0F, 12F, 0F);
      top2.setTextureSize(128, 64);
      top2.mirror = true;
      setRotation(top2, 0F, 0F, 0F);
      bottom3 = new ModelRenderer(this, 36, 42);
      bottom3.addBox(-3F, 0F, -3F, 6, 1, 6);
      bottom3.setRotationPoint(0F, 21F, 0F);
      bottom3.setTextureSize(128, 64);
      bottom3.mirror = true;
      setRotation(bottom3, 0F, 0F, 0F);
      bottom2 = new ModelRenderer(this, 36, 29);
      bottom2.addBox(-5F, 0F, -5F, 10, 1, 10);
      bottom2.setRotationPoint(0F, 22F, 0F);
      bottom2.setTextureSize(128, 64);
      bottom2.mirror = true;
      setRotation(bottom2, 0F, 0F, 0F);
      bottom1 = new ModelRenderer(this, 0, 42);
      bottom1.addBox(-6F, 0F, -6F, 12, 1, 12);
      bottom1.setRotationPoint(0F, 23F, 0F);
      bottom1.setTextureSize(128, 64);
      bottom1.mirror = true;
      setRotation(bottom1, 0F, 0F, 0F);
      glass = new ModelRenderer(this, 0, 0);
      glass.addBox(-8F, -16F, -8F, 16, 13, 16);
      glass.setRotationPoint(0F, 27F, 0F);
      glass.setTextureSize(128, 64);
      glass.mirror = true;
      setRotation(glass, 0F, 0F, 0F);
      diskbottom = new ModelRenderer(this, 72, 12);
      diskbottom.addBox(-0.5F, 3F, -2F, 1, 1, 4);
      diskbottom.setRotationPoint(0F, 0F, 0F);
      diskbottom.setTextureSize(128, 64);
      diskbottom.mirror = true;
      setRotation(diskbottom, 0F, 0F, 0F);
      diskMain = new ModelRenderer(this, 64, 12);
      diskMain.addBox(-0.5F, -3F, -3F, 1, 6, 6);
      diskMain.setRotationPoint(0F, 0F, 0F);
      diskMain.setTextureSize(128, 64);
      diskMain.mirror = true;
      setRotation(diskMain, 0F, 0F, 0F);
      diskTop = new ModelRenderer(this, 72, 12);
      
      diskTop.addBox(-0.5F, -4F, -2F, 1, 1, 4);
      diskTop.setRotationPoint(0F, 0F, 0F);
      diskTop.setTextureSize(128, 64);
      diskTop.mirror = true;
      setRotation(diskTop, 0F, 0F, 0F);
      diskleft = new ModelRenderer(this, 64, 24);
      diskleft.addBox(-0.5F, -2F, 3F, 1, 4, 1);
      diskleft.setRotationPoint(0F, 0F, 0F);
      diskleft.setTextureSize(128, 64);
      diskleft.mirror = true;
      setRotation(diskleft, 0F, 0F, 0F);
      diskRight = new ModelRenderer(this, 64, 24);
      diskRight.addBox(-0.5F, -2F, -4F, 1, 4, 1);
      diskRight.setRotationPoint(0F, 0F, 0F);
      diskRight.setTextureSize(128, 64);
      diskRight.mirror = true;
      setRotation(diskRight, 0F, 0F, 0F);
  }
  
  public void render()
  {
    collumn.render(0.0625F);
    top1.render(0.0625F);
    top2.render(0.0625F);
    bottom3.render(0.0625F);
    bottom2.render(0.0625F);
    bottom1.render(0.0625F);
    glass.render(0.0625F);
    
  }
  
  public void render2(float rot){
	diskbottom.render(0.0625F);
    diskMain.render(0.0625F);
    diskTop.render(0.0625F);
    diskleft.render(0.0625F);
    diskRight.render(0.0625F);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

}
