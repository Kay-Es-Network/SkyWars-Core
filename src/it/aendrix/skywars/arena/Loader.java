package it.aendrix.skywars.arena;

import it.aendrix.skywars.files.ArenaYML;
import it.aendrix.skywars.skywars.SkyWarsArena;

import java.util.HashMap;

public class Loader {

    public Loader() {
        SkyWarsArena.arene = new HashMap<>();

        loadAll();
    }

    public void load(String name) {
       Object arena = new ArenaYML().getObject(name);

       if (arena instanceof SkyWarsArena)
           SkyWarsArena.arene.put(((SkyWarsArena)arena).name.toUpperCase(), (SkyWarsArena) arena);
    }

    public void unload(String name) {
        SkyWarsArena.arene.remove(name.toUpperCase());
    }

    public void loadAll() {
        String[] arene = new ArenaYML().listObjects();
        for (String arena : arene)
            load(arena);
    }

    public void unloadAll() {
        for (SkyWarsArena a : SkyWarsArena.arene.values()) {
            ArenaYML.save(a);
        }
    }
}
