#include <iostream>

#include "gitUtils.h"

/**
 * Function which take care of the Init command
 */
bool init(char * arg = nullptr);

/**
 * Function which print the help of the Init command
 */
void showInitHelp(const std::string& unknownArg = "");