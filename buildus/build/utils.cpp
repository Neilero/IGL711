#include "utils.h"

namespace Utils 
{
    bool FilesDateDifference(const std::string& pathToFile, const std::string& pathToFile1)
    {
        struct stat fileInfo1;
        struct stat fileInfo2;

        stat(pathToFile.c_str(), &fileInfo1);
        stat(pathToFile1.c_str(), &fileInfo2);

        uint64_t modification_ms_1 = fileInfo1.st_mtime * 1000 + fileInfo1.st_mtim.tv_nsec / 1000000;

        uint64_t modification_ms_2 = fileInfo2.st_mtime * 1000 + fileInfo2.st_mtim.tv_nsec / 1000000;

        return (modification_ms_1 < modification_ms_2);
    }

    bool DoesCPPNeedRebuild(const std::string& path, const std::string& name)
    {
        std::filesystem::path currentPath = std::filesystem::current_path();

        if(!std::filesystem::exists(currentPath / Utils::temporaryFolder / (name+".o")))
        {
            return true;
        }
        
        if (FilesDateDifference((currentPath / Utils::temporaryFolder / (name+".o")).string(), (currentPath / path).string()))
        {
            return true;
        }

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