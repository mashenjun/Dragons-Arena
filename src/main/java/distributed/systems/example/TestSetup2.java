package distributed.systems.example;

import java.rmi.RemoteException;

import distributed.systems.network.DragonNode;
import distributed.systems.network.PlayerNode;
import distributed.systems.network.RegistryNode;
import distributed.systems.network.ServerNode;
import distributed.systems.network.logging.LogNode;
import distributed.systems.network.logging.Logger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestSetup2 {

	public static void main(String[] args) throws RemoteException, InterruptedException {
		// Server setup
		ServerNode server1 = new ServerNode(1234, true);

		ServerNode server2 = new ServerNode(1235, false);
		//ServerNode server3 = new ServerNode(1236, false);
		server2.getServerSocket().connectToCluster(server1.getAddress());
		//server3.getServerSocket().connectToCluster(server2.getAddress());*/

        //Dragon added
        DragonNode dragon1 = new DragonNode(server1.getAddress(),10, 10);

		// Players added
		PlayerNode player1 = new PlayerNode(server1.getAddress(),9,10);
        PlayerNode player2 = new PlayerNode(server1.getAddress(),11,10);
        PlayerNode player3 = new PlayerNode(server1.getAddress(),12,10);
        PlayerNode player4 = new PlayerNode(server1.getAddress(),13,10);
		System.out.println(server1.getBattlefield().equals(server2.getBattlefield()));
		System.out.println(server2.getServerSocket().getOtherNodes());

	}
}
