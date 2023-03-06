Code located at https://github.com/SirSkaro/cs-7641-assignment-2

## Setting up an envrionment - Java
1) Clone the repository
2) Import the project as a Maven project into your favorite IDE (that has Maven support)
3) Configure the project to use JDK17 (JDK8 or 11 will probably work too)

## Setting up an envrionment - Python
1) Clone the repository
2) Setup and activate a virtual environment with Python 3.10.
    * If using Anaconda/miniconda, you can simply use a command like "conda create -n bchurchill6_assignment1 python=3.10".
    * Otherwise, download a distribution of Python 3.10 manually and use it to run "python -m venv bchurchill6_assignment2"
3) Install requirements. With the base of the project as the working directory, you can run the command "pip install -r requirements.txt"

## Running the Optimization Algorithms - Java
Create a run configuration using `Assignment.java` as the entry point. One of four commandline arguments are required:

* `part1`: Randomized optimization algorithms over 3 problems
* `export1`: Perform the optimization problems many times and print the results to a .csv files
* `part2`: Randomized optimization algorithms to train neural network
* `export2`: Randomized optimization algorithms to train neural network multiple times and print the results to .csv files

No other configuration is possible from the commandline. Any other modification will require code changes.

Running `export1` or `export2` will create .csv files where each line contains a three-tuple of <train time, iterations, score>. Don't do `export2` unless you want to hear your CPU scream for 12+ hours. These .csv files are used by the Python scripts to create graphs.

## Running the Graphs - Python
First, copy any CSV files created from the Java code into the root directory of the Python code.

Using an interactive interpreter (with your virtual environments), run the following code:
```python
from graph import Problem
import graph

# graph results for 150-Queens
graph.graph_problem(Problem.N_QUEENS)

# graph results for Four Peaks
graph.graph_problem(Problem.FOUR_PEAKS)

# graph results for Traveling Salesman
graph.graph_problem(Problem.TRAVELING_SALESMAN)

#graph results for Neural Network
graph.graph_nn()
```