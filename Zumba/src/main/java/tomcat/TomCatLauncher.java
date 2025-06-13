package tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.catalina.webresources.DirResourceSet;

import java.io.File;

public class TomCatLauncher {
    public static void main(String[] args) throws LifecycleException {
        System.out.println("Initializing Tomcat Launcher ");
        Tomcat tomcat = new Tomcat();

        // 1) HTTP port
        int port = 8082;
        tomcat.setPort(port);
        tomcat.getConnector();
        System.out.println("Connector created at port " + port);

        // 2) Deploy webapp from src/main/webapp
        String webAppDir = new File("src/main/webapp").getAbsolutePath();
        String contextPath = "/JSP";
        Context ctx = tomcat.addWebapp(contextPath, webAppDir);

        //3) Mount your compiled classes ** into WEB-INF/classes
        WebResourceRoot resources = new StandardRoot(ctx);
        String classesDir = new File("target/classes").getAbsolutePath();
        resources.addPreResources(new DirResourceSet(
                resources,
                "/WEB-INF/classes",
                classesDir,
                "/"
        ));
        ctx.setResources(resources);

        //4) start
        System.out.println("tomcat starting");
        tomcat.start();
        System.out.println("tomcat started successfully. visit http://localhost:"
            + port + contextPath + "/");
        tomcat.getServer().await();
    }
}
