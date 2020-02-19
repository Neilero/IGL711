cmake_minimum_required(VERSION 3.10)
project(BuildUS
    VERSION 1.0
    LANGUAGES C CXX
    DESCRIPTION "Compilateur UdeS")

find_library(yaml-cpp REQUIRED)

set(CMAKE_CXX_STANDARD 17)

add_subdirectory(build)
add_subdirectory(tests)

add_executable(buildus buildus.cpp)

target_link_libraries(buildus
	PRIVATE
		build
		yaml-cpp
		"stdc++fs"
	)
