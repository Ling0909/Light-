package up.light.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import up.light.util.ClassUtil;
import up.light.util.ObjectUtil;
import up.light.util.ResourceUtil;
import up.light.util.StringUtil;

public class ClassPathResource extends AbstractResource {
	private final String mPath;
	private ClassLoader mClassLoader;
	private Class<?> mClazz;

	public ClassPathResource(String path) {
		this.mPath = path;
	}

	public ClassPathResource(String path, ClassLoader classLoader) {
		this.mPath = path;
		this.mClassLoader = classLoader;
	}

	public ClassPathResource(String path, ClassLoader classLoader, Class<?> clazz) {
		this.mPath = path;
		this.mClassLoader = classLoader;
		this.mClazz = clazz;
	}

	@Override
	public String getDescription() {
		StringBuilder vBuilder = new StringBuilder("class path resource [");
		String vPathToUse = mPath;
		if (this.mClazz != null && !vPathToUse.startsWith("/")) {
			vBuilder.append(ClassUtil.classPackageAsResourcePath(this.mClazz));
			vBuilder.append('/');
		}
		if (vPathToUse.startsWith("/")) {
			vPathToUse = vPathToUse.substring(1);
		}
		vBuilder.append(vPathToUse);
		vBuilder.append(']');
		return vBuilder.toString();
	}

	@Override
	public boolean exists() {
		return (resolveURL() != null);
	}

	@Override
	public boolean isReadable() {
		try {
			URL vUrl = getURL();
			if (ResourceUtil.isFileURL(vUrl)) {
				File vFile = getFile();
				return (vFile.canRead() && !vFile.isDirectory());
			} else {
				return true;
			}
		} catch (IOException ex) {
			return false;
		}
	}

	@Override
	public URL getURL() throws IOException {
		URL vUrl = resolveURL();
		if (vUrl == null) {
			throw new FileNotFoundException(getDescription() + " cannot be resolved to URL because it does not exist");
		}
		return vUrl;
	}

	@Override
	public File getFile() throws IOException {
		URL vUrl = getURL();
		return ResourceUtil.getFile(vUrl, getDescription());
	}

	@Override
	public InputStream getInputStream() throws IOException {
		InputStream vIn;
		if (this.mClazz != null) {
			vIn = this.mClazz.getResourceAsStream(this.mPath);
		} else if (this.mClassLoader != null) {
			vIn = this.mClassLoader.getResourceAsStream(this.mPath);
		} else {
			vIn = ClassLoader.getSystemResourceAsStream(this.mPath);
		}
		if (vIn == null) {
			throw new FileNotFoundException(getDescription() + " cannot be opened because it does not exist");
		}
		return vIn;
	}

	@Override
	public String getFilename() {
		return StringUtil.getFilename(this.mPath);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof ClassPathResource) {
			ClassPathResource otherRes = (ClassPathResource) obj;
			return (this.mPath.equals(otherRes.mPath)
					&& ObjectUtil.nullSafeEquals(this.mClassLoader, otherRes.mClassLoader)
					&& ObjectUtil.nullSafeEquals(this.mClazz, otherRes.mClazz));
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.mPath.hashCode();
	}

	private URL resolveURL() {
		if (this.mClazz != null) {
			return this.mClazz.getResource(this.mPath);
		} else if (this.mClassLoader != null) {
			return this.mClassLoader.getResource(this.mPath);
		} else {
			return ClassLoader.getSystemResource(this.mPath);
		}
	}
}
