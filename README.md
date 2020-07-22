# Signal-K-Alarm

## Development

With the command `sbt ~frontend/fastOptJS::webpack` the project can be run locally.
The generated javascript file is found in `frontend/target/scala-2.12/scalajs-bundler/main/frontend-fastopt-bundle.js`
and will be hot reloaded whenever changes in either of the submodules are being made, the file is hot reloaded.
To access the webpage, open the `frontend/index.html` file in a browser. The javascript file is being included in there.

## Build

With the command `sbt frontend/fullOptJS` the project can be built to be included in the sailing app frontend.
The generated file needed for this is `frontend/target/scala-2.12/scalajs-bundler/main/frontend-opt.js`

## Tests

To run the tests for the parser module, run `sbt parser/test`.
