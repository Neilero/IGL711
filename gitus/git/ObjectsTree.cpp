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

        // check if given path is valid
        if (!boost::filesystem::exists(path)) {
            return false;
        }

        auto objectRelativePath = boost::filesystem::relative(path, treePath);

        // check if new object is in tree directory or in a subdirectory
        if (objectRelativePath.begin()->string() == "..")
            return false;

        // check if new object is in a subdirectory or is a subdirectory
        if (objectRelativePath.has_parent_path()) {
            auto subdirectoryName = objectRelativePath.begin()->string();
            auto subdirectoryPath = path.string().substr(0, path.string().find_last_of(subdirectoryName)+1);
            auto subdirectorySha = hashFile(subdirectoryPath); //we use the directory's path as ID until asked to write the tree

            // check if subdirectory already exists
            auto subdirectory = objects.find(subdirectorySha);
            if (subdirectory != objects.end()) {
                return subdirectory->second.directoryTree->addObject(path);
            }

            // the subdirectory doesn't exists, so we'll create it
            std::shared_ptr<ObjectsTree> subdirectoryTree(new ObjectsTree(treePath / subdirectoryName));

            // check if there is another object to add, if not, we return true as all the directories were added
            bool objectInserted = true;
            if (boost::filesystem::relative(path, subdirectoryTree->getTreePath()).string() != ".")
                objectInserted = subdirectoryTree->addObject(path);

            ObjectInfo subdirectoryInfo{true, subdirectoryName, subdirectoryTree};
            objects.insert( std::make_pair(subdirectorySha, subdirectoryInfo) );

            return objectInserted;
        }

        // the new object is not in a subdirectory and is a file, so we'll try to add it to this tree
        auto objectName = path.filename().string();
        auto objectSha = getSha1FromFile(path);

        // check object not already present
        if (objects.find(objectSha) != objects.end()) {
            return false;
        }

        ObjectInfo objectInfo{false, objectName, nullptr}; // we don't need an ObjectsTree for a file

        objects.insert( std::make_pair(objectSha, objectInfo) );
        return true;
    }

    bool ObjectsTree::removeObject(const boost::filesystem::path &path) {
        // check if given path is valid
        if (!boost::filesystem::exists(path)) {
            return false;
        }

        auto objectRelativePath = boost::filesystem::relative(path, treePath);

        // check if object is in tree directory or in a subdirectory
        if (objectRelativePath.begin()->string() == "..")
            return false;

        // check if object is in a subdirectory or is a subdirectory
        if (objectRelativePath.has_parent_path()) {
            auto subdirectoryName = objectRelativePath.begin()->string();
            auto subdirectorySha = hashFile(path.string()); //we use the directory's path as ID until asked to write the tree

            // check if subdirectory already exists
            auto subdirectory = objects.find(subdirectorySha);
            if (subdirectory != objects.end()) {
                return subdirectory->second.directoryTree->removeObject(path);
            }

            // the subdirectory doesn't exists, we should never reach this since the file was added
            return false;
        }

        // the object is not in a subdirectory, so we'll try to remove it from this tree
        std::string objectSha;
        if (boost::filesystem::is_directory(path))
            objectSha = hashFile(path.string());
        else
            objectSha = getSha1FromFile(path);

        return objects.erase(objectSha) > 0;
    }

    std::string ObjectsTree::writeTree() {
        // the tree is written with the CSV format
        std::stringstream treeContent;

        for(auto & object : objects) {
            auto objectSha = object.first;
            auto [isDirectory, objectName, directory] = object.second;

            if (isDirectory)
                //write the directory content in another file and update its sha for the written file
                objectSha = directory->writeTree();

            // see git tree code (https://git-scm.com/book/en/v2/Git-Internals-Git-Objects#_tree_objects)
            std::string objectCode = isDirectory ? "040000" : "100644";

            treeContent << objectCode << ", "
                        << objectName << ", "
                        << objectSha << "\n";
        }

        createObjectFile(treeContent.str(), "tree");
        return getSha1FromContent(treeContent.str(), "tree");
    }
}
