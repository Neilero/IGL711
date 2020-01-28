#ifndef GITUS_COMMIT_H
#define GITUS_COMMIT_H

#include <iostream>
#include <boost/uuid/uuid.hpp>
#include <boost/uuid/uuid_generators.hpp>
#include <boost/uuid/uuid_io.hpp> 
#include <ctime>

#include "gitUtils.h"
#include "ObjectsTree.h"

/**
 * Function which take care of the command init
 */
bool commit(std::vector<std::string> arguments);

/**
 * Function which create a new commit file in the objects folder
 */
bool makeCommit(std::string message, std::string author, std::string email);

/**
 * Function which print the help of the Commit command
 */
void showCommitHelp();

#endif //GITUS_COMMIT_H