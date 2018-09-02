package server.logic.entities.comparators;

import java.util.Comparator;

import server.logic.entities.ServerMessage;

public class ServerMessageComparator implements Comparator<ServerMessage> {

	@Override
	public int compare(ServerMessage first, ServerMessage second) {
		int firstNumber=first.getNumber(), secondNumber = second.getNumber();
		if (firstNumber>secondNumber)return 1;
		else if (firstNumber<secondNumber)return -1;
		return 0;
	}

}
