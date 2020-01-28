#define CATCH_CONFIG_MAIN

// RTFM catch2:
// https://github.com/catchorg/Catch2/blob/master/docs/tutorial.md#top

#include "catch.hpp"
#include "init.h"
#include "add.h"
#include "commit.h"

namespace fs = boost::filesystem;
/*#include <dummy.h>

TEST_CASE("is the world ok") 
{
	REQUIRE(GetAnswerToLifeUniverseAndEverything() == 42);
	REQUIRE(GetAnswerToLifeUniverseAndEverything() != 41);
	REQUIRE(GetAnswerToLifeUniverseAndEverything() != 43);
}*/

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

	// TODO: 0 dans fichier index
	std::ifstream indexFile = std::ifstream((currentPath/"index").string());
	std::string content;

	while (std::getline(indexFile, content))
    {
      std::cout << "content : " << content << '\n';
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

	// If wrong parameter:
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
	
}

