package data;

import entities.Player;

public class PlayerData {
    public int worldX, worldY, currentHealth, maxHealth, currentMana, maxMana, currentArmor, maxArmor;
    public String direction;
    public String currentWeapon;

    public PlayerData() {}

    public void loadData(Player player) {
        player.worldX = this.worldX;
        player.worldY = this.worldY;
        player.currentHealth = this.currentHealth;
        player.maxHealth = this.maxHealth;
        player.currentMana = this.currentMana;
        player.maxMana = this.maxMana;
        player.currentArmor = this.currentArmor;
        player.maxArmor = this.maxArmor;
        player.direction = this.direction;
        player.currentWeapon = this.currentWeapon;
    }

    public void saveData(Player player) {
        this.worldX = player.worldX;
        this.worldY = player.worldY;
        this.currentHealth = player.currentHealth;
        this.maxHealth = player.maxHealth;
        this.currentMana = player.currentMana;
        this.maxMana = player.maxMana;
        this.currentArmor = player.currentArmor;
        this.maxArmor = player.maxArmor;
        this.direction = player.direction;
        this.currentWeapon = player.currentWeapon;
    }
}
