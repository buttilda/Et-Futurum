package ganymedes01.etfuturum.entities.ai;

import com.google.common.base.Objects;

import net.minecraft.util.MathHelper;

public class Vec3i implements Comparable<Vec3i> {

	private final int x, y, z;

	public Vec3i(int xIn, int yIn, int zIn) {
		x = xIn;
		y = yIn;
		z = zIn;
	}

	public Vec3i(double xIn, double yIn, double zIn) {
		this(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
	}

	@Override
	public boolean equals(Object p_equals_1_) {
		if (this == p_equals_1_)
			return true;
		else if (!(p_equals_1_ instanceof Vec3i))
			return false;
		else {
			Vec3i vec3i = (Vec3i) p_equals_1_;
			return getX() != vec3i.getX() ? false : getY() != vec3i.getY() ? false : getZ() == vec3i.getZ();
		}
	}

	@Override
	public int hashCode() {
		return (getY() + getZ() * 31) * 31 + getX();
	}

	@Override
	public int compareTo(Vec3i vec) {
		return getY() == vec.getY() ? getZ() == vec.getZ() ? getX() - vec.getX() : getZ() - vec.getZ() : getY() - vec.getY();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public Vec3i crossProduct(Vec3i vec) {
		return new Vec3i(getY() * vec.getZ() - getZ() * vec.getY(), getZ() * vec.getX() - getX() * vec.getZ(), getX() * vec.getY() - getY() * vec.getX());
	}

	public double distanceSq(double toX, double toY, double toZ) {
		double d3 = getX() - toX;
		double d4 = getY() - toY;
		double d5 = getZ() - toZ;
		return d3 * d3 + d4 * d4 + d5 * d5;
	}

	public double distanceSqToCenter(double xIn, double yIn, double zIn) {
		double d3 = getX() + 0.5D - xIn;
		double d4 = getY() + 0.5D - yIn;
		double d5 = getZ() + 0.5D - zIn;
		return d3 * d3 + d4 * d4 + d5 * d5;
	}

	public double distanceSq(Vec3i to) {
		return this.distanceSq(to.getX(), to.getY(), to.getZ());
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("x", getX()).add("y", getY()).add("z", getZ()).toString();
	}
}