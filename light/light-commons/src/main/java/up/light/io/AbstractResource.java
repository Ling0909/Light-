package up.light.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import up.light.util.ResourceUtil;

public abstract class AbstractResource implements IResource {

	@Override
	public boolean exists() {
		try {
			return getFile().exists();
		} catch (IOException ex) {
			try {
				InputStream vIn = getInputStream();
				vIn.close();
				return true;
			} catch (Throwable isEx) {
				return false;
			}
		}
	}

	@Override
	public boolean isReadable() {
		return true;
	}

	@Override
	public boolean isOpen() {
		return false;
	}

	@Override
	public URL getURL() throws IOException {
		throw new FileNotFoundException(getDescription() + " cannot be resolved to URL");
	}

	@Override
	public URI getURI() throws IOException {
		URL vUrl = getURL();
		try {
			return ResourceUtil.toURI(vUrl);
		} catch (URISyntaxException ex) {
			throw new IOException("Invalid URI [" + vUrl + "]", ex);
		}
	}

	@Override
	public File getFile() throws IOException {
		throw new FileNotFoundException(getDescription() + " cannot be resolved to absolute file path");
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return null;
	}

	@Override
	public String getFilename() {
		return null;
	}

	@Override
	public String toString() {
		return getDescription();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj == this
				|| (obj instanceof IResource && ((IResource) obj).getDescription().equals(getDescription())));
	}

	@Override
	public int hashCode() {
		return getDescription().hashCode();
	}
}
