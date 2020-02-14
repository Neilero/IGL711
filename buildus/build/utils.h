#ifndef BUILDUS_UTILS_H
#define BUILDUS_UTILS_H

#include <iostream>
#include <ctime>
#include <sys/types.h>
#include <sys/stat.h>
#include <cerrno>
#include <cstring>
//#include <boost/filesystem.hpp>
#include <filesystem>

namespace Utils {
    const static std::string temporaryFolder = "/tmp/";

    double FilesDateDifference(const std::string& pathToFile, const std::string& pathToFile1);

    bool CheckIfFileNeedBuild(const std::string& name, const std::string& extension);

    std::string GetFileNameWithoutExtension(const std::string& pathWithExtension);

    std::string GetExtension(std::string pathWithExtension);
}

#endif //BUILDUS_UTILS_H
