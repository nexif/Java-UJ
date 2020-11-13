import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

public class ReflectCompiler {

    static FunctionInterface newInstance(String source, String className) {

        FunctionInterface instance = null;

        try {

            File sourceFile = new File("out/production/RefleksjaMoje/" + className + ".java");
            Files.writeString(sourceFile.toPath(), source);

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            compiler.run(null, null, null, sourceFile.getPath());
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { sourceFile.toURI().toURL() });
            Class<?> cls = Class.forName("Function", true, classLoader);
            System.out.println("Compiled");

            instance = (FunctionInterface) cls.newInstance();
            System.out.println(instance);

        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return instance;
    }
}
