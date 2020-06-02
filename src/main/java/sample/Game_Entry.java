package sample;

public class Game_Entry {
    /*private static int id;*/
    private String hero_name;
    private int damage;
    private int kills;
    private int position;

    public Game_Entry (String hero_name, int damage, int kills, int position) {
        /*this.id = id++;*/
        this.hero_name = hero_name;
        this.damage    = damage;
        this.kills     = kills;
        this.position  = position;
    }

    public String getHero_name() {
        return hero_name;
    }

    public void setHero_name(String hero_name) {
        this.hero_name = hero_name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Game_Entry{" +
                "hero_name='" + hero_name + '\'' +
                ", damage=" + damage +
                ", kills=" + kills +
                ", position=" + position +
                '}';
    }
}
