# TodoApp
a small todo app built with Jetpack Compose

## Features
It's a basic todo app. You can list, add, edit and remove todos.

## Architecture
This project is based on [clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) with the following modules:
* domain(kotlin) module for use cases and entities
* data(kotlin) module for repository implementations and data sources definitions
* device(android) for implementation details related to the data sources
* app module which is the presentation layer and where MVVM is used

`app` module depends on all modules because of dependency injection. We need to retrieve koin modules. all the implementations details
use kotlin `internal` modifier. So in theory App module depends only on domain module.  
Domain module has no module dependency.  
Data module depends on domain.  
Device module depends on Data.  
All modules, except `app`, are kotlin [multiplaform modules](https://kotlinlang.org/docs/multiplatform.html)

## Libraries

* Jetpack libraries ([Jetpack Compose](https://developer.android.com/jetpack/compose), [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=en))
* [Koin](https://insert-koin.io) for dependency injection
* [Kotlin](https://kotlinlang.org/) and [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
* [Accompanist](https://google.github.io/accompanist/)
* [SqlDeligth](https://cashapp.github.io/sqldelight/) for data persistence



## Todo
* Tests(I had test before migrating to KMM but after the migration they are all broken. I need to work on that)
* Compose UI testing
* Animations
* CI/CD
