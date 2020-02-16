#ifndef BUILDUS_BUILD_H
#define BUILDUS_BUILD_H

#include <string>
#include <vector>
#include <filesystem>
#include <yaml-cpp/yaml.h> //see https://github.com/jbeder/yaml-cpp

#include "utils.h"

struct CompileFile
{
    std::string name;
    std::string path;
};

struct Config {
    std::string projet;
    std::vector<std::string> deps_include_var;
    std::vector<std::string> deps_include_head;
    std::vector<std::string> deps_library_var;
    std::vector<std::string> deps_library_libs;
    std::vector<CompileFile> compile;
    std::vector<std::string> package;
};

Config readConfig(const std::string &configFile);
int build(const Config& configuration);
int compileFiles(const Config& configuration);

#endif //BUILDUS_BUILD_H
