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