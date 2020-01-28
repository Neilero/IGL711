#ifndef GITUS_OBJECTSTREE_H
#define GITUS_OBJECTSTREE_H

#include <string>
#include <sstream>
#include <vector>
#include <map>
#include <boost/filesystem.hpp>

#include "gitUtils.h"

namespace gitUtils
{
    // declare struct here to be able to use it in the class
    struct ObjectInfo;

    class ObjectsTree {
        boost::filesystem::path treePath;

        // map : object's Sha -> object's infos
        std::map<std::string, ObjectInfo> objects;

    public:
        ObjectsTree(boost::filesystem::path treePath);

        const std::map<std::string, ObjectInfo> &getObjects() const;
        const boost::filesystem::path &getTreePath() const;

        bool addObject(const boost::filesystem::path &path);
        bool removeObject(const boost::filesystem::path &path);

        std::string writeTree();
    };

    struct ObjectInfo {
        bool isDirectory;
        std::string objectName;
        std::shared_ptr<ObjectsTree> directoryTree;
    };
}

#endif //GITUS_OBJECTSTREE_H
