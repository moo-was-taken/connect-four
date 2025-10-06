# Connect Four

**Connect Four** is a terminal-based Java implementation of the classic game explored and documented extensively through the lens of zero-sum algorithms and game tree search.

## Diver Deeper

Documentation found in this repository is meant to be highly human-readable. If you're interested in how board games are represented and solved in computer science, I highly, *highly* recommend starting [here](docs/heart-of-an-engine.md).

## Testing

This project uses **Junit 6.0.0** for automated unit testing.

### Run all tests

```bash
mvn test
```

## Building

> [!NOTE]
> This project uses [Apache Maven](https://maven.apache.org/) for build automation. Ensure Maven (v.38+) is installed and available in your system PATH.

### Cloning and Compiling

```bash
git clone https://github.com/moo-was-taken/connectfour.git
cd connectfour
mvn clean package
```

### Running

```bash
java -jar target/connectfour-1.0-SNAPSHOT.jar
```