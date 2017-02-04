# simulation-bytecode-injection
==================================================
## This project helps simulation to inject Java byte code into service during runtime.

# How to run an example?
1. mvn clean install
2. java -cp ${classpath} -javaagent:target/simulation-bytecode-injection-${version}.jar com.paypal.risk.simulation.app.App
For example:
Y:\_Simulation\simulation-bytecode-injection>java -cp ./asm-5.0.3.jar;target\simulation-bytecode-injection-0.0.1-SNAPSHOT.jar \
                                                  -javaagent:target\simulation-bytecode-injection-0.0.1-SNAPSHOT.jar \
                                                  com.paypal.risk.simulation.app.App
3. Similar contents as below should be printed to console:
Running ExampleAgent::premain() - BEGIN
Running ExampleAgent::premain() - END
Running App::main() - BEGIN
transforming class AbstractBaseVariable
Visiting putReturnValueInContext()
AbstractBaseVariable
Hello world!
Running App::main() - END
