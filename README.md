## GitHub Search Application

 Using the Github Developers Api.The application will provide two functionalities, Search repositories and Search User on github.
A sample application to demonstrate how to use Jetpack Architecture Components in an Android Application following the Clean MVVM Architecture concepts.

## ⚙ Tech used
- Github Api
- Kotlin
- Model-View-Viewmodel Architecture
- Navigation component
- Room database
- Retrofit 2
- GSON
- Moshi
- Kotlin Courtines.
- Glide.
-Data-Binding.
-SQL-queries.

This app demonstrates the following views and techniques from the below documentation.

* [Retrofit](https://square.github.io/retrofit/) to make api calls to an HTTP web service.
* [Moshi](https://github.com/square/moshi) which handles the deserialization of the returned JSON to Kotlin data objects. 
* [Glide](https://bumptech.github.io/glide/) to load and cache images by URL.
* [Room](https://developer.android.com/training/data-storage/room) for local database storage.
  
It leverages the following components from the Jetpack library:

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [Data Binding](https://developer.android.com/topic/libraries/data-binding/) with binding adapters
* [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) with the SafeArgs plugin for parameter passing between fragments


## Setting up the Repository

To get started with this project, simply pull the repository and import the project into Android Studio. From there, deploy the project to an emulator or device. 

* NOTE: In order for this project to pull data, you no need to add your API Key and please sure the internet connectivity is good.The Api is used without taking the access token which is permissiable but can access to some limit .Please dont panic if you find some error in between, its the Apis sending us :)
* [Google Developers Console](https://console.developers.google.com/)

## 🚀 Features & Suggested Workflow
- Get list of latest repositories based on search.(Screen 1)
- Get details of any specific repository .On clicking on the item in the list.(Screen 2)
- User details on Web Browser.(Screen 3).Please click on GitHub Icon on Screen2, right of "Repository Last Used field"
- Clean MVVM Architecture.



## Report Issues
Notice any issues with a repository? Please file a github issue in the repository.

## 📷 Screenshots
<img src ="./screensort/Screenshot_1627978813.png" width="260" />
<img src ="./screensort/Screenshot_1627978877.png" width="260" />
<img src ="./screensort/Screenshot_1627984066.png" width="260" />
<img src ="./screensort/Screenshot_1627984071.png" width="260" />
<img src ="./screensort/Screenshot_1627984103.png" width="260" />
<img src ="./screensort/Screenshot_1627984159.png" width="260" />


## 🎯 Requirements
- Android 5.0 and Above
- Min sdk version 21

## 💻 Permissions
- Internet


## 📝 License

```
MIT License

Copyright (c) 2020 Dhiraj Dafouti

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```




