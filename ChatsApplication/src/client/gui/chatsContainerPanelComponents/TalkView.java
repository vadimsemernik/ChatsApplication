package client.gui.chatsContainerPanelComponents;

import client.logic.entities.Message;

public interface TalkView {
	
	public void addMessage(Message message);

	public void repaintView();

}
