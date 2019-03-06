
# BEEApp Demo

Languages, libraries and tools used

- [Kotlin](https://kotlinlang.org/) and [AndroidX]
(https://developer.android.com/jetpack/androidx)
- MVVM Architectural pattern (MVVM + LiveData + Room)
- Unit test demonstration using JUnit and [Espresso]((https://google.github.io/android-testing-support-library/docs/espresso/index.html))
- [RxJava](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid) 
- Dependency Injection with [Dagger 2](http://google.github.io/dagger/)
- [Room](https://developer.android.com/topic/libraries/architecture/room.html) Persistence Library
- Network communication with [Retrofit 2](http://square.github.io/retrofit/)
- Image loading and caching with [Glide](https://github.com/bumptech/glide)

## Features

* Demo for new android component

[TO-DO: DiffUtils]

## Requirements

- JDK 1.8
- Android Studio 3
- [Android SDK](http://developer.android.com/sdk/index.html)
- Android Oreo [(API 28)](http://developer.android.com/tools/revisions/platforms.html)
- Latest Android SDK Tools and build tools.


## Architecture

This project follows Android architecture guidelines that are based on MVVM.

![Architecture Diagram](images/architecture_diagram.png)

# How to build ?

Open terminal and type the below command to generate debug build 

``` ./gradlew assembleDebug ```

## License

```
    Copyright 2018 LucLX

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```
