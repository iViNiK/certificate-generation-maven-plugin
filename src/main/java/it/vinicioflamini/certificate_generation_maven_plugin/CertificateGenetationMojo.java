package it.vinicioflamini.certificate_generation_maven_plugin;

import java.io.File;
import java.io.IOException;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Goal which touches a timestamp file.
 *
 * @goal touch
 * 
 * @phase process-sources
 */

@Mojo (name = "certgen", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class CertificateGenetationMojo extends AbstractMojo {
	/**
	 * Location of the files.
	 * 
	 * @parameter expression="${project.basedir}/src/main/resources"
	 * @required
	 */
	@Parameter(defaultValue = "${project.basedir}/src/main/resources", required = true, readonly = false)
	private File outputDirectory;

	private static final String CMD_GENPAIR = "keytool -genkeypair -alias testkey -keyalg RSA -keysize 2048 -dname \"CN=cadomain.com, OU=CAOrg, O=CAOrg Inc., ST=CA, C=US\" -keypass password -validity 100 -storetype PKCS12 -keystore %s/keystore.p12 -storepass password";
	private static final String CMD_GENCERT = "keytool -exportcert -alias testkey -keypass password -storetype PKCS12 -keystore %s/keystore.p12 -file %s/server.crt -rfc -storepass password";
	
	public void execute() throws MojoExecutionException {
		File f = outputDirectory;

		if (!f.exists()) {
			f.mkdirs();
		}

		try {
			String cmd = String.format(CMD_GENPAIR, outputDirectory);
			Runtime run = Runtime.getRuntime();
			Process pr = run.exec(cmd);
			pr.waitFor();

			cmd = String.format(CMD_GENCERT, outputDirectory, outputDirectory);
			pr = run.exec(cmd);
			pr.waitFor();
		} catch (IOException|InterruptedException e) {
			throw new MojoExecutionException("Error creating certificate files ", e);
		} 
	}
}
