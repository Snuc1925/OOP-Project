package system;

import entities.projectile.Projectile;
import gamestates.Playing;
import java.util.Random;
import java.util.ArrayList;

import enitystates.Attack;
import enitystates.Death;
import utils.Constants;
import entities.monsters.Monster;
public class MonsterAttackSystem {
    Playing playing;
    public MonsterAttackSystem(Playing playing) {
        this.playing = playing;
    }

    private int frameCounter = 0;
    private void monsterAttackLongRange() {
        frameCounter++;
        if (frameCounter < 20) return;
        frameCounter = 1;
        ArrayList<Integer> monsterIds = new ArrayList<>();
        for (int id = 0; id < playing.monsters.length; id++) {
            if (playing.monsters[id].canSeePlayer()) {
                monsterIds.add(id);
            }
        }

        if (monsterIds.size() != 0) {
            Random random = new Random();
//            int r = random.nextInt(2);
//            if (r != 0) return; // Neu r = 0 thi attack

            int id = random.nextInt(monsterIds.size());
            int monsterId = monsterIds.get(id);
            Monster monster = playing.monsters[monsterId];

            Projectile projectile = new Projectile(playing,"MONSTER", monster);
            playing.getProjectileManager().addProjectile(projectile);
        }
    }
    public void update() {
        monsterAttackLongRange();
    }
}
