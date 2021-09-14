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


Home Screen            |  Detail Screen
:-------------------------:|:-------------------------:
![ScreenShot](/screenshots/home_screen.jpg) |  ![ScreenShot](/screenshots/detail_screen.jpg)


## **All libraries:**
- Android X
- Dagger 2
- Kotlin Coroutines
- Retrofit 2
- OkHTTP 3
- Glide
