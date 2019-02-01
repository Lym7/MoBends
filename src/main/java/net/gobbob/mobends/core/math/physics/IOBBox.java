package net.gobbob.mobends.core.math.physics;

import net.gobbob.mobends.core.math.matrix.IMat4x4d;
import net.gobbob.mobends.core.math.vector.IVec3fRead;

public interface IOBBox
{
	
	IVec3fRead getMin();
	IVec3fRead getMax();
	IMat4x4d getTransform();
	
}
