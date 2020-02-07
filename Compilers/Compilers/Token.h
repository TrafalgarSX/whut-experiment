#pragma once
#include<string>
#include"data.h"
using namespace std;
class Token
{
public:
	int tag;
	Token(int t) {
		tag = t;
	};
	virtual ~Token();
	virtual string toString();

};


Token::~Token()
{
}

inline string Token::toString()
{
	return to_string(tag);
}

class Num:public Token
{
public:
	int value;
	Num(int v):Token(NUM) ,value(v){};
	~Num();
	string toString() override;

};


Num::~Num()
{
}

inline string Num::toString()
{
	return to_string(value);
}

class Word:public Token
{
public:
	string lexeme = "";
	Word(string s, int tag) :Token(tag),lexeme(s){};
	string toString();

	~Word();
	static Word  andd,orr,eq,ne,le,ge,minus,True,False,temp;
};


inline string Word::toString()
{
	return lexeme;
}

Word::~Word()
{
}
class Real:public Token
{
public:
	float value;
	Real(float v) :Token(REAL),value(v){};
	~Real();
	string toString();
private:

};


Real::~Real()
{
}

inline string Real::toString()
{
	return to_string(value);
}
