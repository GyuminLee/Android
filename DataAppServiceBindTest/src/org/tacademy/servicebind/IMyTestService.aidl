package org.tacademy.servicebind;

interface IMyTestService {
	int getState(in String name);
	boolean setState(in String name,in int state);
}