#include <iostream>
#include <ctime>
#include <time.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <cerrno>
#include <cstring>
#include <boost/filesystem.hpp>

namespace Utils 
{
    static std::string temporaryFolder = "/tmp/";

    double FilesDateDifference(std::string pathToFile, std::string pathToFile1)
    {
        struct stat fileInfo1;
        struct stat fileInfo2;

        stat(pathToFile.c_str(), &fileInfo1);
        stat(pathToFile1.c_str(), &fileInfo2);
        
        std::time_t timeFile1(fileInfo1.st_mtime);
        std::time_t timeFile2(fileInfo2.st_mtime);

        return difftime(timeFile1, timeFile2);
    }

    // TODO refaire avec la prise en compte du dossier temporaire des fichiers intermédiaires.
    bool CheckIfFileNeedBuild(std::string name, std::string extension)
    {
        boost::filesystem::path currentPath = boost::filesystem::current_path();

        if(!boost::filesystem::exists(currentPath / (name+extension)))
        {
            return true;
        }

        if (FilesDateDifference((currentPath / (name+extension)).string(), (currentPath / (name+".cpp")).string()) <= 0)
            return true;

        if(boost::filesystem::exists(currentPath / (name+".h")))
        {
            if (FilesDateDifference((currentPath / (name+extension)).string(), (currentPath / (name+".h")).string()) <= 0)
                return true;
        }

        if(boost::filesystem::exists(currentPath / (name+".hpp")))
        {
            if (FilesDateDifference((currentPath / (name+extension)).string(), (currentPath / (name+".hpp")).string()) <= 0)
                return true;
        }

        return false;
    }

    std::string GetFileNameWithoutExtension(std::string pathWithExtension)
    {
        int index = pathWithExtension.find('.');
        return pathWithExtension.substr(0, index);
    }

    std::string GetExtension(std::string pathWithExtension)
    {
        int index = pathWithExtension.find('.');
        if (index == std::string::npos)
            return pathWithExtension;
        return pathWithExtension.substr(index);
    }
}