#ifndef BUILDUS_LINK_H
#define BUILDUS_LINK_H

#include <iostream>
#include "compile.h"

int linkFiles(const Config& configuration);
std::string createLinkCommand(const Config& configuration);

#endif