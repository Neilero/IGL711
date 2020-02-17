add_library(build
        utils.h
        utils.cpp
        build.h
        build.cpp
        clean.h
        clean.cpp
        Config.h
        Config.cpp)

#if (UNIX)
#    target_link_libraries(build
#            PUBLIC
#            ${Boost_LIBRARIES}
#            ${ZLIB_LIBRARIES}
#            )
#else ()
#    target_link_libraries(build
#            PUBLIC
#            ${Boost_LIBRARIES}
#            )
#endif ()
#
#target_include_directories(build
#        PUBLIC
#        $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}>
#        ${Boost_INCLUDE_DIRS}
#        )
