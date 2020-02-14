#include "build.h"

int build(const Config& configuration)
{
    return compileFiles(configuration);
}

int compileFiles(const Config& configuration)
{
    std::filesystem::create_directory(Utils::temporaryFolder);

    std::string includeString;

    for(auto & includeVar : configuration.deps_include_var)
    {
        char * pathVar = std::getenv(includeVar.c_str());

        if (pathVar == nullptr)
            return -1;

        includeString.append(" -I "+std::string(pathVar));
    }
    for(const auto & includeVar : configuration.deps_include_head)
    {
        includeString.append(" -I " + includeVar);
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

        std::cout << compileCommand << std::endl;

        int returnCode = system(compileCommand.c_str());
        if (returnCode != 0)
            return returnCode;
    }

    return 0;
}