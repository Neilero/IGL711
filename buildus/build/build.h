#ifndef BUILDUS_BUILD_H
#define BUILDUS_BUILD_H

#include <string>
#include <vector>

#include "utils.h"
#include "Config.h"

int build(const Config& configuration);
int compileFiles(const Config& configuration);

#endif //BUILDUS_BUILD_H
