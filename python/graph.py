import os
import numpy
import matplotlib.pyplot as plt
from enum import Enum


class Problem(Enum):
    N_QUEENS = "150-Queens"
    FOUR_PEAKS = "Four Peaks"
    TRAVELING_SALESMAN = "Traveling Salesman with 25 cities"


def graph_problem(problem: Problem):
    datasets = _get_data_for_problem(problem.value)
    _graph_data(datasets)

def _get_data_for_problem(problem_name):
    path = "."
    files = [file for file in os.listdir(path) if file.startswith(problem_name)]
    datasets = {}

    for file in files:
        algorithm = file.split(' ')[-1].split('.')[0]
        dataset = numpy.loadtxt(
            fname=file,
            delimiter=',',
            dtype=float
        )
        datasets[algorithm] = dataset

    return datasets


def _graph_data(datasets):
    fig, ax = plt.subplots(1, 3)

    ax[0].set_title("Fitness Scores")
    ax[0].set_ylabel("Score")
    ax[0].set_xlabel("Trial")
    ax[1].set_title("Wall Clock Times")
    ax[1].set_ylabel("Time (in ms)")
    ax[1].set_xlabel("Trial")
    ax[2].set_title("Iterations to Converge")
    ax[2].set_ylabel("Iterations")
    ax[2].set_xlabel("Trial")

    for algorithm, dataset in datasets.items():
        trials = numpy.arange(1, dataset.shape[0] + 1)
        ax[0].plot(trials, dataset[:, 2], label=f'{algorithm}', marker=None, drawstyle="default",
                   linestyle='solid')
        ax[1].plot(trials, dataset[:, 0], label=f'{algorithm}', marker=None, drawstyle="default",
                   linestyle='solid')
        ax[2].plot(trials, dataset[:, 1], label=f'{algorithm}', marker=None, drawstyle="default",
                   linestyle='solid')

    ax[0].legend(loc="best")
    ax[1].legend(loc="best")
    ax[2].legend(loc="best")
    return fig, ax


def graph_nn():
    fig, ax = plt.subplots(1, 3)
    percentages = numpy.arange(0, 11)[1:-1] * 10
    drawstyle = 'default'

    ax[0].set_title("Learning Curve")
    ax[0].set_xlabel("Percentage Training Set")
    ax[0].set_ylabel("Error")
    ax[1].set_title("Wall Clock Times")
    ax[1].set_ylabel("Time (in ms)")
    ax[1].set_xlabel("Percentage Training Set")
    ax[2].set_title("Iterations to Converge")
    ax[2].set_ylabel("Iterations")
    ax[2].set_xlabel("Percentage Training Set")

    test_errors = {'RHC': [], 'SA': [], 'GA': []}
    iterations = {'RHC': [], 'SA': [], 'GA': []}
    times = {'RHC': [], 'SA': [], 'GA': []}

    for percentage in percentages:
        datasets = _get_data_for_problem(f'NN {percentage}')
        for algorithm, dataset in datasets.items():
            test_errors[algorithm].append(dataset[:, 2].mean())
            times[algorithm].append(dataset[:, 0].mean())
            iterations[algorithm].append(dataset[:, 1].mean())

    for algorithm in ['RHC', 'SA', 'GA']:
        ax[0].plot(percentages, test_errors[algorithm], label=algorithm, marker="o", drawstyle=drawstyle, linestyle='solid')
        ax[1].plot(percentages, times[algorithm], label=algorithm, marker="o", drawstyle=drawstyle, linestyle='solid')
        ax[2].plot(percentages, iterations[algorithm], label=algorithm, marker="o", drawstyle=drawstyle, linestyle='solid')

    ax[0].legend(loc="best")
    ax[1].legend(loc="best")
    ax[2].legend(loc="best")
    return fig, ax
