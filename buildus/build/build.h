#ifndef BUILDUS_BUILD_H
#define BUILDUS_BUILD_H

#include <string>
#include <vector>
#include <stdexcept>

#include "utils.h"

struct CompileFile
{
    std::string name;
    std::string path;
};

struct Config {
    std::string projet;
    std::vector<std::string> deps_include_var;
    std::vector<std::string> deps_library_var;
    std::vector<std::string> deps_library_libs;
    std::vector<CompileFile> compile;
    std::vector<std::string> package;
};

std::string createIncludeOptionsFromVars(const std::vector<std::string>& environmentVars);
std::string createCompileCommand(const std::string& path, const std::string& name, const std::string& includes);

int build(const Config& configuration);
int compileFiles(const Config& configuration);

#endif //BUILDUS_BUILD_H
