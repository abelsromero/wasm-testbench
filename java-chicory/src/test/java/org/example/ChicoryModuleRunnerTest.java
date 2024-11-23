package org.example;

import com.dylibso.chicory.runtime.ExportFunction;
import com.dylibso.chicory.runtime.Instance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.Options.REDIRECT_OUTPUT;

class ChicoryModuleRunnerTest {

    private static final String NUMBERS_MODULE = "random_numbers.wasm";
    private static final String HELLO_MODULE = "hello.wasm";
    private static final String ADD_LIBRARY = "library.wasm";

    private final ChicoryModuleRunner runner = new ChicoryModuleRunner();

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

    @ParameterizedTest
    @MethodSource("compilationTargets")
    void should_load_Rust_library(String target) {
        final String modulePath = moduleLocation(ADD_LIBRARY, target);

        LoadResult result = runner.load(modulePath, List.of());
        Instance instance = (Instance) result.instance();

        ExportFunction start = instance.export("add");

        long[] apply = start.apply(42, 73);
        assertThat(apply).hasSize(1);
        assertThat(apply[0]).isEqualTo(115);
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
