#include "build.h"

int build(const Config& configuration)
{
    return compileFiles(configuration);
}

int compileFiles(const Config& configuration)
{
    // create the intermediate folder if it doesn't exist
    std::filesystem::create_directory(Utils::temporaryFolder);

    // generate the string of includes options based on environment variables
    std::string includes = createIncludeOptionsFromVars(configuration.deps_include_var);

    for(const auto & file : configuration.compile)
    {
        if (Utils::DoesCPPNeedRebuild(file.path, file.name))
        {
            std::string compileCommand = createCompileCommand(file.path, file.name, includes);

            int returnCode = system(compileCommand.c_str());

            if (returnCode != 0)
            {
                return returnCode;
            }
        }
    }

    return 0;
}

std::string createIncludeOptionsFromVars(const std::vector<std::string>& environmentVars)
{
    std::string includeString;
    std::vector<std::string> includesPaths;

    for(auto & includeVar : environmentVars)
    {
        char * pathVar = std::getenv(includeVar.c_str());

        if (pathVar == nullptr)
        {
            throw std::runtime_error("The environment variable "+includeVar+" does not exist.");
        }
        
        includeString.append(" -I "+std::string(pathVar));
    }

    return includeString;
}

std::string createCompileCommand(const std::string& path, const std::string& name, const std::string& includes)
{
    std::string compileCommand;
    compileCommand.append("g++ -c ")
                .append(path)
                .append(" -o ")
                .append(Utils::temporaryFolder)
                .append(name)
                .append(".o")
                .append(includes);

    return compileCommand;
}