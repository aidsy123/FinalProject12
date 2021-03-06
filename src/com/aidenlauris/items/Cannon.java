/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Cannon
 * type of gun that shoots explosives
 */

package com.aidenlauris.items;

import com.aidenlauris.gameobjects.CannonShell;
import com.aidenlauris.gameobjects.Projectile;

public class Cannon extends Gun {

	/**
	 * creates a cannon with set values
	 */
	public Cannon() {
		setAtkSpeed(30);
		setLength(30);
		setAccuracy(0);
		setAuto(false);
		setDamage(120);
		
		setSpawnSound("cannon.wav");

		setAmmoType(new ExplosiveAmmo().item());
	}

	@Override
	public Projectile bulletType() {
		
		//factory for cannon shell
		CannonShell p = new CannonShell(getDamage());
		p.setMoveSpeed(30f);
		p.setReduction(0.08f);
		return p;
	}

}
