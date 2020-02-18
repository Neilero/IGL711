#include "Config.h"

Config::Config(const std::string &configFile) {
    compileFiles(configFile);
}

const std::string &Config::getProjet() const {
    return projet;
}

const std::vector<std::string> &Config::getDepsIncludeVar() const {
    return deps_include_var;
}

const std::vector<std::string> &Config::getDepsLibraryVar() const {
    return deps_library_var;
}

const std::vector<std::string> &Config::getDepsLibraryLibs() const {
    return deps_library_libs;
}

const std::vector<Config::CompileFile> &Config::getCompile() const {
    return compile;
}

const std::vector<std::string> &Config::getPackage() const {
    return package;
}

void Config::compileFiles(const std::string &configFile) {
    YAML::Node yaml = YAML::LoadFile(configFile);

    // check mandatory elements
    std::vector<std::string> mandatoryConfigItems{"projet", "compile", "package"};
    checkMandatoryConfig(yaml, mandatoryConfigItems);

    // check and add "projet"
    checkAndAddProjet(yaml["projet"]);

    // check and add "deps_include"
    checkAndAddDeps_include(yaml["deps_include"]);

    // check and add "deps_library"
    checkAndAddDeps_library(yaml["deps_library"]);

    // check and add "compile"
    checkAndAddCompile(yaml["compile"]);

    // check and add "package"
    checkAndAddPackage(yaml["package"]);
}

void Config::checkMandatoryConfig(const YAML::Node &yamlConfig, const std::vector<std::string>& mandatoryConfigItems) {
    for (const auto& mandatoryItem : mandatoryConfigItems) {
        if (!yamlConfig[mandatoryItem])
            throwYamlError("specify the " + mandatoryItem);
    }
}

void Config::throwYamlError(const std::string& message) {
    const std::string errorPrefix("BuildUS error : please ");
    const std::string errorSuffix(" in the .buildus configuration file.");

    std::string fullMessage = errorPrefix + message + errorSuffix;
    throw ConfigParsingException(fullMessage);
}

void Config::checkAndAddProjet(const YAML::Node& projetNode) {
    // no check valid node because mandatory

    // check node type
    if (!projetNode.IsScalar())
        throwYamlError("specify only one project");

    projet = projetNode.as<std::string>();
}

void Config::checkAndAddDeps_include(const YAML::Node &deps_includeNode) {
    // check valid node
    if (!deps_includeNode)
        return;

    // check node type
    if (!deps_includeNode.IsMap()) {
        throwYamlError("format the content of deps_include as a map");
    }

    for (YAML::const_iterator include=deps_includeNode.begin(); include!=deps_includeNode.end(); ++include) {
        if (include->first.as<std::string>() == "var")
            deps_include_var.push_back(include->second.as<std::string>());
    }
}

void Config::checkAndAddDeps_library(const YAML::Node &deps_libraryNode) {
    // check valid node
    if (!deps_libraryNode)
        return;

    // check node type
    if (!deps_libraryNode.IsMap()) {
        throwYamlError("format the content of deps_library as a map");
    }

    for (YAML::const_iterator library=deps_libraryNode.begin(); library!=deps_libraryNode.end(); ++library) {
        // add var
        if (library->first.as<std::string>() == "var")
            deps_library_var.push_back(library->second.as<std::string>());

        // add libs
        if (library->first.as<std::string>() == "libs") {
            YAML::Node libs = library->second;

            // check libs node type
            if (!libs.IsSequence())
                throwYamlError("format the content of deps_library's libs as a sequence");

            for (const auto& lib : libs)
                deps_library_libs.push_back(lib.as<std::string>());
        }
    }
}

void Config::checkAndAddCompile(const YAML::Node &compileNode) {
    // no check valid node because mandatory

    // check node type
    if (!compileNode.IsSequence())
        throwYamlError("format the content of compile as a sequence of map");

    for (const auto& compileMap : compileNode) {

        // check compile's item node type
        if (!compileMap.IsMap())
            throwYamlError("format the content of compile's items as a map");

        for (YAML::const_iterator compileFile=compileMap.begin(); compileFile!=compileMap.end(); ++compileFile) {
            auto name( compileFile->first.as<std::string>() );
            auto path( compileFile->second.as<std::string>() );

            this->compile.push_back({name, path});
        }
    }
}

void Config::checkAndAddPackage(const YAML::Node &packageNode) {
    // no check valid node because mandatory

    // check node type
    if (!packageNode.IsScalar())
        throwYamlError("format the content of package as a scalar (separate each package with a space)");

    std::istringstream packages(packageNode.as<std::string>()); // used to split the packages' list by space
    std::string packageBuffer;
    while (packages >> packageBuffer) {
        package.push_back(packageBuffer);
    }
}

bool Config::CompileFile::operator==(const Config::CompileFile &rhs) const {
    return name == rhs.name &&
           path == rhs.path;
}

bool Config::CompileFile::operator!=(const Config::CompileFile &rhs) const {
    return !(rhs == *this);
}
