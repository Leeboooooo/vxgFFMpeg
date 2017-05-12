# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.
project(FFMPEG)

#ffmpeg 路径
set(FFMPEG_SRC src/main/cpp/mb_ffmpeg)

#配置加载native依赖
include_directories(${FFMPEG_SRC}/include/)

#动态方式加载
add_library( 
    mbff 
    SHARED
    ${FFMPEG_SRC}/src/cmdutils.c
    ${FFMPEG_SRC}/src/ffmpeg_opt.c
    ${FFMPEG_SRC}/src/ffmpeg.c
    ${FFMPEG_SRC}/src/mb_ff.cpp)

##使用ffmpeg.so
#只需导入第三方.so库，不用构建
add_library(ffmpeg SHARED IMPORTED)

#引入ffmpeg相关的.so文件
set_target_properties(
    ffmpeg
    PROPERTIES IMPORTED_LOCATION
    ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libffmpeg.so)

#已经存在的.so库无需使用target_link_libraries
#target_link_libraries(mbff ${ffmpeg})