/*
 * Order.h
 *
 *  Created on: 20 de jul. de 2016
 *      Author: oscar
 */

#ifndef ORDER_H_
#define ORDER_H_


class Order {
  public:
    char getType();
    char getServo();
    int getValue();
    void setValue(int newvalue);
    Order(char newtype, char newservo, int newvalue);
    char type;
    char servoNum;
    int value;
};

class TOrder: public Order {
  public:
    TOrder(char newtype, char newservo, int newvalue);
    TOrder * ptr = nullptr;
  };

  class Orderlist {
  public:
    Orderlist();
    void insert(Order newelement);
    void deletepos(int pos);
    Order getOrder(int pos);
    void refresh(Order newelement);
    int totalelements;

  protected:
    TOrder* firstelement;
  };

  class Servo{
  public:
	  Servo(int p);
	  Servo();
	  unsigned int pos;
  };

#endif /* ORDER_H_ */
