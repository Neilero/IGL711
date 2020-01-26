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
	if(!(fs::is_directory(currentPath.append("tests")))){
        fs::create_directory(currentPath.append("tests"));
	}

	// If arguments: 
		// - Checks if -h and --help parameters are ok
	REQUIRE(init("-h") == true);
	REQUIRE(init("--help") == true);
		// - Checks if wrong parameters
	REQUIRE(init("") == false);
	REQUIRE(init(1) == false);

	// If no argument:
		// - Checks if everything is ok and runs the init() method
	REQUIRE(init() == true);
	init();
		// - Checks whether the .git and objects  directories and the index file are created or not
	REQUIRE(fs::is_directory(currentPath.append("tests/.git")) == true);
	REQUIRE(fs::is_directory(currentPath.append("tests/.git/objects")) == true);
	REQUIRE(fs::exists(currentPath.append("tests/.git/index")) == true);

}

TEST_CASE("add command: everything is fine") 
{
	fs::path currentPath = fs::current_path();
	if(!(fs::is_directory(currentPath.append("tests")))){
        fs::create_directory(currentPath.append("tests"));
	}
	fs::ofstream file = fs::ofstream(currentPath.append("test.txt"));
    file.close();
	
	// If no argument:
	REQUIRE(add() == false);

	// If wrong parameter:
	REQUIRE(add(1) == false);
	REQUIRE(add("") == false);

	// If <pathspec> parameter is ok:
		// - Checks if everything is ok and runs the add() method
		REQUIRE(add("test.txt"));
		add("test.txt");
		// - Checks whether the file is added to the staged area or not,
		// 	that the index file is modified with the right information
		// 	and are inserted in the objects directory as a blob
		REQUIRE()


	
		
}

TEST_CASE("commit command: everything is fine") 
{
	
}