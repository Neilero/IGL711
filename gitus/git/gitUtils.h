#include <iostream>

#include <string>
#include <boost/filesystem.hpp>
#include <boost/functional/hash.hpp>

namespace fs = boost::filesystem;

namespace gitUtils {
    int hashFile(const fs::path& path);

    bool isValidGitFolder();
}