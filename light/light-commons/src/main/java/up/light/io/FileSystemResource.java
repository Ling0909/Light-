package up.light.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import up.light.util.Assert;

public class FileSystemResource extends AbstractResource {
	private File file;

	public FileSystemResource(String file) {
		Assert.notNull(file, "File must not be null");
		this.file = new File(file);
	}

	public FileSystemResource(File file) {
		Assert.notNull(file, "File must not be null");
		this.file = file;
	}

	@Override
	public boolean exists() {
		return this.file.exists();
	}

	@Override
	public boolean isReadable() {
		return (this.file.canRead() && !this.file.isDirectory());
	}

	@Override
	public URL getURL() throws IOException {
		return this.file.toURI().toURL();
	}

	@Override
	public URI getURI() throws IOException {
		return this.file.toURI();
	}

	@Override
	public File getFile() throws IOException {
		return this.file;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(this.file);
	}

	@Override
	public String getFilename() {
		return this.file.getName();
	}

	@Override
	public String getDescription() {
		return "file [" + this.file.getAbsolutePath() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return (obj == this
				|| (obj instanceof FileSystemResource && this.file.equals(((FileSystemResource) obj).file)));
	}

	@Override
	public int hashCode() {
		return this.file.hashCode();
	}
}
