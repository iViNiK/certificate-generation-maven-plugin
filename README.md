# certificate-generation-maven-plugin

Maven plugin that creates a SSL key pair and a certificate.

It defines a goal named "certgen" in "building resources" lifecycle.

To use it you can add following lines to the target project pom.xml:

			  <plugin>
                <groupId>it.vinicioflamini</groupId>
                <artifactId>certificate-generation-maven-plugin</artifactId>
                <version>0.0.1-SNAPSHOT</version>
                <executions>
                    <execution>
                        <configuration>
                            <outputDirectory>${project.basedir}/src/main/resources</outputDirectory>
                        </configuration>
                        <goals>
                            <goal>certgen</goal>
                        </goals>
                        <phase>none</phase>  
                    </execution>
                </executions>
            </plugin>
