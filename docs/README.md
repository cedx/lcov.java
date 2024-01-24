# LCOV Reports for Java
Parse and format [LCOV](https://github.com/linux-test-project/lcov) coverage reports,
in [Java](https://www.oracle.com/java).

## Quick start
Download the latest JAR file of **LCOV Reports for Java** from the GitHub releases:  
https://github.com/cedx/lcov.java/releases/latest

Add it to your class path. Now in your [Java](https://www.oracle.com/java) code, you can use:

```java
import io.belin.lcov.*;
```

## Usage
This library provides a set of classes representing a [LCOV](https://github.com/linux-test-project/lcov) coverage report and its data.
The `Report` class, the main one, provides the parsing and formatting features.

- [Parse coverage data from a LCOV file](usage/parsing.md)
- [Format coverage data to the LCOV format](usage/formatting.md)

## See also
- [API reference](api/)
- [GitHub releases](https://github.com/cedx/lcov.java/releases)
