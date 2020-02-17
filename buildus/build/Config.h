#ifndef BUILDUS_CONFIG_H
#define BUILDUS_CONFIG_H

#include <string>
#include <vector>
#include <iostream>
#include <yaml-cpp/yaml.h> //see https://github.com/jbeder/yaml-cpp

class Config {
    struct CompileFile
    {
        std::string name;
        std::string path;
    };

    struct ConfigParsingException : public std::logic_error {
        explicit ConfigParsingException(const std::string &message) : logic_error(message) {};
    };

    std::string projet;
    std::vector<std::string> deps_include_var;
    std::vector<std::string> deps_include_head;
    std::vector<std::string> deps_library_var;
    std::vector<std::string> deps_library_libs;
    std::vector<CompileFile> compile;
    std::vector<std::string> package;

public:
    explicit Config(const std::string &configFile);

    [[nodiscard]] const std::string &getProjet() const;

    [[nodiscard]] const std::vector<std::string> &getDepsIncludeVar() const;

    [[nodiscard]] const std::vector<std::string> &getDepsIncludeHead() const;

    [[nodiscard]] const std::vector<std::string> &getDepsLibraryVar() const;

    [[nodiscard]] const std::vector<std::string> &getDepsLibraryLibs() const;

    [[nodiscard]] const std::vector<CompileFile> &getCompile() const;

    [[nodiscard]] const std::vector<std::string> &getPackage() const;

private:
    void compileFiles(const std::string &configFile);
    static void throwYamlError(const std::string& message);
    static void checkMandatoryConfig(const YAML::Node &yamlConfig, const std::vector<std::string>& mandatoryConfigItems);
    void checkAndAddProjet(const YAML::Node& projetNode);
    void checkAndAddDeps_include(const YAML::Node& deps_includeNode);
    void checkAndAddDeps_library(const YAML::Node& deps_libraryNode);
    void checkAndAddCompile(const YAML::Node& compileNode);
    void checkAndAddPackage(const YAML::Node& packageNode);
};


#endif //BUILDUS_CONFIG_H
