#include "build.h"

int build(const Config& configuration)
{
    return compileFiles(configuration);
}

int compileFiles(const Config& configuration)
{
    std::filesystem::create_directory(Utils::temporaryFolder);

    std::string includeString;
    std::vector<std::string> includesPaths;

    for(auto & includeVar : configuration.deps_include_var)
    {
        char * pathVar = std::getenv(includeVar.c_str());

        if (pathVar == nullptr)
            return -1;

        includeString.append(" -I "+std::string(pathVar));
    }
    for(const auto & includeHeader : configuration.deps_include_head)
    {
        includeString.append(" -I " + includeHeader);

        includesPaths.push_back(includeHeader);
    }

    for(const auto & file : configuration.compile)
    {
        if (Utils::DoesCPPNeedRebuild(file.path, includesPaths, file.name))
        {
            std::string compileCommand;
            compileCommand.append("g++ -c ")
                        .append(file.path)
                        .append(" -o ")
                        .append(Utils::temporaryFolder)
                        .append(file.name)
                        .append(".o")
                        .append(includeString);

            //std::cout << compileCommand << std::endl;

            int returnCode = system(compileCommand.c_str());
            if (returnCode != 0)
                return returnCode;
            }
    }

    return 0;
}