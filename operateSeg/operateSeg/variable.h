#pragma once
#include"Partition.h"
#include<vector>

vector<Available>  available;
vector<Request> request;//�����
Partition * head = nullptr, *tail = nullptr, *temp;
extern int reqNum = 0;
int total;
unordered_map<string,Process> processTable;//���̱�  ��ǰ�ж��ٽ��� �п������ڴ����