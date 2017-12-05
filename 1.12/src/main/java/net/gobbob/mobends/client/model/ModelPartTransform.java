package net.gobbob.mobends.client.model;

import org.lwjgl.util.vector.Vector3f;

import net.gobbob.mobends.util.SmoothVector3f;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

/*
 * Used for manipulating the transform of things that are
 * going to postRender this part.
 */
public class ModelPartTransform implements IModelPart
{
	public Vector3f position;
	public Vector3f scale;
	public SmoothVector3f rotation;
	public SmoothVector3f pre_rotation;
	public float scaleX, scaleY, scaleZ;
	
	public ModelPartTransform() {
		this.position = new Vector3f();
		this.scale = new Vector3f(1, 1, 1);
		this.rotation = new SmoothVector3f();
		this.pre_rotation = new SmoothVector3f();
	}
	
	@Override
	public void applyTransform(float scale)
	{
		if (this.position.x != 0.0F || this.position.y != 0.0F || this.position.z != 0.0F)
        	GlStateManager.translate(this.position.x * scale, this.position.y * scale, this.position.z * scale);
        
        if (this.rotation.getZ() != 0.0F)
            GlStateManager.rotate(this.rotation.getZ(), 0F, 0F, 1F);
        if (this.rotation.getY() != 0.0F)
            GlStateManager.rotate(this.rotation.getY(), 0F, 1F, 0F);
        if (this.rotation.getX() != 0.0F)
            GlStateManager.rotate(this.rotation.getX(), 1F, 0F, 0F);
        
        if(this.scale.x != 0.0F || this.scale.y != 0.0F || this.scale.z != 0.0F)
        	GlStateManager.scale(this.scale.x, this.scale.y, this.scale.z);
	}

	@Override
	public void renderPart(float scale)
	{
		//Since this is just a transform, do nothing.
	}

	@Override
	public void update(float ticksPerFrame)
	{
		this.rotation.update(ticksPerFrame);
		this.pre_rotation.update(ticksPerFrame);
	}

	@Override
	public Vector3f getPosition()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector3f getScale()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmoothVector3f getRotation()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmoothVector3f getPreRotation()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void syncUp(IModelPart part)
	{
		// TODO Auto-generated method stub
		
	}
}