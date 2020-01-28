add_library(
    git
    add.h
    add.cpp
    commit.h
    commit.cpp
    init.h
    init.cpp
    gitUtils.h
    gitUtils.cpp
	ObjectsTree.cpp
	ObjectsTree.h)
if (UNIX)
target_link_libraries(git 
    PUBLIC 
		${Boost_LIBRARIES}
		${ZLIB_LIBRARIES}
)
else ()
target_link_libraries(git 
    PUBLIC 
		${Boost_LIBRARIES}
)
endif ()	
target_include_directories(git 
    PUBLIC 
        $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}>
		${Boost_INCLUDE_DIRS}
)
