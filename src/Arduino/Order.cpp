/*
 * Order.cpp
 *
 *  Created on: 20 de jul. de 2016
 *      Author: oscar
 */
#include "Arduino.h"

#include "Order.h"
#include <stdlib.h>

char Order::getType() {
	return type;
}
char Order::getServo() {
	return servoNum;
}
int Order::getValue() {
	return value;
}
void Order::setValue(int newvalue) {
	value = newvalue;
}
Order::Order(char newtype, char newservo, int newvalue) {
	type = newtype;
	servoNum = newservo;
	value = newvalue;
}

TOrder::TOrder(char newtype, char newservo, int newvalue) :
		Order(newtype, newservo, newvalue) {
}

Orderlist::Orderlist() {
	totalelements = 0;
	firstelement = nullptr;
}
void Orderlist::insert(Order newelement) {
	TOrder* aux = firstelement;
	TOrder* otro = new TOrder(newelement.getType(), newelement.getServo(),
			newelement.getValue());
	if (firstelement == nullptr) {
		firstelement = otro;
	} else {
		while (aux->ptr != nullptr) {
			aux = aux->ptr;
		}
		aux = otro;
	}
	totalelements++;
}
void Orderlist::deletepos(int pos) {
	TOrder* prev = firstelement;
	if (firstelement != nullptr) {
		TOrder* aux;
		TOrder* next;
		int i;
		for (i = 0; i < pos; i++) {
			prev = prev->ptr;
		}
		aux = prev->ptr;
		next = aux->ptr;
		prev->ptr = next;
		free(aux);
		totalelements--;
	}
}
Order Orderlist::getOrder(int pos) {
	TOrder* aux = firstelement;
	for (int i = 0; i < pos-1; i++) {
		aux = aux->ptr;
	}
	return Order(aux->type, aux->servoNum, aux->value);
}
void Orderlist::refresh(Order newelement) {
	TOrder* aux = firstelement;
	while (aux->type != newelement.getType()
			&& aux->servoNum != newelement.getServo()) {
		aux = aux->ptr;
	}
	aux->value = newelement.getValue();
}

