#ifndef BUILDUS_COMPILE_H
#define BUILDUS_COMPILE_H

#include <string>
#include <vector>
#include <stdexcept>

#include "utils.h"
#include "Config.h"

std::string createIncludeOptionsFromVars(const std::vector<std::string>& environmentVars);
std::string createCompileCommand(const std::string& path, const std::string& name, const std::string& includes);

int compileFiles(const Config& configuration);

#endif //BUILDUS_COMPILE_H
