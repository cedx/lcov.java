# LCOV parsing
The `Report.parse()` static method parses a [LCOV](https://github.com/linux-test-project/lcov) coverage report provided as string,
and returns an [`Optional<Report>`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Optional.html).
The `Report` instance gives detailed information about the provided coverage report:

```java
import io.belin.lcov.Report;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class Program {
  public static void main(String... args) throws IOException {
    var result = Report.parse(Files.readString(Path.of("share/lcov.info")));
    if (result.isEmpty()) System.err.println("The coverage data is empty or invalid.");
    else {
      var report = result.get();
      System.out.printf("The coverage report contains %d source files:%n", report.sourceFiles.size());
      System.out.println(JsonbBuilder.create(new JsonbConfig().withFormatting(true)).toJson(report));
    }
  }
}
```

Converting the `Report` instance to [JSON](https://www.json.org) format will return a map like this:

```json
{
  "testName": "Example",
  "sourceFiles": [
    {
      "path": "/home/cedx/lcov.java/fixture.java",
      "branches": {
        "found": 0,
        "hit": 0,
        "data": []
      },
      "functions": {
        "found": 1,
        "hit": 1,
        "data": [
          {"functionName": "main", "lineNumber": 4, "executionCount": 2}
        ]
      },
      "lines": {
        "found": 2,
        "hit": 2,
        "data": [
          {"lineNumber": 6, "executionCount": 2, "checksum": "PF4Rz2r7RTliO9u6bZ7h6g"},
          {"lineNumber": 9, "executionCount": 2, "checksum": "y7GE3Y4FyXCeXcrtqgSVzw"}
        ]
      }
    }
  ]
}
```

> See the [API reference](api/) of this library for more information on the `Report` class.
