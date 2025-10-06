# Bitboards - The Heart of an Engine

Hey, my name's moo. Let's talk connect 4. :)

> *\*What do you mean technical documentation has to be boring?? Fine... I'll make a boring, concise one later so boring, concise people don't feel too left out...*\*

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
}; // 0 = empty, 1 = red, 2 = black
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
    check_in_direction(cell: {col, row}, direction: "RIGHT"); // horizontal
    check_in_direction(cell: {col, row}, direction: "DOWN"); // vertical
    check_in_direction(cell: {col, row}, direction: "DOWN-LEFT"); // diagonal --> '/'
    check_in_direction(cell: {col, row}, direction: "DOWN-RIGHT"); // diagonal --> '\'

    // --> capture direction counts. if any depth counted four pieces contiguously, we found a winner!
  }
}
```

Not too bad. We could optimize this further by recognizing that in a legal game of Connect-4, part of the winning sequence will always be at the top of one of the columns. But there's an even better way. Ready?

*\*drumroll intensifies\**

## What's a Bitboard...??

Most people make bitboards sound like sorcery. It’s not — but it may feel that way until *\*you*\* become the wizard. Lucky for you, my dear apprentice, today's the day to impress your friends. Is it with knowledge that over the years they've learned to disregard? Probably. :)

### Okay fine - don't tell me. Then what's a bit...???

Glad you asked. A __**bit**__ is the smallest unit of information present in the universe (probably). While it builds the instruction set used to, y'know, run the computer you're reading from, far more important, is the potential to (mis)use it for our own personal mischief. This might be hard to believe, considering a bit can only tell us if it's on (1) or off (0). That's true — or false ;) — until they begin to band together.

... But what would a `11` mean? Or `0101010110101` for that matter? The second one might be an evil spirit, although `11` definitely means 4. But, really, to understand bits, we must talk about numbers themselves — how they mean anything at all!

### Uh, yeah, I know what the decimal system is.

What does "942" mean to you? Yes, I know it's the number of days since your last shower, but not that.

9 *100's*, 4 *10's*, 2 *1's*. `900 + 40 + 2 = 942.` But what if it meant: \
9 *81's*, 4 *9's*, 2 *1's*. `729 + 36 + 1 = 756.`

There's not actually anything inherently telling us that, for example, the 10's place *must* carry units of 10 (*\*Wait moooo... not the insane asylum...*\*). In fact we could carry whatever number we wanted — even numbers greater than 10! ...it just might not always be practical. :)

### Binary — a practical numbering system

To bit or to not to bit — that is the question... posed by your CPU trillions of times per second. 

See, binary is what happens when numbers stop trying to impress anyone. It holds up its hand and says: *\*"Look, I've only got TWO fingers. YOU deal with it."*\*

And somehow we did. We dealt with it by counting in powers of two instead of powers of ten, and in the process quietly retiring the numbers 2 through 9. For example:

```java
Binary
 Language:    1 0 1 1
- - - - - - - - - - -
English
 Translation: 8 4 2 1
```

In other words, we have one copy of `8`, zero copies of `4`, one copy of `2`, and one copy of `1` for a grand total of `11` (`8+2+1`). `1011` in binary means `11`.

That's it. That's the whole trick. Binary is merely counting with only two symbols - 0 and 1. In fact it's so simple, that's it's brilliant. Every transistor, wire or gate in your computer only has to care about *two states*: on or off, current or no current, yes or no.

And, incidentally, a **bit** is the base currency system of binary. `1011` has 4 bits and `0101010110101` has 13 bits. We even have names for different denominations. A minted 4-bit coin is called a **nibble** and an 8-bit one, a **byte**. A 32-bit unit is usually a **dword**, although interestingly, early collectors have dwords worth only 16 bits. 

So, the big reveal? As it turns out, a **bitboard** is a type of 64-bit coin (a **qword** for you nerds out there). Aha! ...But wait, how exactly are we meant to represent a connect four board with pocket change?

## Bitboards — Revisited

The more astute of you may have noted that the connect four board does, in fact, only have 42 cells (7 across and 6 down). A keen observation which deserves a pat on the back. Go ahead - I'll wait.

But in all seriousness, computers do really like powers of two - as in really, *really* like. Powers of two (specifically 64) fits snugly inside the way (most) CPUs store and move data. It becomes less important that we use *all* 64-bits, and more important that we use *no more than* 64-bits. Any leftovers are padded with zeroes.

Let's demonstrate a possible bitboard representation of our original board from the opening [section](#an-introduction-to-board-representations).

```java
long board =  // long is guaranteed to be 64-bits in Java
0b            // tells the compiler this is a binary number
  0 0 0 0 0 0 0
  0 0 0 0 1 1 0
  0 0 1 1 1 1 0
  1 0 1 1 1 1 1
  1 1 1 1 1 1 1
  1 1 1 1 1 1 1
; // 0 = empty, 1 = piece
```

> [!TIP]
> There wouldn't actually be any spaces or line breaks in code, but is simply used here to depict the values visually. There is also technically padding around the "edges" to make it exactly 64 bits, but this implied and where we put it specifically can be subjective.
>
> In real code it might look something like this: `long board = 0b111000110000  /* (... etc.) */;`. This starts from the bottom left corner and works up each column, left to right. Both paradigms have special names, which we'll cover in the next section. :)

Wow! That's pretty nifty, but I can year you say, "*\*Wait, but we're only representing pieces in general now? How do we know who they belong to??*\*". Patience, young grasshopper, patience - all in due time.

### Why bitboards though?

Remember this pseudo code from earlier?

```java
for (row : rows) { // iterate over all row
  for (col : cols) { // iterate over all columns
    check_in_direction(cell: {col, row}, direction: "RIGHT"); // horizontal
    check_in_direction(cell: {col, row}, direction: "DOWN"); // vertical
    check_in_direction(cell: {col, row}, direction: "DOWN-LEFT"); // diagonal --> '/'
    check_in_direction(cell: {col, row}, direction: "DOWN-RIGHT"); // diagonal --> '\'

    // --> capture direction counts. if any depth counted four pieces contiguously, we found a winner!
  }
}
```

What if I were to tell you we can collapse all of this code (iteration, cells, direction — everything!) using the power of bitboards. Currently, we have to check *every* cell in *every* direction (`42` cells x `4` directions x `4` max pieces = `168` checks in a worst-case scenario). But we can do better —  we can narrow this down to... *\*drumroll*\*... a total of `4` - yes 4! - total checks.

Prepare to have your socks thoroughly knocked off. See you in the [next section](language-of-the-bits.md)!