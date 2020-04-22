package up.light.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public interface IResource {

	boolean exists();

	boolean isReadable();

	boolean isOpen();

	URL getURL() throws IOException;

	URI getURI() throws IOException;

	File getFile() throws IOException;

	InputStream getInputStream() throws IOException;

	String getFilename();

	String getDescription();
}
