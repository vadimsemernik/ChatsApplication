package server.logic.entities.comparators;

import java.util.Comparator;

import server.logic.entities.TalkParticipant;

public class TalkParticipantComparator implements Comparator<TalkParticipant> {

	@Override
	public int compare(TalkParticipant first, TalkParticipant second) {
		int firstNumber=first.getNumber(), secondNumber = second.getNumber();
		if (firstNumber>secondNumber)return 1;
		else if (firstNumber<secondNumber)return -1;
		return 0;
	}

}
