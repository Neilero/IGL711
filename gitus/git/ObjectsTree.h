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
    // define the structure here to be able to use it in the class (see full declaration bellow)
    struct ObjectInfo;

    /**
     * Class representing the content of a folder in the repository
     */

    // AB - is it necessary?
    // je suis mitigé sur son utilité
    class ObjectsTree {

        /**
         * path to this folder
         */
        boost::filesystem::path treePath;

        /**
         * folder's contents as a map of sha and objects' information
         */
        std::map<std::string, ObjectInfo> objects;

    public:
        /**
         * Basic constructor specifying the tree location as a path
         * @param treePath the tree location
         */
        ObjectsTree(boost::filesystem::path treePath);

        /**
         * returns the map of all the objects contained in this folder
         * @return a std::map<std::string, gitUtils::ObjectInfo> of the tree's objects
         */
        const std::map<std::string, ObjectInfo> &getObjects() const;

        /**
         * returns the path to this tree
         * @return the path to this tree
         */
        const boost::filesystem::path &getTreePath() const;

        /**
         * add the given object to the tree's content
         * this method creates the required sub-trees if they doesn't exist
         * @param path the path to the object to add
         * @return true if everything was successful, false otherwise
         */
        bool addObject(const boost::filesystem::path &path);

        /**
         * remove the given object from the tree's content
         * this method does NOT remove the empty tree that might result from this removal
         * @param path the object to remove
         * @return true if everything was successful, false otherwise
         */
        bool removeObject(const boost::filesystem::path &path);

        /**
         * write the tree's content in the .git/objects folder of the repository
         * @return the SHA of the file containing this instance's content
         */
        std::string writeTree();
    };



    /**
     * Structure containing information about an object in a gitUtils::ObjectsTree
     */

    struct ObjectInfo {

        /**
         * is the object a directory (i.e. folder) ? if not, it is a file
         */
        bool isDirectory;

        /**
         * the name of the file / folder
         */
        std::string objectName;

        /**
         * a pointer to the gitUtils::ObjectsTree content of the folder or NULL if the object is a file
         */
        std::shared_ptr<ObjectsTree> directoryTree;
    };
}

#endif //GITUS_OBJECTSTREE_H
