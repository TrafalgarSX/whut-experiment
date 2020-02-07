#pragma once
#include<iostream>
#include<string>
#include<vector>
#include<unordered_map>
#define USEFUL 1
#define NOUSE 0
using namespace std;
//分区说明表
struct Partition {
public:
	int address;//每个表项的起始地址
	int length;//每个表项的长度
	int state;//分区说明表状态
	string processName;//占据一个表项的进程名
	int segnum;//相关进程段号
	int itemCode;//每个表项区号
	Partition *next;
};
//可用表
struct Available {
public:
	int itemCode;//空闲区号  和相应的分区说明表项相同
	int address;//空闲区起始地址
	int length;//空闲区长度

};
//请求表
struct Request {
	string processName;//请求进程名
	int segnum;//哪个进程的哪个段
	int length;//请求进程请求段的长度
};
struct Segtable {//段结构
	int segNum;//段号
	int startAdd=0;//物理开始地址
	int segLength;//段长
	int visitCount=0;//访问次数
	int state=0;//状态位  1在内存，0在外存
};
//进程结构
struct Process {
	string processName;//进程名
	unordered_map<int,Segtable> segTable;//段表
};
bool cmpAsc(const Available& a1, const Available& a2);
bool cmpDec(const Available& a1, const Available& a2);
//输出分区表
void printPartition();
//初始化
void initPartition();
//添加进程  向请求表
void insert();
//合并空闲区
void Mergeidle();
//更新可用表
void updateAvail();
//释放进程所占内存
void freePartition();
//最先
void theFirst(string processName,int segnum);
//最佳
void theBest(string processName,int segnum);
//最坏
void worst(string processName,int segnum);
//为进程分配内存
void allocation();
void createhead(string processName, Request allocatReq);
bool worstmain(string processName, Request allocatReq);
bool firstmain(string processName, Request allocatReq);
bool bestmain(string processName, Request allocatReq);
void compaction();//内存紧缩技术
void eliminate(int size);
void visitraise();