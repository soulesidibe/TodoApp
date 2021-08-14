# TodoApp
a small todo app build with Jetpack Compose

## Features
It's a very basic todo app. You can list, add edit and removes todos

## Architecture
This project use a [clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) with modules:
* domain(kotlin) module for use cases and entities
* data(kotlin) module for repository implementation
* device(android) for implementation details related to the data sources
* app module which is the presentation layer and where MVVM is used

App module depends on all module because of dependency injection. We need to retrieve koin modules. all the implementations 
use kotlin internal modifier. So in theory App module depends only on domain module.  
Domain module has no module dependency.  
Data module depends on domain.  
Device module depends on Data.  

##Libraries

* [Jetpack Compose](https://developer.android.com/jetpack/compose)
* Jetpack libraries ([Room](https://developer.android.com/training/data-storage/room), [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=en))
* [Koin](https://insert-koin.io) for dependency injection
* [Kotlin](https://kotlinlang.org/) and [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
* [Accompanist](https://google.github.io/accompanist/)

## Todo
* Unit tests and UI testing
* Centralize dependencies
* Animation between Composables
