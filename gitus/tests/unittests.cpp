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
	REQUIRE(init("-h") == true);
	REQUIRE(init("--help") == true);
		// - Checks if wrong parameters
	REQUIRE(init("") == false);
	REQUIRE(init("1") == false);

	// If no argument:
		// - Checks if everything is ok and runs the init() method
	REQUIRE(init() == true);
	init();
		// - Checks whether the .git and objects directories and the index file are created or not
	REQUIRE(fs::is_directory(currentPath/".git") == true);
	REQUIRE(fs::is_directory(currentPath/".git/objects") == true);
	REQUIRE(fs::exists(currentPath/".git/index") == true);

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
	std::vector<std::string> args;
	file2<<"Testing writing text.";

    file.close();
	file2.close();

	// If wrong parameter:P
	args.push_back("1");
	args.push_back("");
	REQUIRE(add(args) == true);

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
	add(args);

		// - Checks the number of files in the object directory after adding a file
	int countAfterAdding = 0;
	for (fs::directory_iterator endDirIt, it(currentPath/".git/objects"); it != endDirIt; ++it) {
		countAfterAdding++;
	}

		// - Checks if the file is added in the object directory
	REQUIRE(countAfterAdding == (countBeforeAdding + 1));

	// - Checks if everything is ok while running the add() method with 1 file
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
	std::cout<<"countBefore"<<countBeforeAdding<<std::endl;

		// - Runs the add() method
	add(args);

		// - Checks the number of files in the object directory after adding a file
	countAfterAdding = 0;
	for (fs::directory_iterator endDirIt, it(currentPath/".git/objects"); it != endDirIt; ++it) {
		countAfterAdding++;
	}
	std::cout<<"countAfter"<<countAfterAdding<<std::endl;

		// - Checks if the two files are added in the object directory
	REQUIRE(countAfterAdding == (countBeforeAdding + 2));

	for (fs::directory_iterator endDirIt, it(currentPath/".git/objects"); it != endDirIt; ++it) {	// Clears the objects directory
		fs::remove_all(it->path());
	}
	args.push_back("test.txt");
	args.push_back("-h");
	REQUIRE(add(args) == true);

	for (fs::directory_iterator endDirIt, it(currentPath/".git/objects"); it != endDirIt; ++it) {	// Clears the objects directory
		fs::remove_all(it->path());
	}
	args.clear();
	args.push_back("test.txt");
	args.push_back("--help");
	REQUIRE(add(args) == true);

	//
	
	/*
	Add :
	OKTester si ça ajoute dans le object un fichier
	Tester si ça ajoute dans index nblignes -1
	Tester si quand on le fait une deuxième fois, ça ne crée pas les fichiers en double (dans objects et dans le fichier index)
	Tester si quand on modifie un fichier qui a été ajouté et qu'on l'ajoute une deuxième fois, ça modifie le index à la bonne ligne 
	et ça crée un nouveau fichier dans objects
	*/
		
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
