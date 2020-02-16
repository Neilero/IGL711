#ifndef GITUS_ADD_H
#define GITUS_ADD_H

#include <iostream>
#include <boost/filesystem.hpp>
#include "gitUtils.h"

/**
 * Function which take care of the command add
 */
bool add(std::vector<std::string> arguments);

/**
 * Function which add a file to the staging state
 */
bool addFileToGit(fs::path pathToFile); // AB - pourquoi dans le .h?

/**
 * Function which print the help of the Add command
 */
void showAddHelp(); // AB - pourquoi dans le .h?

#endif //GITUS_ADD_H