#include "build.h"

int build(const Config& configuration)
{
    return compileFiles(configuration);
}

int compileFiles(const Config& configuration)
{
    std::string includeString;
    for(auto & includeVar : configuration.deps_include_var)
    {
        std::string pathVar = std::getenv(includeVar.c_str());
        includeString += " -I "+pathVar;
    }
    for(const auto & includeVar : configuration.deps_include_head)
    {
        includeString += " -I " + includeVar;
    }

    for(const auto & index : configuration.compile)
    {
        std::string compileCommand;
        compileCommand.append("g++ -c ")
                      .append(index.path)
                      .append(" -o ")
                      .append(Utils::temporaryFolder)
                      .append(index.name)
                      .append(includeString);

        int returnCode = system(compileCommand.c_str());
        if (returnCode != 0)
            return returnCode;
    }

    return 0;
}

Config readConfig(const std::string &configFile) {
    YAML::Node yaml = YAML::LoadFile(configFile);
    Config config;

    // check mendatory elements
    if(!yaml["projet"])
        throw std::invalid_argument("BuildUS error : please specify the project in the .buildus configuration file.");
    if(!yaml["compile"])
        throw std::invalid_argument("BuildUS error : please specify the compile list in the .buildus configuration file.");
    if(!yaml["package"])
        throw std::invalid_argument("BuildUS error : please specify the package list in the .buildus configuration file.");

    // check and add "projet"
    if (!yaml["projet"].IsScalar())
        throw std::invalid_argument("BuildUS error : please specify only one project in the .buildus configuration file.");
    config.projet = yaml["projet"].as<std::string>();

    // check and add deps_include
    if (yaml["deps_include"]) {
        YAML::Node deps_include = yaml["deps_include"];

        if (!deps_include.IsMap()) {
            throw std::invalid_argument("BuildUS error : please format the content of deps_include as a map in the .buildus configuration file.");
        }

        for (YAML::const_iterator include=deps_include.begin(); include!=deps_include.end(); ++include) {
            if (include->first.as<std::string>() == "var")
                config.deps_include_var.push_back(include->second.as<std::string>());
        }
    }

    // check and add deps_library
    if (yaml["deps_library"]) {
        YAML::Node deps_library = yaml["deps_library"];

        if (!deps_library.IsMap()) {
            throw std::invalid_argument("BuildUS error : please format the content of deps_library as a map in the .buildus configuration file.");
        }

        for (YAML::const_iterator library=deps_library.begin(); library!=deps_library.end(); ++library) {
            // add var
            if (library->first.as<std::string>() == "var")
                config.deps_library_var.push_back(library->second.as<std::string>());

            // add libs
            if (library->first.as<std::string>() == "libs") {
                YAML::Node libs = library->second;
                // check libs type
                if (!libs.IsSequence())
                    throw std::invalid_argument("BuildUS error : please format the content of deps_library's libs as a sequence in the .buildus configuration file.");

                for (const auto& lib : libs)
                    config.deps_library_libs.push_back(lib.as<std::string>());
            }
        }
    }

    // check and add compile
    if (!yaml["compile"].IsSequence())
        throw std::invalid_argument("BuildUS error : please format the content of compile as a sequence of map in the .buildus configuration file.");
    for (const auto& compile : yaml["compile"]) {
        if (!compile.IsMap())
            throw std::invalid_argument("BuildUS error : please format the content of compile's item as a map in the .buildus configuration file.");

        for (YAML::const_iterator compileFile=compile.begin(); compileFile!=compile.end(); ++compileFile) {
            auto name( compileFile->first.as<std::string>() );
            auto path( compileFile->second.as<std::string>() );

            config.compile.push_back({name, path});
        }
    }

    // check and add package
    if (!yaml["package"].IsScalar())
        throw std::invalid_argument("BuildUS error : please format the content of package as a scalar (separate each package with a space) in the .buildus configuration file.");
    std::istringstream packages(yaml["package"].as<std::string>());
    std::string package;
    while (packages >> package) {
        config.package.push_back(package);
    }

    std::cout << "Parsing finished!" << std::endl;
    return config;
}
