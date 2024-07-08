# Sagor - Automated Fish Farming Monitoring and Controlling
The idea was to make an automated IoT system along with an AI to monitor and control the Fish farms's Pools. We aimed to monitor the environment of each pool in the farm by looking after the pH, temperature and the Oxygen levels. For the Controlling part, we provided a Feeding pump that dispatches the appropriate amount of food into the pools.

That's the abstract of the idea, there are a lot to talk about, but I want to focus on the Mobile application and how did we manage to use the Android technology to provide a friendly-app to be used as a dashboard the Farm's guard (Client).

## Android Tech in Action
We thought of providing insights of the client's pools by visualizing the changes of the environment's characteristics (pH, Temperature, Oxygen also but we couldn't afford it)

Here comes the challenge, the idea is easy to be done but I thought of challenging myself and facing my Android's only fear (Canvas).
I implemented the app using only **Compose Canvas API** to visualize the pools' data, no third-party libraries for the UI. It was one of the most exciting challenges I had.
I found out that it's very hard to maintain a clean code when dealing with Canvas, this was a point I looked after when dealing with the project.

I also thought of dividing the app into features and each feature has its own navigation graph, and none of the features depends directly on each other but some preferences stored like *isUserLoggedIn* which makes a centralized repository that affects multiple features.
I created a dynamic Root navigation graph that adds the features' graphs based on some **NavigationParams** data class instance.

## third-party libraries
I have tried to minimize the usage of Third-party libs to produce a light-weight app, the final app's size was 3MB.
- Dagger Hilt for DI
- Ktor as a Networking client along with some plugins [Logging, Serialization and a DefaultRequest]
- Kotlinx-datetime to manage timestamps coming from the backend in a formalized way.
- Splash Screen API: We needed some customization in the splash screen.


## Sagor Artwork
<span>
  <img src="https://github.com/muhammed9865/Sagor/assets/84887514/7d038562-a94f-4491-84db-cdc6e771fab0" width=400 height=560/>
  <img src="https://github.com/muhammed9865/Sagor/assets/84887514/cdd742a1-d86e-444c-9c58-a7da35c6ac55" width=400 height=560/>
  <img src="https://github.com/muhammed9865/Sagor/assets/84887514/6aba1776-908a-4ba5-9441-9e1b1d5d1467" height=560/>
  <video src="https://github.com/muhammed9865/Sagor/assets/84887514/aa00308c-b3e8-4569-bf61-58d2fe78b47e" width="200" height="100" controls />
</span>
