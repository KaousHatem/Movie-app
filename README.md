# MovieApp

## Fave Technical Assignment

### Introduction
sample movie app that shows discover movies and get the details using TMBD Api


## Features

- Ordered by release date (default), alphabetical, rating - can use dropdown 
- Pull to refresh
- Load when scrolled to bottom
- Book the movie 

    
## **Overview**
- ### **Model**

Model is implemented as Repository pattern.  we're retrieving data from the server (by using Retrofit 2).

- ### **View**

View is realised as 2 fragments. First one contains RecyclerView, second one depends on clicks on recycler-items and finally displays detailed data fetched from the Model.
### **Backend**
Use the API from [TMDb](https://developers.themoviedb.org/3/getting-started/introduction) as your data source.

You can use our API Key: `328c283cd27bd1877d9080ccb1604c91`
  
**Sample requests:**

Listing

```
http://api.themoviedb.org/3/discover/movie?api_key=328c283cd27bd1877d9080ccb1604c91&primary_release_date.lte=2016-12-31&sort_by=release_date.desc&page=1
```

Detail

```
http://api.themoviedb.org/3/movie/328111?api_key=328c283cd27bd1877d9080ccb1604c91
```

### Technical requirements:

| iOS | Android | Web (ReactJS) | Web (Vanilla JS) |
| ---- | ------ | ------------- | ---------------- |
| Minimum Swift 4.0 | Kotlin or Java | React based framework (ReactJS, create-react-app, etc) | Use normal / no-framework JS |
| Usage of RxSwift + MVVM | RxJava or RxKotlin or Coroutine | CSS or SASS | CSS or SASS |
| Dependency Injection | MVVM or VIPER | Context API & hooks | State to store value & support 2 way data binding |
| | Dependency Injection - Dagger or Koin or Hilt | Use correct routes, param & URL (include navigation & not found routes) | Use correct routes, param & URL (include navigation & not found routes) |
| | Data Binding | Knowledge & experience using typescript is a must | |

We expect unit tests for the main functionalities only.
You can use third party libraries.

### Code Repo
Please use this repo for your commits.

## Evaluation Criteria
- Clean, readable, maintainable, and performant code (*this include but not limited to:* no unused code, logger, debugger, warning, error, memory leak, infinite loop, lagging)
- Clear documentation that describe your assumptions and design considerations, you can use the wiki in this repo. Don't know how to create wiki? [Create one](https://docs.github.com/en/github/building-a-strong-community/adding-or-editing-wiki-pages)
- Unit Tests will be evaluated

That’s the end of the assignment, we hope you have fun!
looking forward to your submission.
