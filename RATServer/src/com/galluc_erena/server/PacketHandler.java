package com.galluc_erena.server;

import com.esotericsoftware.kryonet.Connection;
import com.galluc_erena.server.packets.Packet1Connected;
import com.galluc_erena.server.packets.Packet3Response;
import com.galluc_erena.server.packets.Packet5Tasks;
import com.galluc_erena.server.packets.Packet7Info;

public class PacketHandler {

	private ClientManager clientManager;
	private Window window;
	
	public PacketHandler(ClientManager clientManager, Window window) {
		this.clientManager = clientManager;
		this.window = window;
	}
	
	public void handlePacket1(Packet1Connected p1, Connection con) {
		String id = p1.id;
		Client client = new Client(con, id);
		if (!clientManager.clientConnected(client)) {
			System.out.println(id + " connected.");
			clientManager.addClient(client);
			window.addClientToList(id);
		}
	}
	
	public void handlePacket3(Packet3Response p3) {
		String response = p3.response;
		System.out.println(response);
	}
	
	public void handlePacket5(Packet5Tasks p5) {
		window.updateTasks(p5.tasks);
	}
	
	public void handlePacket7(Packet7Info p7) {
		window.openInfoFrame();
		window.setInfoFrame(p7.info);
	}
	
}
