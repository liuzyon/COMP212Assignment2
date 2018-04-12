How to run the program?
In the terminal:
javac Main.java
java Main

Input:
1. Input the selected algorithm.
2. Input the size of network.
3. Input the value of f which is the maximum amount of failure.
4. Select the output function: 
    1. Yes 
        It will output the fail processors ID and all processors decisions which is easy to check if algorithm simulates correctness. But it may be messy (especially in terminal) because of large amount of processors.
    2. No
        It will not output the fail processors ID and all processors decisions. Just show the total results of simulator.

 OutputExample:

    Time used: 0.196 s

    Simulator results:
    Number of Processors: 1000
    Number of Decided Processors: 661
    Number of Failed Processors: 339
    Number of Rounds: 501
    Number of Messages: 1998000
    Satisfy Agreement: true
    Agreement Decision: Abort

    if the forth option is yes, it will also print the following information.
    Fail processor:
    <failed proessors information>...

    Processor decision:
    <processors decisions>...
