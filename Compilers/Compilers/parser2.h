#pragma once
#include"variable.h"
#include<iostream>
#include"lexer.h"
using namespace std;
ofstream pfile;//四元式
ofstream pofile;
string minstr1,minstr2;
vector<Quaternary> quaternary;//四元式
extern production pro[productions];
int  makelist(int i) {
	return i;
}
void backpatch(vector<int>& lists, string minstr) {
	if (lists.size() != 0) {
		int index = 0;
		for (int i = 0; i < lists.size(); i++) {
			index = lists[i];
			quaternary[index].result = minstr;
		}
	}
}
string newaddr() {
	static int alast = 0;
	string temp = "t",t;
	t=to_string(alast);
	temp +=t;
	alast++;
	return temp;
}
string newlabel(){
	static int llast = 0;
	string temp = "L",t;
	t = to_string(llast);
	temp += t;
	llast++;
	return temp;
}
void outquaternary() {
	int i = 0;
	pofile<<setiosflags(ios::left);
	for (auto iter = quaternary.begin(); iter != quaternary.end(); iter++) {
		pofile<<setw(10) << i<<setw(20)<<iter->operate << setw(20) << iter->operand1 << setw(20) << iter->operand2 << setw(20) << iter->result << setw(20) << endl;
		i++;
	}
	pofile << resetiosflags(ios::left);
	pofile.close();
}
//产生四元式
void gen(string operate,string operand1,string operand2,string result) {
	//pofile <<operate<< setw(5) << operand1 <<setw(5) << operand2 <<setw(5)<<result<<setw(5)<< endl;
	Quaternary tempQ;
	tempQ.operand1 = operand1;
	tempQ.operand2 = operand2;
	tempQ.operate = operate;
	tempQ.result = result;
	quaternary.push_back(tempQ);
}
//最后一步规约  不需要属性的计算
attribute S(vector<attribute>&attributes) {
	attribute temp;

	backpatch(attributes.back().snextlist, minstr1);//A.nextlist
	backpatch(attributes[2].btruelist, minstr2);
	temp.snextlist = attributes[2].bfalselist;
	gen("goto"," "," ",minstr1);

	backpatch(temp.snextlist, to_string(quaternary.size()));
	gen("return", " ", " ", " ");
	
	temp.symbol = 'S';
	return temp;
}
//这个也不需要属性的而计算  输出四元式就可以
attribute A( vector<attribute>&attributes) {

	gen("=", attributes.back().addr, " ", attributes[0].sval);
	attribute temp;
	temp.symbol = 'A';
	return temp;
}
//仅有属性计算 但只是综合属性的复制  不需要新的 addr
attribute M( vector<attribute>&attributes) {
	attribute temp;
	temp.addr = attributes.back().addr;
	temp.symbol = 'M';
	return temp;
}
attribute E(int proi, vector<attribute>&attributes) {
	attribute temp;
	if (proi == 3) {
		temp.addr = attributes.back().addr;//不需要有新的addr
	}
	else if (proi == 4) {
		temp.addr = newaddr();
		gen("+", attributes[0].addr, attributes.back().addr, temp.addr);
	}
	else if (proi == 5) {
		temp.addr = newaddr();
		gen("-", attributes[0].addr, attributes.back().addr, temp.addr);
	}
	temp.symbol = 'E';
	return temp;
}
attribute N( vector<attribute>&attributes) {
	attribute temp;
	temp.addr = attributes.back().addr;
	temp.symbol = 'N';
	return temp;
}
attribute T(int proi, vector<attribute>&attributes) {
	attribute temp;
	if (proi == 7) {
		temp.addr = attributes.back().addr;//不需要有新的addr
	}
	else if (proi == 8) {
		temp.addr = newaddr();
		gen("*", attributes[0].addr, attributes.back().addr, temp.addr);
	}
	else if (proi == 9) {
		temp.addr = newaddr();
		gen("/", attributes[0].addr, attributes.back().addr, temp.addr);
	}
	temp.symbol = 'T';
	return temp;
}
attribute F(int proi, vector<attribute>&attributes) {
	attribute temp;
	if (proi == 10) {
		temp.addr = attributes.back().sval;//不需要有新的addr
	}
	else if (proi == 11) {
		temp.addr = attributes.back().nval;//传值
	}
	else if (proi == 12) {
		temp.addr = attributes[1].addr;
	}
	temp.symbol = 'F';
	return temp;
}
attribute C(int proi, vector<attribute>&attributes) {
	attribute temp;
	string naddr=newaddr();
	if (proi == 13) {
		temp.btruelist.push_back(makelist(quaternary.size() + 1));
		temp.bfalselist.push_back(makelist(quaternary.size() + 2));
		gen(">", attributes[0].sval, attributes.back().sval, naddr);
		gen("if", naddr, "goto", " ");
		gen("else", " ", "goto", " ");
	}
	else if (proi == 14) {
		temp.btruelist.push_back(makelist(quaternary.size() + 1));
		temp.bfalselist.push_back( makelist(quaternary.size()+2));
		gen("<", attributes[0].sval, attributes.back().sval, naddr);
		gen("if", naddr, "goto", " ");
		gen("else", " ", "goto", " ");
	}
	else if (proi == 15) {
		temp.btruelist.push_back(makelist(quaternary.size() + 1));
		temp.bfalselist.push_back(makelist(quaternary.size() + 2));
		gen(">=", attributes[0].sval, attributes.back().sval, naddr);
		gen("if", naddr, "goto", " ");
		gen("else", " ", "goto","");
	}
	else if (proi == 16) {
		temp.btruelist.push_back(makelist(quaternary.size() + 1));
		temp.bfalselist.push_back(makelist(quaternary.size() + 2));
		gen("<=", attributes[0].sval, attributes.back().sval, naddr);
		gen("if", naddr, "goto", " ");
		gen("else", " ", "goto", " ");
	}
	temp.symbol = 'C';
	return temp;
}
void initpro() {//存储产生式
	pro[0].left = 'S'; pro[0].right = "w(C)A";
	pro[1].left = 'A'; pro[1].right = "i=M";//M==E'

	pro[2].left = 'M'; pro[2].right = "E";//M==E'
	pro[3].left = 'E'; pro[3].right = "N";//N==T'
	pro[4].left = 'E'; pro[4].right = "E+N";
	pro[5].left = 'E'; pro[5].right = "E-N";//N==T'

	pro[6].left = 'N'; pro[6].right = "T";//N==T'
	pro[7].left = 'T'; pro[7].right = "F";
	pro[8].left = 'T'; pro[8].right = "T*F";
	pro[9].left = 'T'; pro[9].right = "T/F";

	pro[10].left = 'F'; pro[10].right = "i";
	pro[11].left = 'F'; pro[11].right = "n";
	pro[12].left = 'F'; pro[12].right = "(M)";//M==E'

	pro[13].left = 'C'; pro[13].right = "i>i";
	pro[14].left = 'C'; pro[14].right = "i<i";
	pro[15].left = 'C'; pro[15].right = "igi";//>=   ==     GE  ==g   小g
	pro[16].left = 'C'; pro[16].right = "ili";//<=   ==     LE  ==l   小L   这长得和1（一）一样
}
//判断一个文法符号串是不是哪一个产生式的右部
int isright(string &right) {
	for (int i = 0; i < 17; i++) {
		if (pro[i].right == right) {//遍历判断是不是文法右部的符号串
			//如果是就返回  该产生式的左部字符
			return i;
		}
	}
	return FALSE;//标识不是该文法的文法符号串
}
void midAct1(string lable1, string lable2) {
	//cfalse = "L3";
	//ctrue = lable2;
	//gen("label", " ", " ", lable1);
	//拉链回填
	minstr1 = to_string(quaternary.size());//下一条指令的地址  就是 四元式指令前面的标号

}
void midAct2(string lable1, string lable2) {
	minstr2 = to_string(quaternary.size());
	//whilebegin = lable1;
	//gen("goto", " ", " ", " ");//跳转地址先空着
}
void midAct(int which) {
	string lable1 = newlabel();
	string lable2 = newlabel();
	switch (which) {
	case 1:midAct1(lable1,lable2); break;
	case 2:midAct2(lable1,lable2); break;
	}
}

attribute action(int proi,vector<attribute>&attributes) {//不同产生式的动作 根据 proi找产生式
	attribute temp;
	switch (proi) {
	case 0:
		temp=S(attributes);
		break;
	case 1:
		temp=A(attributes);
		break;
	case 2:
		temp=M(attributes);
		break;
	case 3:
		temp=E(proi, attributes);
		break;
	case 4:
		temp = E(proi, attributes);
		break;
	case 5:
		temp = E(proi, attributes);
		break;
	case 6:
		temp = N(attributes);
		break;
	case 7:
		temp = T(proi, attributes);
		break;
	case 8:
		temp = T(proi, attributes);
		break;
	case 9:
		temp = T(proi, attributes);
		break;
	case 10:
		temp = F(proi, attributes);
		break;
	case 11:
		temp = F(proi, attributes);
		break;
	case 12:
		temp = F(proi, attributes);
		break;
	case 13:
		temp = C(proi, attributes);
		break;
	case 14:
		temp = C(proi, attributes);
		break;
	case 15:
		temp = C(proi, attributes);
		break;
	case 16:
		temp = C(proi, attributes);
		break;
	
	}
	return temp;
}