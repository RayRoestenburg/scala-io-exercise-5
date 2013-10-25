Exercise 5
==========

In this exercise we continue with the end result of [exercise 4](http://github/RayRoestenburg/scala-io-exercise-4). 
In this exercise we will use the **EventStream** to publish events and subscribe to them.
A new actor is added, the **PalindromeCountActor**, which can keep track of the number of palindromes that have been found. There was a problem in the test for ReverseActor that we needed to sleep, which we can solve with events as well. The ReverseActor should publish a **ReverseInitialized** event when it is initialized. The test can subscribe the testActor to listen for the ReverseInitialized event and expect this message in the test before actually testing reverse. 


###Objective
Learn about the **EventStream**, how it can be used to communicate between actors in a loosely coupled manner (publish/subscribe).

###What is already prepared

The end result of exercise 4 is the starting point of this exercise. A **PalindromeCountActor** is added to the receptionist and a **PalindromeCountRoute** has been added to the REST routes, as well as a JSON reponse which contains the count. An example of using httpie to get the count:

    http GET localhost:8000/count-palindromes

The response should look like: 

    HTTP/1.1 200 OK

    {
      "count": 0
    }     

###The Exercise
- Subscribe the PalindromeCountActor actor to PalindromeFound case class
- Create a case class ReverseInitialized which contains the ActorRef of the ReverseActor
- Create a case object PalindromeFound
- Publish the ReverseInitialized message containing the ActorRef of this actor when the ReverseActor is properly initialized
- Publish the PalindromeFound message in the ReverseActor when a palindrome is found
- In ReverseActorSpec, subscribe the testActor to the ReverseActorReady event
- In ReverseActorSpec, expect the ReverseInitialized



