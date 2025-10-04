# A Comprehensive Inquiry into BitBoards

Hey, my name's moo. Let's talk connect 4. :)

(*\*What do you mean technical documentation has to be boring?? Fine... I'll make a boring, concise one later so boring, concise people don't feel too left out...*\*)

## Table of Contents
1. [Introduction](#an-introduction-to-board-representations)
2. [What Are Bitboards?](#whats-a-bitboard)
3. [Bitboards Revisited](#bitboards--revisited)

## An Introduction to Board Representations

We need a data container to describe the contents of a board. Let's try the first thing that comes to mind: 

```java
int[][] board = new int[6][7] = {
  {0, 0, 0, 0, 0, 0, 0},
  {0, 0, 0, 0, 1, 2, 0},
  {0, 0, 2, 1, 2, 1, 0},
  {1, 0, 1, 1, 2, 1, 2},
  {2, 2, 1, 2, 1, 2, 2},
  {1, 1, 2, 1, 2, 1, 2} 
}; // 0=empty, 1 = red, 2 = black
```

This works surprisingly well. It's convenient to represent visually and its contents straight forward to manipulate.

To make it easier on the eyes:

```java
// this be the board from the last code snippet
 ---------------
|        ○ ●    |
|     ● ○ ● ○   |
| ○   ○ ○ ● ○ ● |
| ● ● ○ ● ○ ● ● |
| ○ ○ ● ○ ● ○ ● |
 ---------------
```

What's the board used for? A pretty dumb question, honestly (... from an extremely smart person of course). What about checking for a win? Let's try pseudo-coding this out:

```java
for (row : rows) { // iterate over all row
  for (col : cols) { // iterate over all columns
    recurse(depth: 1, {col, row}, direction: "RIGHT"); // horizontal
    recurse(depth: 1, {col, row}, direction: "DOWN"); // vertical
    recurse(depth: 1, {col, row}, direction: "DOWN-LEFT"); // diagonal --> '/'
    recurse(depth: 1, {col, row}, direction: "DOWN-RIGHT"); // diagonal --> '\'

    // --> capture recurse depths. if any depth = 4, we found a winner!
  }
}
```

If it's not apparent, `recurse()` will continue to call itself until it hits an empty space. It'll move in the direction we provide. We need to record where in the board we are (`{col, row}`), otherwise the recursion won't know where to move to next! We also need to provide how many times we've recursed (`depth`), so we can report the results back!

Not too bad. We could optimize this further by recognizing that in a legal game of Connect-4, part of the winning sequence will always be at the top of one of the columns. But there's an even better way. Ready?

*\*drumroll intsenifies\**

## What's a Bitboard...??

Most people make bitboards sound like sorcery. It’s not — but it may feel that way until *\*you*\* become the wizard. Lucky for you, my dear apprentice, today's the day to impress your friends. Is it with knowledge that over the years they've learned to disregard? Probably. :)

### Okay fine - don't tell me. Then what's a bit...???

Glad you asked. A **bit** is the smallest unit of information present in the universe (probably). While it builds the instruction set used to, y'know, run the computer you're reading from, far more important, is the potential to (mis)use it for our own personal mischief. This might be actually pretty hard to believe considering a bit can only tell us whether it's on (1) or off (0). That's true (...or false) until they begin to band together.

... But what would a `11` mean? Or `0101010110101` for that matter? The second one might be an evil spirit, although `11` definitely means 4. But, really, to understand bits, we must talk about numbers themselves — how they mean anything at all!

### Uh, yeah, I know what the decimal system is.

What does "942" mean to you? Yes, I know it's the number of days since your last shower, but not that.

9 *100's*, 4 *10's*, 2 *1's*. `900 + 40 + 2 = 942.` But what if it meant:

9 *81's*, 4 *9's*, 2 *1's*. `729 + 36 + 1 = 756.`

There's not actually anything inherently telling us that, for example, the 10's place *must* carry units of 10 (*\*Wait noooo... not the insane asylum...*\*). In fact we could carry whatever number we wanted - even numbers greater than 10! ...it just might not always be practical. :)

### Binary — a practical numbering system

To bit or to not to bit — that is the question... posed by your CPU trillions of times per second. 

See, binary is what happens when numbers stop trying to impress anyone. It holds up its hand and says: *\*"Look, I've only got TWO fingers. YOU deal with it."*\*

And so we did deal with it. We dealt with it by counting in powers of two instead of powers of ten, and in the process quietly retiring the numbers 2 through 9. For example:

```java
Binary
 Language:    1 0 1 1
- - - - - - - - - - -
English
 Translation: 8 4 2 1
```

In other words, we have one copy of `8`, zero copies of `4`, one copy of `2`, and one copy of `1` for a grand total of `11` (`8+2+1`). `1011` in binary means `11`.

That's it. That's the whole trick. Binary is merely counting with only two symbols - 0 and 1. In fact it's so simple, that's it's brilliant. Every transistor, wire or gate in your computer only has to care about *two states*: on or off, current or no current, yes or no.

And, incidentally, a **bit** is the base currency system of binary. `1011` has 4 bits and `0101010110101` has 13 bits. We even have names for different denominations. A minted 4-bit coin is called a **nibble** and an 8-bit one, a **byte**. A 32-bit unit is usually a **dword**, but interestingly, early collectors have dwords worth only 16 bits. 

So, the big reveal? As it turns out, a **bitboard** is a type of 64-bit coin. Aha! ...But wait, how exactly are we meant to represent a connect four board with pocket change?

## Bitboards — Revisited