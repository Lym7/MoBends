package net.gobbob.mobends.core.client.gui.customize.viewport;

import net.gobbob.mobends.core.animatedentity.AlterEntry;
import net.gobbob.mobends.core.animatedentity.BoneMetadata;
import net.gobbob.mobends.core.animatedentity.IPreviewer;
import net.gobbob.mobends.core.client.model.IModelPart;
import net.gobbob.mobends.core.data.LivingEntityData;
import net.gobbob.mobends.core.math.TransformUtils;
import net.gobbob.mobends.core.math.matrix.IMat4x4d;
import net.gobbob.mobends.core.math.matrix.Mat4x4d;
import net.gobbob.mobends.core.math.physics.IAABBox;
import net.gobbob.mobends.core.math.physics.OBBox;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class AlterEntryRig
{
	
	private final AlterEntry alterEntry;
	final Map<String, Bone> nameToBoneMap = new HashMap<>();
	private Bone hoveredOverBone = null;
	private Bone selectedBone = null;
	
	public AlterEntryRig(AlterEntry alterEntry)
	{
		this.alterEntry = alterEntry;
		IPreviewer previewer = alterEntry.getPreviewer();
		LivingEntityData data = alterEntry.getDataForPreview();
		
		if (previewer == null || data == null)
			return;
		
		Map<String, BoneMetadata> boneMetadata = previewer.getBoneMetadata();
		if (boneMetadata != null)
		{
			boneMetadata.forEach((key, value) -> {
				Object part = data.getPartForName(key);
				
				if (part instanceof IModelPart)
				{
					IModelPart modelPart = (IModelPart) part;
					this.nameToBoneMap.put(key, new Bone(modelPart, value.getBounds()));
				}
			});
		}
	}
	
	public void updateTransform()
	{
		Mat4x4d mat = new Mat4x4d(Mat4x4d.IDENTITY);
		TransformUtils.scale(mat, -1, 1, -1);
		alterEntry.transformModelToCharacterSpace(mat);
		TransformUtils.scale(mat, 0.0625F, 0.0625F, 0.0625F);
		
		LivingEntityData data = alterEntry.getDataForPreview();
		
		TransformUtils.translate(mat, data.renderOffset.getX(), -data.renderOffset.getY(), data.renderOffset.getZ());
		
		this.nameToBoneMap.forEach((key, bone) -> {
			bone.updateTransform(mat);
		});
	}
	
	public void hoverOver(@Nullable Bone bone)
	{
		this.hoveredOverBone = bone;
	}
	
	public void select(@Nullable Bone bone)
	{
		this.selectedBone = bone;
	}

	public boolean isBoneHoveredOver(Bone bone)
	{
		return bone == this.hoveredOverBone;
	}
	
	public boolean isBoneSelected(Bone bone)
	{
		return bone == this.selectedBone;
	}

	@Nullable
	public Bone getBone(String name)
	{
		return this.nameToBoneMap.get(name);
	}

	public static class Bone
	{
		
		final IModelPart part;
		/**
		 * Can be null in case the bone represents something like item rotation.
		 */
		final OBBox collider;

		public Bone(IModelPart part)
		{
			this.part = part;
			this.collider = null;
		}

		public Bone(IModelPart part, IAABBox bounds)
		{
			this.part = part;
			this.collider = new OBBox(bounds);
		}
		
		public void updateTransform(IMat4x4d parentMat)
		{
			this.collider.transform.copyFrom(parentMat);
			this.part.applyCharacterSpaceTransform(1, this.collider.transform);
		}
		
	}
	
}
