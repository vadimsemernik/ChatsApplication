package client.logic.utils;

import test.logging.ClientLog;

public class SharedQueue <E> {
	
	private E [] array;
	private int addIndex;
	private int getIndex;
	
	
	public SharedQueue  (){
		array = (E []) new Object [16];
		addIndex = getIndex =0;
	}
	
	public SharedQueue  (int size){
		array = (E []) new Object [size];
		addIndex = getIndex =0;
	}
	
	public boolean add(E item) {
		synchronized (array) {
			while(addIndex>= getIndex+array.length){
					try {
						array.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			array[addIndex%array.length]=item;
			addIndex++;
			array.notify();
		}
		return true;
		
	}
	
	public E getNext() {
		E result=null;
		synchronized (array) {
			while (getIndex>=addIndex){
					try {
						array.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			result = array[getIndex%array.length];
			array[getIndex%array.length]=null;
			getIndex++;
			array.notify();
		}
		return result;
		
	}

	

}
