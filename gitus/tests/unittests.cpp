#define CATCH_CONFIG_MAIN

// RTFM catch2:
// https://github.com/catchorg/Catch2/blob/master/docs/tutorial.md#top

#include "catch.hpp"
#include "init.h"
#include "add.h"
#include "commit.h"
#include "ObjectsTree.h"
#include <filesystem>

namespace fs = boost::filesystem;

TEST_CASE("init command: everything is fine") 
{
	fs::path currentPath = fs::current_path();

	for (fs::directory_iterator endDirIt, it(currentPath/".git"); it != endDirIt; ++it) {	// Clears the .git directory
		fs::remove_all(it->path());
	}

	// If arguments: 
		// - Checks if -h and --help parameters are ok
	REQUIRE(init("-h"));
	REQUIRE(init("--help"));
		// - Checks if wrong parameters
	REQUIRE(init("") == false);
	REQUIRE(init("1") == false);

	// If no argument:
		// - Checks if everything is ok and runs the init() method
	REQUIRE(init());
	init();
		// - Checks whether the .git and objects directories and the index file are created or not
	REQUIRE(fs::is_directory(currentPath / ".git"));
	REQUIRE(fs::is_directory(currentPath / ".git/objects"));
	REQUIRE(fs::exists(currentPath / ".git/index"));

	// Checks if the index file contains "0"
	std::ifstream indexFile = std::ifstream((currentPath/".git/index").string());
	std::string content;

	while (std::getline(indexFile, content))
    {
	  	REQUIRE(content == "0");
    }

	indexFile.close();

}

TEST_CASE("add command: everything is fine") 
{
	fs::path currentPath = fs::current_path();
	std::ofstream file = std::ofstream((currentPath/"test.txt").string());
	std::ofstream file2 = std::ofstream((currentPath/"test2.txt").string());
	std::ifstream indexFile;
	std::string content, content1, content2;
	std::vector<std::string> args;
	int lineCounter;
	std::string tmp1;
	std::string tmp2;

	file2<<"Testing writing text.";

    file.close();
	file2.close();

	// If wrong parameter:
	args.push_back("1");
	args.push_back("");
	REQUIRE(add(args));

	// If <pathspec> parameter is ok:
	
	for (fs::directory_iterator endDirIt, it(currentPath/".git/objects"); it != endDirIt; ++it) {	// Clears the objects directory
		fs::remove_all(it->path());
	}

		// - Checks if everything is ok while running the add() method with 1 file
	args.clear();
	args.push_back("test.txt");
	REQUIRE(add(args));

	for (fs::directory_iterator endDirIt, it(currentPath/".git/objects"); it != endDirIt; ++it) {	// Clears the objects directory
		fs::remove_all(it->path());
	}

		// - Checks the number of files in the object directory before adding a file
	int countBeforeAdding = 0;
	for (fs::directory_iterator endDirIt, it(currentPath/".git/objects"); it != endDirIt; ++it) {
		countBeforeAdding++;
	}

		// - Runs the add() method
	init();
	add(args);

		// - Checks if the index file contains the right count of lines for 1 file added
	lineCounter = 0;
	indexFile = std::ifstream((currentPath/".git/index").string());
	
	while (std::getline(indexFile, content))
    {
	  	lineCounter++;
    }

	REQUIRE(lineCounter == 2);	// 1 represents the number of files added (1) + the default line of the file (1)

	indexFile.close();

		// - Checks the number of files in the object directory after adding a file
	int countAfterAdding = 0;
	for (fs::directory_iterator endDirIt, it(currentPath/".git/objects"); it != endDirIt; ++it) {
		countAfterAdding++;
	}

		// - Checks if the file is added in the object directory
	REQUIRE(countAfterAdding == (countBeforeAdding + 1));

	// - Checks if everything is ok while running the add() method with 2 files
	args.clear();
	args.push_back("test.txt");
	args.push_back("test2.txt");
	REQUIRE(add(args));

	for (fs::directory_iterator endDirIt, it(currentPath/".git/objects"); it != endDirIt; ++it) {	// Clears the objects directory
		fs::remove_all(it->path());
	}

		// - Checks the number of files in the object directory before adding a file
	countBeforeAdding = 0;
	for (fs::directory_iterator endDirIt, it(currentPath/".git/objects"); it != endDirIt; ++it) {
		countBeforeAdding++;
	}

		// - Runs the add() method
	init();
	add(args);

		// - Checks if the index file contains the right count of lines for 2 files added
	lineCounter = 0;
	indexFile = std::ifstream((currentPath/".git/index").string());

	while (std::getline(indexFile, content))
    {
	  	lineCounter++;
    }

	REQUIRE(lineCounter == 3);	// 2 represents the number of files added (2) + the default line of the file (1)

	indexFile.close();

		// - Checks the number of files in the object directory after adding a file
	countAfterAdding = 0;
	for (fs::directory_iterator endDirIt, it(currentPath/".git/objects"); it != endDirIt; ++it) {
		countAfterAdding++;
	}

		// - Checks if the two files are added in the object directory
	REQUIRE(countAfterAdding == (countBeforeAdding + 2));

	for (fs::directory_iterator endDirIt, it(currentPath/".git/objects"); it != endDirIt; ++it) {	// Clears the objects directory
		fs::remove_all(it->path());
	}

	// Checks if the method works with -h
	args.push_back("test.txt");
	args.push_back("-h");
	REQUIRE(add(args));

	for (fs::directory_iterator endDirIt, it(currentPath/".git/objects"); it != endDirIt; ++it) {	// Clears the objects directory
		fs::remove_all(it->path());
	}

	// Checks if the method works with --help
	args.clear();
	args.push_back("test.txt");
	args.push_back("--help");
	REQUIRE(add(args));

	// Checks if, when a modified file is re-added, the index file and the objects directory are well modified

	init();
	file2 = std::ofstream((currentPath/"test2.txt").string());
	file2<<"Testing modifying text.";
	file2.close();
	args.clear();
	args.push_back("test2.txt");
	add(args);
	lineCounter = 0;
	indexFile = std::ifstream((currentPath/".git/index").string());

	while (std::getline(indexFile, content1))
    {
	  	std::cout<<"content1: "<<content1<<std::endl;
		tmp1 += content1 + "\n";
		std::cout<<"tmp1: "<<tmp1<<std::endl;
    }


	//indexFile.close();

	file2 = std::ofstream((currentPath/"test2.txt").string());
	file2<<"Testing modifying text again.";
	file2.close();
	args.clear();
	args.push_back("test2.txt");
	add(args);

	lineCounter = 0;
	indexFile = std::ifstream((currentPath/".git/index").string());

	while (std::getline(indexFile, content2))
    {
	  	std::cout<<"content2: "<<content2<<std::endl;
		tmp2 += content2 + "\n";
		std::cout<<"tmp2: "<<tmp2<<std::endl;
    }

	//indexFile.close();

	std::cout<<"testing: \n"<<tmp1<<" != "<<tmp2<<std::endl;
	REQUIRE_FALSE(tmp1 == tmp2);

	indexFile.close();

}

