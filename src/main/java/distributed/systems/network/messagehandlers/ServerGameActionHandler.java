package distributed.systems.network.messagehandlers;

import java.rmi.RemoteException;

import distributed.systems.core.LogType;
import distributed.systems.core.Message;
import distributed.systems.network.ServerNode;

public class ServerGameActionHandler implements MessageHandler {


	private static final String MESSAGE_TYPE = "DEFAULT";
	private final ServerNode me;

	public ServerGameActionHandler(ServerNode me) {
		this.me = me;
	}

	@Override
	public String getMessageType() {
		return MESSAGE_TYPE;
	}

	@Override
	public Message onMessageReceived(Message message) throws RemoteException {
		// TODO: task distribution
		me.getSocket().logMessage("[" + me.getAddress() + "] received message: (" + message + ")", LogType.DEBUG);
		System.out.println(message);
		System.out.println(me.getAddress());
		System.out.println(me.getBattlefield());
		return me.getBattlefield().onMessageReceived(message);
	}
}
