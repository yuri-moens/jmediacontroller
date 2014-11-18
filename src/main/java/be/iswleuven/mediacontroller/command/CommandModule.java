package be.iswleuven.mediacontroller.command;

import be.iswleuven.mediacontroller.server.ServerHandler;

import com.google.inject.AbstractModule;

public class CommandModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ServerHandler.class).toInstance(ServerHandler.getInstance());
  }

}
