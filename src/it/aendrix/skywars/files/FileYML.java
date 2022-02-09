package it.aendrix.skywars.files;

public interface FileYML {

    String directory = "plugins/SkyWars-Core";

    Object getObject(String paramString);
    String[] listObjects();
}
