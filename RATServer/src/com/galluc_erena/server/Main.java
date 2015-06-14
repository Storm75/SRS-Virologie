package com.galluc_erena.server;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.galluc_erena.server.packets.Packet;
import com.galluc_erena.server.packets.Packet1Connected;
import com.galluc_erena.server.packets.Packet2RunCmd;
import com.galluc_erena.server.packets.Packet3Response;
import com.galluc_erena.server.packets.Packet4Popup;
import com.galluc_erena.server.packets.Packet5Tasks;
import com.galluc_erena.server.packets.Packet6KillTask;
import com.galluc_erena.server.packets.Packet7Info;

public class Main {

	public static Server server;
	public static ClientManager clientManager;
	public static Window window;
	
	private static PacketHandler pHandler;
	
	public static void main(String[] args) throws IOException {
		server = new Server(21234, 5096);
		clientManager = new ClientManager();
		server.start();
		server.bind(21234, 21234);
		register();
		window = new Window();
		pHandler = new PacketHandler(clientManager, window);
		new Main();
	}
	
	public static void register() {
		server.getKryo().register(Packet.class);
		server.getKryo().register(Packet1Connected.class);
		server.getKryo().register(Packet2RunCmd.class);
		server.getKryo().register(Packet3Response.class);
		server.getKryo().register(Packet4Popup.class);
		server.getKryo().register(String.class);
		server.getKryo().register(String[].class);
		server.getKryo().register(Object[].class);
		server.getKryo().register(Packet5Tasks.class);
		server.getKryo().register(Packet6KillTask.class);
		server.getKryo().register(Packet7Info.class);
	}
	
	public Main() {
		server.addListener(new Listener() {
			   public void received (Connection con, Object o) {
				   if (o instanceof Packet) {
					   if (o instanceof Packet1Connected) {
						   Packet1Connected p1 = (Packet1Connected) o;
						   pHandler.handlePacket1(p1, con);
					   } else if (o instanceof Packet3Response) {
						   Packet3Response p3 = (Packet3Response) o;
						   pHandler.handlePacket3(p3);
					   } else if (o instanceof Packet5Tasks) {
						   Packet5Tasks p5 = (Packet5Tasks) o;
						   pHandler.handlePacket5(p5);
					   } else if (o instanceof Packet7Info) {
						   Packet7Info p7 = (Packet7Info) o;
						   pHandler.handlePacket7(p7);
					   }
				   }
			   }
			   
			   public void disconnected (Connection con) {
				   Client c = clientManager.getClient(con); //Get client
				   if (c != null) {
					   window.removeClient(c.getId());
					   clientManager.clients.remove(c);
				   }
			   }
			
		});
	}

}
