package org.example;

import com.dylibso.chicory.log.Logger;
import com.dylibso.chicory.log.SystemLogger;
import com.dylibso.chicory.runtime.ImportValues;
import com.dylibso.chicory.runtime.Instance;
import com.dylibso.chicory.runtime.Store;
import com.dylibso.chicory.wasi.WasiOptions;
import com.dylibso.chicory.wasi.WasiPreview1;
import com.dylibso.chicory.wasm.Module;
import com.dylibso.chicory.wasm.Parser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Runs WASM module.
 */
public class ChicoryModuleRunner implements org.example.Module {

    public String run(String modulePath) {
        return run(modulePath, List.of());
    }

    public String run(String modulePath, List<String> args) {
        final var result = load(modulePath, args);
        // NOTE: Also works. Runs _start on init, hence main method is run.
        // createInstance(wasi, module);
        return result.output();
    }

    public RunResult load(String modulePath, List<String> args) {
        final Module module = Parser.parse(new File(modulePath));

        final Logger logger = new SystemLogger();
        final var fakeStdout = new MockPrintStream();

        final WasiOptions options = WasiOptions.builder()
            .withStderr(fakeStdout)
            .withStdout(fakeStdout)
            .withArguments(args)
            .build();
        final WasiPreview1 wasi = new WasiPreview1(logger, options);

        Instance instance = createStore(wasi, module);

        return new RunResult(instance, fakeStdout.output());
    }

    private static Instance createInstance(WasiPreview1 wasi, Module module) {
        ImportValues imports = ImportValues.builder()
            .addFunction(wasi.toHostFunctions())
            .build();
        return Instance.builder(module)
            .withImportValues(imports)
            .build();
    }

    private static Instance createStore(WasiPreview1 wasi, Module module) {
        return new Store()
            .addFunction(wasi.toHostFunctions())
            .instantiate("my-module", module);
    }

    record RunResult(Instance instance, String output) {
    }

    private final class MockPrintStream extends PrintStream {
        private final ByteArrayOutputStream baos;

        public MockPrintStream() {
            super(new ByteArrayOutputStream());
            this.baos = (ByteArrayOutputStream) this.out;
        }

        @Override
        public void println(String s) {
            super.println(s);
        }

        public String output() {
            return baos.toString(UTF_8);
        }
    }

}
