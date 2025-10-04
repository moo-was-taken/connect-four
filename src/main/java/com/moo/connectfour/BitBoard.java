package com.moo.connectfour;

class BitBoard {

    // ========================================
    //  Member Constants and Data Types
    // ========================================

    private static final int NUM_COLS = 7;
    private static final int NUM_ROWS = 7;

    private long player1Board;
    private long player2Board;

    // ========================================
    //  Bit Manipulation Helpers
    // ========================================

    private static int makeUnsquare(int col, int row) {
        return (col * NUM_ROWS) + row;
    }

    private static long makeMask(int square) {
        return 1 << square;
    }

    @SuppressWarnings("unused")
    private static long makeMask(int col, int row) {
        return makeMask(makeUnsquare(col, row));
    }

    public static int getBit(long bitboard, int square) {
        return (bitboard & makeMask(square)) != 0 ? 1 : 0;
    }

    public static int getBit(long bitboard, int col, int row) {
        return getBit(bitboard, makeUnsquare(col, row));
    }

    private static long setBit(long board, int square) {
        return board | makeMask(square);
    }

    @SuppressWarnings("unused")
    private static long setBit(long board, int col, int row) {
        return board | setBit(board, makeUnsquare(col, row));
    }

    private static boolean isBitSet(long board, int square) {
        return (board & makeMask(square)) != 0;
    }

    @SuppressWarnings("unused")
    private static boolean isBitSet(long board, int col, int row) {
        return isBitSet(board, makeUnsquare(col, row));
    }

    // ========================================
    //  Constructors
    // ========================================

    public BitBoard() {

    }

    // ========================================
    //  Display
    // ========================================

    public void debugPrint() {
        System.out.println("Player 1: ");
        debugPrint(player1Board);
        System.out.println("Player 2: ");
        debugPrint(player2Board);
    }

    public static void debugPrint(long board) {
        // print column-major
        for (int col = 0; col < NUM_COLS; ++col) {
            for (int row = 0; row < NUM_ROWS; ++row) {
                System.out.printf("%d ", getBit(board, col, row));
            }
            System.out.println();
        }
        System.out.println();
    }
}
