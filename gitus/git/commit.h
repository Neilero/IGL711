#include <iostream>
#include "gitUtils.h"

void commit(std::vector<std::string> arguments);

void showCommitHelp();

bool makeCommit(std::string message, std::string author, std::string email);