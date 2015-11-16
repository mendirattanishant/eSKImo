# eSKImo
## Project Abstract
The purpose of this project is to build an Android app that allows skiers to connect with other skiers and sync up on events. The app will let you create your own “skiing” profile. User’s profile picture, events, current location, ski score and other profile information will be stored. A user can create a ski event where he can set the start location, the ski path and the time at which the event will start. Once the user saves the event, he can invite other users by browsing existing user profiles. When the event starts, participants will be able to view each other’s location in real time. This will help skiers in knowing how others are doing during the event. Users will also be able to track lap times privately. They will be provided with a stopwatch inside the app, which they can use to record lap times. After the completion of an event, the user will be able to trace his path on a map, view the distance he has travelled and view the progress of other users.

## Major Features
1. Login into the app with Facebook and Google (Implementing Oauth2.0)
2. Creation of user profile by extracting user‘s info from their Facebook or Google+ profile
and storing it on database
3. Creation and sharing of ski event with other users
..1. A user will create a ski event and invite ​other users to join. The event will have a
title, description, start and ending time
..2. Users can see the details of the events including the list of users attending that
event and their avatars. On tapping the users their profile screen shows up & ski
records like total time and total ski distance will be displayed
..3. User will be able to view other user’s location in real­time on Google Maps
4. Implementing a Ski tracker:
..1. User can start and end the ski session when the event starts
..i. Start ​a new session will start user’s session and start tracking his location
..ii. Stop ​will stop the session and no more of user’s location data will be
stored
..iii. Start​and Stop​times will be displayed as previous ski session
..2. Each ski session will track the following:
..i. Distance a​user has travelled
..ii. Trace of the trip highlighted in the map
..iii. List of ski records ​will displayed on user's profile and can be viewed by
other users within an event
..5. User Home Screen: User screen in which he can track his ski sessions and see the
events he is going and he has been invited

## Platform and Technology Choices:
..1. Android­ Client interface
..2. SQLite­ Client database
..3. Node.js­ Backend server
..4. MongoDb­ Server database

A## Architecture Flow
The android app would act as the user interface to the application. The Client would interact
with the server by making rest calls over the internet. The server would be built using NodeJS.
The server will accept requests from clients. It would also handle server side database and login
activities. The client location details would also be stored in its local database (SQLite)
