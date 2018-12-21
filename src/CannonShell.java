
public class CannonShell extends Projectile{

	CannonShell(float x, float y,float damage, float theta, float gunOffset, float reduction) {
		super(x, y, 10f,damage, theta, gunOffset, reduction);
		weight = 3;
		setCollisionBox(new HurtBox(this, -15f, -15f, 30, 30, damage));
	}

}