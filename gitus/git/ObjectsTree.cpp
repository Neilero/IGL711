#include "ObjectsTree.h"

#include <utility>

namespace gitUtils
{
    ObjectsTree::ObjectsTree(boost::filesystem::path treePath) : treePath(std::move(treePath)) {}

    const boost::filesystem::path &ObjectsTree::getTreePath() const {
        return treePath;
    }

    const std::map<std::string, ObjectInfo> &ObjectsTree::getObjects() const {
        return objects;
    }

    bool ObjectsTree::addObject(const boost::filesystem::path &path) {
        //TODO : add test exists


        auto objectRelativePath = boost::filesystem::relative(path, treePath);

        // check if new object is in tree directory or subdirectory
        if (objectRelativePath.begin()->string() == "..")
            return false;

        // check if new object is in a subdirectory or is a subdirectory
        if (objectRelativePath.has_parent_path()) {
            auto subdirectoryName = objectRelativePath.begin()->string();
            auto subdirectorySha = hashFile(subdirectoryName);

            // check if subdirectory already exists
            auto subdirectory = objects.find(subdirectorySha);
            if (subdirectory != objects.end()) {
                std::cout << "Exists: " << subdirectoryName << std::endl;
                return subdirectory->second.directoryTree->addObject(path);
            }

            // the subdirectory doesn't exists, so we'll create it
            auto *subdirectoryTree = new ObjectsTree(treePath / subdirectoryName);
            bool objectInserted = subdirectoryTree->addObject(path);

            std::cout << "Created: " << subdirectoryName << std::endl;
            ObjectInfo subdirectoryInfo{true, subdirectoryName, subdirectoryTree};
            objects.insert( std::make_pair(subdirectorySha, subdirectoryInfo) );

            return objectInserted;
        }

        // the new object is not in a subdirectory and is a file, so we'll try to add it to this tree
        auto objectName = path.filename().string();
        auto objectSha = hashFile(path);

        // check object not already present
        if (objects.find(objectSha) != objects.end()) {
            std::cout << "Exists: " << objectName << std::endl;
            return false;
        }

        std::cout << "Created: " << objectName << std::endl;
        ObjectInfo objectInfo{false, objectName, nullptr}; // we don't need an ObjectsTree for a file

        objects.insert( std::make_pair(objectSha, objectInfo) );
        return true;
    }

    bool ObjectsTree::removeObject(const boost::filesystem::path &path) {
        //extract object info
        auto objectName = path.filename();
        auto objectSha = hashFile(objectName);

        // check object exists
        if (objects.find(objectSha) == objects.end())
            return false;

        objects.erase(objectSha);
        return true;
    }

    bool ObjectsTree::writeTree() {
        // the tree is written with the CSV format
        std::stringstream treeContent;

        for(auto & object : objects) {
            auto objectSha = object.first;
            auto [isDirectory, objectName, directory] = object.second;

            if (isDirectory)
                directory->writeTree();

            // see git tree code (https://git-scm.com/book/en/v2/Git-Internals-Git-Objects#_tree_objects)
            std::string objectCode = isDirectory ? "040000" : "100644";

            treeContent << objectCode << ", "
                        << objectName << ", "
                        << objectSha << ", "
                        << "\n";
        }

        return createObjectFile(treeContent.str());
    }

    ObjectInfo::~ObjectInfo() {
        delete directoryTree;
    }
}
