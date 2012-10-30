package org.tacademy.servicebind;

interface IHelloService {
	int setCount(in int count);
	int getCount(in int flag);
	int setStart(in boolean bStart);
}