#include <string>
#include <vector>
#include "utils.cpp"

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

int build(Config configuration)
{
    return compileFiles(configuration);
}

int compileFiles(Config configuration)
{
    std::string includeString;
    for(int indexIncludeVar = 0; indexIncludeVar < configuration.deps_include_var.size; indexIncludeVar++)
    {
        std::string pathVar = std::getenv(configuration.deps_include_var[indexIncludeVar].c_str());
        includeString += " -I "+pathVar;
    }
    for(int indexIncludeVar = 0; indexIncludeVar < configuration.deps_include_head.size; indexIncludeVar++)
    {
        includeString += " -I "+configuration.deps_include_head[indexIncludeVar];
    }

    for(int index = 0; index < configuration.compile.size; index++)
    {
        std::string compileCommand = "g++ -c "+ configuration.compile[index].path + " -o " + Utils::temporaryFolder + configuration.compile[index].name+includeString;

        int returnCode = system(compileCommand.c_str());
        if (returnCode != 0)
            return returnCode;
    }

    return 0;
}