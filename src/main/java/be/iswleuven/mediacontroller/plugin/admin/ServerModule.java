package be.iswleuven.mediacontroller.plugin.admin;

import be.iswleuven.mediacontroller.server.ServerHandler;

import com.google.inject.AbstractModule;

public class ServerModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ServerHandler.class).toInstance(ServerHandler.getInstance());
  }

}
