#ifndef GITUS_INIT_H
#define GITUS_INIT_H

#include <iostream>

#include "gitUtils.h"

/**
 * Function which take care of the Init command
 */
bool init(const char * arg = nullptr);

/**
 * Function which print the help of the Init command
 */
void showInitHelp(const std::string& unknownArg = "");

#endif //GITUS_INIT_H