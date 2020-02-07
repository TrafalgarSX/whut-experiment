#pragma once
#include<iostream>
#include<string>
#include<unordered_map>
#include"inter.h"
#include"Token.h"
using namespace std;
class Env
{
public:
private:
	unordered_map<Token, Ids> table;
public:
	Env *prev;
	Env(Env &n) {
		prev = &n;
	};
	~Env();
	//void put(Token w, Ids i);
	//int get(Token w);
private:

};
//inline void Env::put(Token w, Ids i)
//{
//}
//int Env::get(Token w) {
//	for (Env *e = this; e != nullptr; e = e->prev) {
//		if (e->table[w] != table.end) {
//			return e->table[w];
//		}
//	}
//}

	


class Type:public Word
{
public:
	int width = 0;
	Type(string s, int tag, int w) :Word(s, tag), width(w) {};
	~Type();
	static Type Int, Float, Char, Bool;
	static bool numeric(Type p);
	static Type max(Type p1, Type p2);
};


Type::~Type()
{
}

 bool Type::numeric(Type p)
{
	 if (p.lexeme == Type::Char.lexeme || p.lexeme == Type::Int.lexeme || p.lexeme == Type::Float.lexeme) {
		 return true;
	}
	 else {
		 return false;
	 }
}

 Type Type::max(Type p1, Type p2)
 {
	 if (!numeric(p1) || !numeric(p2)) {
		 return Type("false",0,0);
	 }
	 else if (p1.lexeme == Type::Float.lexeme || p2.lexeme == Type::Float.lexeme)return Type::Float;
	 else if (p1.lexeme == Type::Int.lexeme || p2.lexeme == Type::Int.lexeme)return Type::Int;
	 else return Type::Char;
 }
 class Array:public Type
 {
 public:
	 Type of;
	 int size = 1;
	 Array(int sz, Type p) :Type("[]", INDEX, sz*p.width), size(sz), of(p.lexeme,p.tag,p.width) {};
	 ~Array();
	 string toString();

 };


 Array::~Array()
 {
 }

 inline string Array::toString()
 {
	 string result = "[";
	 result += to_string(size);
	 result += "]";
	 result += of.toString();
	 return result;
 }
