package it.aendrix.skywars.arena;

import it.aendrix.skywars.files.ArenaYML;
import it.aendrix.skywars.files.SkyWarsTypeYML;
import it.aendrix.skywars.main.Main;
import it.aendrix.skywars.skywars.SkyWarsArena;
import it.aendrix.skywars.skywars.SkyWarsType;

import java.util.HashMap;

public class Loader {

    public Loader() {
        SkyWarsArena.arene = new HashMap<>();
        SkyWarsType.types = new HashMap<>();
        Arena.setArenaPlayers(Main.getPlayers());

        loadAll();
    }

    public void load(String name) {
       Object arena = new ArenaYML().getObject(name);

       if (arena instanceof SkyWarsArena)
           SkyWarsArena.arene.put(((SkyWarsArena)arena).name.toUpperCase(), (SkyWarsArena) arena);
    }

    public void loadType(String name) {
        Object type = new SkyWarsTypeYML().getObject(name);

        if (type instanceof SkyWarsType)
            SkyWarsType.types.put(((SkyWarsType)type).getName().toUpperCase(), (SkyWarsType) type);
    }

    public void unload(String name) {
        SkyWarsArena.arene.remove(name.toUpperCase());
    }

    public void loadAll() {
        String[] arene = new ArenaYML().listObjects();
        for (String arena : arene)
            load(arena);

        String[] types = new SkyWarsTypeYML().listObjects();
        for (String type : types)
            loadType(type);
    }

    public void unloadAll() {
        for (SkyWarsArena a : SkyWarsArena.arene.values()) {
            ArenaYML.save(a);
        }

        for (SkyWarsType a : SkyWarsType.types.values()) {
            SkyWarsTypeYML.save(a);
        }
    }
}
