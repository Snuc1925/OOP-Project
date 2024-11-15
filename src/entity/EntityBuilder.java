package entity;
import components.*;
public class EntityBuilder {
    private Entity entity;

    public EntityBuilder() {
        entity = new Entity();
    }

//    public EntityBuilder withHealthComponent(int maxHealth, int currentHealth) {
//        entity.addComponent(new HealthComponent(maxHealth, currentHealth));
//        return this;
//    }

    public EntityBuilder withPositionComponent(int x, int y) {
        entity.addComponent(new PositionComponent(x, y));
        return this;
    }

//    public EntityBuilder withManaComponent(int maxMana, int currentMana) {
//        entity.addComponent(new ManaComponent(maxMana, currentMana));
//        return this;
//    }

    public EntityBuilder withAnimationComponent(int totalAnimationFrame, int frameDuration) {
        entity.addComponent(new AnimationComponent(totalAnimationFrame, frameDuration));
        return this;
    }

    public Entity build() {
        return entity;
    }
}
