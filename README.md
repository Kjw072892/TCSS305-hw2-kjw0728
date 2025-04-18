# TCSS305

Assignment 2

Kassie Whitney

Winter 2025

## Assignment Overview
We are given a GUI and an API sheet.
Our goal is to create a Vehivle abstract class that implents the vehicle 
interface, that will also be the parent of different vehcile classes.
The GUI is already calling the appropriate classes and methods, and all we have to do is implement these methods 
into our abstract and our vehicle classes to create the desired animation effect.
Each vehicle class has their own unquie rules, we are to ensure that our vevhicles abides by theses rules.
We will also create a test class for each vehicle to ensure that there these classes are written to standard.

## Technical Impression:
This assignment was actually quite fun to do!
It was challenging, rewarding, and it really gets you thinking about
the inheritance paradigm.
The biggest challenge was definitely figuring out exactly what each method does, how the GUI calls them, when it
calls it, and how often it gets called.
The first day was essentially spent trying to grasp what is happening with everything.
There were most definitely a ton of informal tests performed.
The API was comprehensive enough, but it required a lot of things to figure out yourself, such as how every method
is called by the GUI, when it calls it, and how often it calls it.
At first, the most challenging thing to do was to try to get the vehicle to move, then it was to try to keep the
vehicle on screen, then it was trying to keep it off the walls and following their designated movements in
accordance with the API sheet.
The other thing that was also quite challenging was trying to figure out how to implement the use of fields within
each vehicle class since not every vehicle class will need to store and utilize data.
Needless to say, there were tons of editing and deleting of these fields.
The ATV was by far the easiest, but a lot of time was spent figuring out which methods should go into abstract, which
methods should stay in the concrete classes, but that was honestly the best part of the assignment.
It really gets you thinking about inheritance and how it truly plays a critical role within object-oriented
programming.
For whatever reason, perhaps its due to having to pass the Junit test, the Human class was most definetly a 
brain-buster.
It was challenging becuase we had to set it up so that the method returns a random direction everytime, except for the 
times its near a crosswalk and the crosswalk direction wont cause the human to move backwards.
The task definetly seemed really simple at first, until a number of bugs started popping up everywhere and the human 
sprite wasn't doing anything of what it was suppose to do. 
We had to also make sure it passed the junit test that was given to us.
That was probably the most challenging part because the test also measures the randomness of the human object, and 
because its not practical to code the 10,000 lines needed to test every possible outcome, occasional failures are to 
be expected.
So with this in mind, it was difficult to discern whether the testing algorithm is just flawed or if the code is 
flawed.
All in all this assignment was challenging, but rewarding.
  

## Unresolved problems in my submission:
The only Unresolved problem is the import in FileLoader, one of the imports uses an "*" at the end thats unable to get changed. 

## Citations and Collaborations:
For this assingment java documents and stack overflow was used to learn more about enumerators, how they work, and 
what their functions are.
Some AI was used to quickly find information and resources that talks about Java's Inheritence hierarchy, abstract classes, and interface classes.

## Questions:
None
