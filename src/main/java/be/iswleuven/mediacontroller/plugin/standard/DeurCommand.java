package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.inject.Inject;

public class DeurCommand extends Command {
	
	public static String COMMAND_STRING = "o";
	public static String[] COMMAND_ALIASES = {"O","deur","sesam","openu"};
	
	public static String COMMAND_HELP = "Doe 'o', dit opent de ISW deur";
	
	@Inject
	public DeurCommand(StandardPlugin plugin) {
		super(plugin);
	}

	@Override
	public void execute() throws CommandException {
		Socket client = null;
		try {
			client = new Socket("d.isw",3333);
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			out.println("o");
			client.close();
			setMessage("Deur geopend");
			notifyWorker();
		} catch (IOException e) {
			setMessage("Deur kon niet gevonden worden of er kon niet heen geschreven worden.");
			notifyWorker();
		}		
	}

}
