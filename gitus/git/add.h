#include <iostream>
#include <boost/filesystem.hpp>
#include "gitUtils.h"

/**
 * Function which print the help of the Add command
 */
void showAddHelp();

/**
 * Function which take care of the command add
 */
void add(std::vector<std::string> arguments);

/**
 * Function which add a file to the staging state
 */
bool addFileToGit(fs::path pathToFile);