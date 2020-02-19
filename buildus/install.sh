#!/bin/bash

echo Create temp folder yamlCpp_install
mkdir yamlCpp_install
(
  cd yamlCpp_install || return

  echo Download yaml-cpp v0.6.3
  wget https://github.com/jbeder/yaml-cpp/archive/yaml-cpp-0.6.3.tar.gz

  echo Extract library
  tar xzf yaml-cpp-0.6.3.tar.gz
  cd yaml-cpp-yaml-cpp-0.6.3 || return

  echo Run CMake
  cmake .

  echo Run make
  make

  echo Run sudo make install
  sudo make install
)
echo Remove temp folder yamlCpp_install
rm -rf yamlCpp_install

echo yaml-cpp has been successfully installed!