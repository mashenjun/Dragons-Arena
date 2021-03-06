package distributed.systems.example;


import java.rmi.RemoteException;


import distributed.systems.core.exception.AlreadyAssignedIDException;
import distributed.systems.das.BattleField;
import distributed.systems.network.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestSetup3 {
    public static final int MIN_PLAYER_COUNT = 30;
    public static final int MAX_PLAYER_COUNT = 60;
    public static final int DRAGON_COUNT = 20;
    public static final int TIME_BETWEEN_PLAYER_LOGIN = 5000;
    public static int playerCount;

    public static void main(String[] args) throws RemoteException, InterruptedException, ConnectionException {
        // Server setup
        ServerNode server1 = new ServerNode(1234);
	    server1.startCluster();

        ServerNode server2 = new ServerNode(1235);
        //ServerNode server3 = new ServerNode(1236, false);
        server2.connect(server1.getAddress());
        //server3.getServerSocket().connectToCluster(server2.getAddress());*/

        for (int i = 0; i < DRAGON_COUNT; i++) {
			/* Try picking a random spot */
            int x, y, attempt = 0;
            final int temp = i;
            do {
                x = (int) (Math.random() * BattleField.MAP_WIDTH);
                y = (int) (Math.random() * BattleField.MAP_HEIGHT);
                attempt++;
            } while (server1.getServerState().getBattleField().getUnit(x, y) != null && attempt < 10);

            // If we didn't find an empty spot, we won't add a new dragon
            if (server1.getServerState().getBattleField().getUnit(x, y) != null) break;

            final int finalX = x;
            final int finalY = y;

            new Thread(() -> {
                try {
                    new DragonNode(server1.getAddress(), finalX, finalY);
                } catch (AlreadyAssignedIDException | ConnectionException | RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }).start();
            Thread.sleep(500);
        }

        playerCount = (int)((MAX_PLAYER_COUNT - MIN_PLAYER_COUNT) * Math.random() + MIN_PLAYER_COUNT);
        for(int i = 0; i < playerCount; i++)
        {
			/* Once again, pick a random spot */
            int x, y, attempt = 0;
            final int temp = i;
            do {
                x = (int)(Math.random() * BattleField.MAP_WIDTH);
                y = (int)(Math.random() * BattleField.MAP_HEIGHT);
                attempt++;
            } while (server1.getServerState().getBattleField().getUnit(x, y) != null && attempt < 10);

            // If we didn't find an empty spot, we won't add a new player
            if (server1.getServerState().getBattleField().getUnit(x, y) != null) break;

            final int finalX = x;
            final int finalY = y;
            new Thread(() -> {
                try {
                    new PlayerNode(server1.getAddress(),finalX,finalY);
                } catch (AlreadyAssignedIDException | ConnectionException | RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }).start();
            System.out.println(server1.getServerState().getBattleField().equals(server2.getServerState().getBattleField()));
            Thread.sleep(500);
        }

    }
}

