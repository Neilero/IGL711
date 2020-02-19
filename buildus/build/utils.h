#ifndef BUILDUS_UTILS_H
#define BUILDUS_UTILS_H

#include <iostream>
#include <vector>
#include <ctime>
#include <sys/types.h>
#include <sys/stat.h>
#include <cerrno>
#include <cstring>
#include <filesystem>

namespace Utils {
    const static std::string temporaryFolder = "intermediate/";

    double FilesDateDifference(const std::string& pathToFile, const std::string& pathToFile1);

    bool DoesCPPNeedRebuild(const std::string& path /*, const std::vector<std::string>& includes */, const std::string& name);

    std::string GetFileNameWithoutExtension(const std::string& pathWithExtension);

    std::string GetExtension(std::string pathWithExtension);
}

#endif //BUILDUS_UTILS_H