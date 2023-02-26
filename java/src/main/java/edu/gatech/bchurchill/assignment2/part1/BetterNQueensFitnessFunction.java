package edu.gatech.bchurchill.assignment2.part1;

import opt.EvaluationFunction;
import opt.ga.BoardLocation;
import opt.ga.NQueensBoardGame;
import shared.Instance;

import java.util.List;

public class BetterNQueensFitnessFunction implements EvaluationFunction {
    private int fEvals = 0;
    private NQueensBoardGame currentBoard;

    public BetterNQueensFitnessFunction() {
    }

    public double value(Instance d) {
        ++this.fEvals;
        double fitness = 0.0;
        NQueensBoardGame board = this.getBoardForGivenInstance(d);
        this.currentBoard = board;
        int boardSize = board.getSize();
        List<BoardLocation> qPositions = board.getQueenPositions();
        boolean solved = true;

        for(int fromX = 0; fromX < boardSize - 1; ++fromX) {
            for(int toX = fromX + 1; toX < boardSize; ++toX) {
                int fromY = (qPositions.get(fromX)).getYCoOrdinate();
                boolean nonAttackingPair = true;
                if (board.queenExistsAt(new BoardLocation(toX, fromY))) {
                    nonAttackingPair = false;
                    solved = false;
                }

                int toY = fromY - (toX - fromX);
                if (toY >= 0 && board.queenExistsAt(new BoardLocation(toX, toY))) {
                    nonAttackingPair = false;
                    solved = false;
                }

                toY = fromY + (toX - fromX);
                if (toY < boardSize && board.queenExistsAt(new BoardLocation(toX, toY))) {
                    nonAttackingPair = false;
                    solved = false;
                }

                if (nonAttackingPair) {
                    ++fitness;
                }
            }
        }

        return fitness;
    }

    public NQueensBoardGame getBoardForGivenInstance(Instance d) {
        int boardSize = d.size();
        NQueensBoardGame board = new NQueensBoardGame(boardSize);

        for(int i = 0; i < boardSize; ++i) {
            int pos = d.getDiscrete(i);
            board.addQueenAt(new BoardLocation(i, pos));
        }

        return board;
    }

    public String boardPositions() {
        return this.currentBoard.toString();
    }

    public int getFunctionEvaluations() {
        return this.fEvals;
    }

    public void resetFunctionEvaluationCount() {
        this.fEvals = 0;
    }
}
