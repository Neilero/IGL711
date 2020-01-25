#include <iostream>
#include <boost/uuid/uuid.hpp>
#include <boost/uuid/uuid_generators.hpp>
#include <boost/uuid/uuid_io.hpp> 

#include "gitUtils.h"

void commit(std::vector<std::string> arguments);

void showCommitHelp();

bool makeCommit(std::string message, std::string author, std::string email);