TEST_CASE("commit command: everything is fine") 
{
	fs::path currentPath = fs::current_path();

	fs::remove_all(currentPath/".git");
	REQUIRE(init());

	std::string message("message");
	std::string user("user commit");
	std::string mail("email commit");
	std::vector<std::string> args;

	// Error with only 1 argument
	args.push_back(message);
	REQUIRE(!commit(args));

	// Error with only 2 arguments
	args.push_back(user);
	REQUIRE(!commit(args));

	// Error with more than 3 arguments
	args.push_back(mail);
	args.push_back(mail);
	REQUIRE(!commit(args));

	// No error with the -h option
	args.push_back("-h");
	REQUIRE(commit(args));

	// No error with the --help option
	args.clear();
	args.push_back(message);
	args.push_back(user);
	args.push_back(mail);
	args.push_back("--help");
	REQUIRE(commit(args));

	// Error with the correct number of arguments but no staged file
	args.clear();
	args.push_back(message);
	args.push_back(user);
	args.push_back(mail);
	REQUIRE(!commit(args));

	std::ofstream newFile1("test1.txt", std::ofstream::trunc);
	std::ofstream newFile2("test2.txt", std::ofstream::trunc);
	fs::create_directories(currentPath/"testFolder");
	std::ofstream newFile23("testFolder/test3.txt", std::ofstream::trunc);
	std::vector<std::string> files;
	files.push_back("test1.txt");
	files.push_back("test2.txt");
	files.push_back("testFolder/test3.txt");
	add(files);

	// - Checks the number of files in the object directory before committing
	int countBeforeCommit = 0;
	for (fs::directory_iterator endDirIt, it(currentPath/".git/objects"); it != endDirIt; ++it) {
		countBeforeCommit++;
	}

	// - Runs the commit() method
	REQUIRE(commit(args));

	// - Checks the number of files in the object directory after committing
	int countAfterCommit = 0;
	for (fs::directory_iterator endDirIt, it(currentPath/".git/objects"); it != endDirIt; ++it) {
		countAfterCommit++;
	}

	// - Checks if the two trees file and the commit were added
	REQUIRE(countAfterCommit == (countBeforeCommit + 3));

	std::string sha;
	int numLines = 0;
	std::ifstream in(".git/index");
	std::string line;
	while ( std::getline(in, line) )
	{
		if (numLines == 0)
		{
			REQUIRE(line != "0"); // Check if the parent commit was changed
			sha = line;
		}
   		++numLines;
	}
	// Check if the index file was cleared
	REQUIRE(numLines == 1);

	numLines = 0;
	std::ifstream commitFile((currentPath / ".git/objects/" / sha.substr(0,2) / sha.substr(2, std::string::npos)).c_str());
	while ( std::getline(commitFile, line) )
	{
		if (numLines == 1)
		{
			std::vector<std::string> result;
			std::istringstream iss(line);
			for(std::string s; iss >> s; )
   				result.push_back(s);

			std::string shaTree = result[1];

			// Check if the tree exists in the objects folder
			REQUIRE(fs::exists(currentPath / ".git/objects/" / shaTree.substr(0,2) / shaTree.substr(2, std::string::npos)));
		}
		if (numLines == 2)
		{
			// Check if the user is the same as given before
			REQUIRE(line.find('\''+user+"\' "+'\''+mail+'\'') == 0);	
		}
		if (numLines == 3)
		{
			// Check if the message is the same as given before	
			REQUIRE(line == ('\''+message+'\''));
		}
		numLines++;
	}
}

