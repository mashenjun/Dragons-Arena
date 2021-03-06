package distributed.systems.network;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NodeAddress implements Serializable {

	public static final String SEPARATOR = "-";

	private NodeType type;

	private int id = -1;

	private Address physicalAddress;

	public NodeAddress(String ip, int port) {
		physicalAddress = new Address(ip, port);
	}


	public NodeAddress(int port, NodeType type) {
		physicalAddress = Address.getMyAddress(port);
		this.type = type;
	}

	public NodeAddress(NodeAddress nodeAddress) {
		physicalAddress = nodeAddress.physicalAddress;
		type = nodeAddress.type;
		id = nodeAddress.id;
	}

	public static NodeAddress fromAddress(String address) {
		String[] parts = address.split(SEPARATOR, 2);
		NodeType nodeType = NodeType.valueOf(parts[0]);
		return new NodeAddress(nodeType, Integer.valueOf(parts[1]), null);
	}

	public boolean isServer() {
		return type.equals(NodeType.SERVER);
	}

	public boolean isClient() {
		return type.equals(NodeType.DRAGON) || type.equals(NodeType.PLAYER);
	}

	@Override
	public String toString() {
		return physicalAddress + "/" + type + ((id != -1) ? SEPARATOR + id : "");
	}

	public boolean hasSamePhysicalLocation(NodeAddress other) {
		return this.getPhysicalAddress().equals(other.getPhysicalAddress());
	}

	public String getName() {
		return type + SEPARATOR + id;
	}
}
