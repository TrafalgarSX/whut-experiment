#pragma once
#include"Partition.h"
#include<vector>

vector<Available>  available;
vector<Request> request;//请求表
Partition * head = nullptr, *tail = nullptr, *temp;
extern int reqNum = 0;
int total;
unordered_map<string,Process> processTable;//进程表  当前有多少进程 有可能在内存或不在