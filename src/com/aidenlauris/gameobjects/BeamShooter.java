package com.aidenlauris.gameobjects;

import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.Force;

public class BeamShooter extends Enemy {

	public BeamShooter(float x, float y) {
		super(x, y, 50, 10, 3);
	}
	
	@Override
	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));

		  
			Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random()*360));
			f.setId("BeamMove");
			f.setLifeSpan(60);
			f.setReduction(0f);
			if (getForceSet().getForce("BeamMove") == null){
			addForce(f);
			}
			
	}
	
	public void update() {
		tickUpdate();

		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		time++;
		if (isStunned()) {
			return;
		}
		
		move();
		
		
		if (dist < 500 && Time.alertPassed(alert)) {
			attack();
			alert = Time.alert((long) (180));
		}

	}
	
	public void attack(){
		Beam b = new Beam(3);
		b.x = this.x;
		b.y = this.y;
		Player p = Player.getPlayer();
		b.setMoveSpeed(Integer.MAX_VALUE);
		b.setLifeSpan(60);
		b.setGunOffset(50);
		b.team = team.ENEMY;
		float theta = (float) Math.atan2(p.y - this.y, p.x - this.x);
		b.setTheta(theta);
		b.init();
		
	}

}
