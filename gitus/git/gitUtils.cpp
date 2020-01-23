#include "gitUtils.h"

namespace fs = boost::filesystem;

namespace gitUtils {
    int hashFile(const fs::path &path) {
        std::ifstream ifs(path.string());
        std::string fileContent((std::istreambuf_iterator<char>(ifs)), (std::istreambuf_iterator<char>()));

        boost::hash<std::string> string_hash;

        return string_hash(fileContent);
    }

    /**
     * Return true if the current working directory contains a ".git" folder
     * @return true if the current working directory contains a ".git" folder, false otherwise
     */
    bool isValidGitFolder() {
        fs::path potentialGitDirectory = fs::current_path() / ".git";

        return fs::exists(potentialGitDirectory);
    }
}