#define CATCH_CONFIG_MAIN

// RTFM catch2:
// https://github.com/catchorg/Catch2/blob/master/docs/tutorial.md#top

#include "catch.hpp"
#include "init.h"
#include "add.h"
#include "commit.h"
#include <filesystem>

namespace fs = boost::filesystem;

TEST_CASE("init command: everything is fine") 
{
	fs::path currentPath = fs::current_path();

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

}

TEST_CASE("add command: everything is fine") 
{
	fs::path currentPath = fs::current_path();
	std::ofstream file = std::ofstream((currentPath/"test.txt").string());
    file.close();
	
	// If no argument:
	//REQUIRE(add() == false);

	// If wrong parameter:
	std::vector<std::string> args;
	args.push_back("1");
	args.push_back("");
	REQUIRE(add(args) == true);

	// If <pathspec> parameter is ok:
		// - Checks if everything is ok and runs the add() method
	args.clear();
	args.push_back("test.txt");
	REQUIRE(add(args));
	add(args);

	// Clears the objects directory
	for (fs::directory_iterator endDirIt, it(currentPath/".git/objects"); it != endDirIt; ++it) {
		fs::remove_all(it->path());
	}
	args.push_back("test.txt");
	args.push_back("-h");
	REQUIRE(add(args) == true);

	// Clears the objects directory
	for (fs::directory_iterator endDirIt, it(currentPath/".git/objects"); it != endDirIt; ++it) {
		fs::remove_all(it->path());
	}
	args.clear();
	args.push_back("test.txt");
	args.push_back("--help");
	REQUIRE(add(args) == true);

		// - Checks whether the file is added to the staged area or not,
		// 	that the index file is modified with the right information
		// 	and are inserted in the objects directory as a blob
	
		
}

TEST_CASE("commit command: everything is fine") 
{
	fs::path currentPath = fs::current_path();

	REQUIRE(init());

	std::string message("Commit message");
	std::string user("Commit user");
	std::string mail("Commit email");
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

	// No error with the correct number of arguments
	args.clear();
	args.push_back(message);
	args.push_back(user);
	args.push_back(mail);
	REQUIRE(commit(args));

	// Pour tester les trees

	

	// On ajoute 3 fichiers dans différents directories
	// On teste qu'il y a les bonnes lignes dans le fichier de tree
	// On teste que les codes en début lignes sont okay
	// On teste que ça pointe vers des fichiers de objects qui existent

}