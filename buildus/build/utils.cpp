#include "utils.h"

namespace Utils 
{
    double FilesDateDifference(const std::string& pathToFile, const std::string& pathToFile1)
    {
        struct stat fileInfo1;
        struct stat fileInfo2;

        stat(pathToFile.c_str(), &fileInfo1);
        stat(pathToFile1.c_str(), &fileInfo2);

        std::time_t timeFile1(fileInfo1.st_mtime);
        std::time_t timeFile2(fileInfo2.st_mtime);

        return difftime(timeFile1, timeFile2);
    }

    bool DoesCPPNeedRebuild(const std::string& path, const std::string& name)
    {
        std::filesystem::path currentPath = std::filesystem::current_path();

        if(!std::filesystem::exists(currentPath / Utils::temporaryFolder / (name+".o")))
            return true;

        if (FilesDateDifference((currentPath / Utils::temporaryFolder / (name+".o")).string(), (currentPath / path).string()) <= 0)
            return true;

        // if(std::filesystem::exists(currentPath / (GetFileNameWithoutExtension(path)+".h")))
        // {
        //     if (FilesDateDifference((currentPath / Utils::temporaryFolder / (name+".o")).string(),  (GetFileNameWithoutExtension(path)+".h")) <= 0)
        //         return true;
        // }

        // if(std::filesystem::exists(currentPath / (GetFileNameWithoutExtension(path)+".hpp")))
        // {
        //     if (FilesDateDifference((currentPath / Utils::temporaryFolder / (name+".o")).string(),  (GetFileNameWithoutExtension(path)+".hpp")) <= 0)
        //         return true;
        // }

        return false;
    }

    std::string GetFileNameWithoutExtension(const std::string& pathWithExtension)
    {
        int index = pathWithExtension.find('.');
        return pathWithExtension.substr(0, index);
    }

    std::string GetExtension(std::string pathWithExtension) {
        int index = pathWithExtension.find('.');
        if (index == std::string::npos)
            return pathWithExtension;
        return pathWithExtension.substr(index);
    }
}