package be.iswleuven.mediacontroller;


import be.iswleuven.mediacontroller.command.CommandHandler;
import be.iswleuven.mediacontroller.config.Config;
import be.iswleuven.mediacontroller.config.ConfigLoader;
import be.iswleuven.mediacontroller.player.Player;
import be.iswleuven.mediacontroller.player.Playlist;
import be.iswleuven.mediacontroller.player.VlcPlayer;
import be.iswleuven.mediacontroller.plugin.PluginHandler;
import be.iswleuven.mediacontroller.server.ServerHandler;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class AppModule extends AbstractModule {
  
  @Override
  protected void configure() {
    bind(CommandHandler.class).in(Singleton.class);
    bind(PluginHandler.class).in(Singleton.class);
    bind(ServerHandler.class).in(Singleton.class);
    
    bind(Config.class).toInstance(ConfigLoader.getConfig());
    bind(Playlist.class).in(Singleton.class);
    
    bind(Player.class).to(VlcPlayer.class).in(Singleton.class);;
  }

}
