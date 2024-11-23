package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.Options.REDIRECT_OUTPUT;

class WasmerModuleRunnerTest {

    private static final String NUMBERS_MODULE = "random_numbers.wasm";
    private static final String HELLO_MODULE = "hello.wasm";

    private final ModuleRunner runner = new WasmerModuleRunner();

    @ParameterizedTest
    @MethodSource("compilationTargets")
    void should_run_Rust_simple_cli(String target) {
        final String modulePath = moduleLocation(NUMBERS_MODULE, target);

        String output = runner.run(modulePath);

        assertThat(output)
            .startsWith("Hello from Rust!")
            .endsWith("Values generated: 1000\n");
    }

    @ParameterizedTest
    @MethodSource("compilationTargets")
    void should_run_Rust_with_args(String target) {
        final String modulePath = moduleLocation(HELLO_MODULE, target);

        String output = runner.run(modulePath, List.of("Testy"), REDIRECT_OUTPUT);

        assertThat(output).isEqualTo("Hello Testy!\n");
    }

    private static String moduleLocation(String name, String target) {
        return "../rust/target/%s/release/%s".formatted(target, name);
    }

    private static Stream<Arguments> compilationTargets() {
        return Stream.of(
            Arguments.of("wasm32-unknown-unknown"),
            Arguments.of("wasm32-wasi"),
            Arguments.of("wasm32-wasip1"),
            Arguments.of("wasm32-wasip2")
        );
    }
}
