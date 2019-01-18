package com.aidenlauris.gameobjects;

import java.util.Random;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.items.BulletAmmo;
import com.aidenlauris.items.EnergyCell;
import com.aidenlauris.items.ExplosiveAmmo;
import com.aidenlauris.items.ShotgunAmmo;
import com.aidenlauris.render.SoundHelper;

public class Chaser extends Enemy {

	public Chaser(float x, float y) {
		super(x, y, 25, 10, 3.5f);
	}

	@Override
	public void kill() {
		Random rand = new Random();
		int death = rand.nextInt((2 - 1) + 1) + 1;
		
		if (death == 1) {
		for (int i = 0; i < 450; i += 90) {
			Bullet b = new Bullet(2);
			b.x = this.x;
			b.y = this.y;
			Player p = Player.getPlayer();
			b.setMoveSpeed(8);
			b.setLifeSpan(60);
			b.setGunOffset(50);
			b.team = team.ENEMY;
			float theta = (float)Math.toRadians(i);
			b.setTheta(theta);
			b.init();
			
			
			ItemDropEntity.drop(x, y, new BulletAmmo(1), 0.2, 4, 10);
			ItemDropEntity.drop(x, y, new ShotgunAmmo(1), 0.15, 2, 3);
			ItemDropEntity.drop(x, y, new ExplosiveAmmo(1), 0.05, 1, 1);
			ItemDropEntity.drop(x, y, new EnergyCell(1), 0.05, 1, 2);
			HealthDropEntity.drop(x, y, 0.15, 1, 3);
			
			WorldMap.addGameObject(new Corpse(x, y, this));
	System.out.println(getForceSet().getNetMagnitude());
			removeSelf();
			
			SoundHelper.makeSound("pew.wav");
			
		}
	}else if (death == 2) {
		for (int i = 45; i < 405; i += 90) {
			Bullet b = new Bullet(2);
			b.x = this.x;
			b.y = this.y;
			Player p = Player.getPlayer();
			b.setMoveSpeed(8);
			b.setLifeSpan(60);
			b.setGunOffset(50);
			b.team = team.ENEMY;
			float theta = (float)Math.toRadians(i);
			b.setTheta(theta);
			b.init();
		}
	}
		
		removeSelf();
	}
	
	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		if (dist < 400) {
			ForceAnchor f = new ForceAnchor(3f, this, Player.getPlayer(), -1);
			
			f.setId("PlayerFollow");
			f.hasVariableSpeed(false);
			if (getForceSet().getForce("PlayerFollow") == null) {
				getForceSet().removeForce("Random");
				getForceSet().addForce(f);
			}
		} else {
			Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random() * 360));
			f.setId("Random");
			f.setLifeSpan(60);
			f.setReduction(0f);
			if (getForceSet().getForce("Random") == null) {
				getForceSet().removeForce("PlayerFollow");
				getForceSet().addForce(f);
			}
		}
	}
	
	
}
