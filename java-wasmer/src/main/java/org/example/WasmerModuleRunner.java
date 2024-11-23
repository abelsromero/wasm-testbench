package org.example;

import org.wasmer.Exports;
import org.wasmer.Instance;
import org.wasmer.Module;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.example.Options.REDIRECT_OUTPUT;

public class WasmerModuleRunner implements ModuleRunner {

    @Override
    public String run(String modulePath) {
        return run(modulePath, List.of(), REDIRECT_OUTPUT);
    }

    @Override
    public String run(String modulePath, List<String> args, Options... options) {
        byte[] wasmBytes = loadModuleBytes(modulePath);

        var value = Module.validate(wasmBytes);
//      assert value == true;

        // WASI is not supported:
        //   Failed to instantiate the module: Error while importing "wasi_snapshot_preview1"."args_get": unknown import. Expected Function(FunctionType { params: [I32, I32], results: [I32] })
        Instance instance = new Instance(wasmBytes);

        Exports exports = instance.exports;

        instance.close();

        return "";
    }

    private static byte[] loadModuleBytes(String modulePath) {
        final Path wasmPath = Paths.get(modulePath);
        try {
            return Files.readAllBytes(wasmPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