TEST_CASE("ObjectsTree: everything is fine") {

    REQUIRE(init());
    gitUtils::ObjectsTree tree(boost::filesystem::current_path());

    auto file1 = fs::current_path() / "a.txt";
    auto folder= fs::current_path() / "folder";
    auto file2 = folder / "a.txt";
    auto file3 = folder / "b.txt";

    SECTION("Check adding objects") {
        // the file must exists
        REQUIRE_FALSE(fs::exists(file1));
        REQUIRE_FALSE(tree.addObject(file1));

        std::ofstream output1(file1.string());
        output1 << "a" << std::endl;
        auto file1Sha = gitUtils::getSha1FromFile(file1);

        REQUIRE(tree.addObject(file1));
        REQUIRE(tree.getObjects().at(file1Sha).objectName == "a.txt");

        // Check adding complex objects
        fs::create_directories(folder);
        auto folderSha = gitUtils::hashFile(folder.string());
        std::ofstream output2(file2.string());
        output2 << "aa" << std::endl;
        auto file2Sha = gitUtils::getSha1FromFile(file2);
        std::ofstream output3(file3.string());
        output3 << "bb" << std::endl;
        auto file3Sha = gitUtils::getSha1FromFile(file3);

        REQUIRE(tree.addObject(file2));
        REQUIRE(tree.addObject(file3));
        REQUIRE(tree.getObjects().size() == 2);
        REQUIRE(tree.getObjects().at(folderSha).directoryTree->getObjects().size() == 2);
        REQUIRE(tree.getObjects().at(folderSha).directoryTree->getObjects().at(file2Sha).objectName == "a.txt");
        REQUIRE(tree.getObjects().at(folderSha).directoryTree->getObjects().at(file3Sha).objectName == "b.txt");
    }


    SECTION("Checking the output file(s) of writeTree") {
        std::ofstream output1(file1.string());
        output1 << "a" << std::endl;
        auto file1Sha = gitUtils::getSha1FromFile(file1);
        fs::create_directories(folder);
        auto folderSha = gitUtils::hashFile(folder.string());
        std::ofstream output2(file2.string());
        output2 << "aa" << std::endl;
        auto file2Sha = gitUtils::getSha1FromFile(file2);
        std::ofstream output3(file3.string());
        output3 << "bb" << std::endl;
        auto file3Sha = gitUtils::getSha1FromFile(file3);

        REQUIRE(tree.addObject(file1));
        REQUIRE(tree.addObject(file2));
        REQUIRE(tree.addObject(file3));

        auto rootFileSha = tree.writeTree();
        REQUIRE_FALSE(rootFileSha.empty());

        SECTION("Checking the root file") {
            auto rootFilePath = fs::current_path() / ".git/objects" / rootFileSha.substr(0, 2) / rootFileSha.substr(2);
            std::ifstream rootFile( rootFilePath.string() );

            std::string line;

            //first word is tree
            rootFile >> line;
            REQUIRE(line.substr(0, 4) == "tree");

            //
        }
    }

    // On teste qu'il y a les bonnes lignes dans le fichier de tree
    // On teste que les codes en début lignes sont okay
    // On teste que ça pointe vers des fichiers de objects qui existent

    fs::remove_all(fs::current_path() / ".git");
    fs::remove_all(fs::current_path() / "folder");
    fs::remove_all(fs::current_path() / "a.txt");
}