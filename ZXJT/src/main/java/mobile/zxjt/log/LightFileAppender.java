package mobile.zxjt.log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorCode;

import up.light.LightContext;
import up.light.folder.FolderTypes;
import up.light.util.StringUtil;

public class LightFileAppender extends FileAppender {
	private static final String DEFAULT_FILENAME_PATTERN = "'log.'yyyy.MM.dd-HH.mm.ss'.txt'";
	private String mNamePattern;

	public LightFileAppender() {
	}

	// use this constructor when no properties or xml file found
	public LightFileAppender(Layout layout, String namePattern) throws IOException {
		super(layout, getFileName());
	}

	public String getNamePattern() {
		return mNamePattern;
	}

	public void setNamePattern(String namePattern) {
		mNamePattern = namePattern;
	}

	// call this method when properties or xml file exists
	@Override
	public void activateOptions() {
		try {
			setFile(getFileName(), fileAppend, bufferedIO, bufferSize);
		} catch (IOException e) {
			errorHandler.error("setFile(" + fileName + "," + fileAppend + ") call failed.", e,
					ErrorCode.FILE_OPEN_FAILURE);
		}
	}

	/*
	 * 因日志直接生成在报告目录暂时不使用此方法
	 */
	@Deprecated
	static String getFileName(String namePattern) {
		if (!StringUtil.hasText(namePattern)) {
			namePattern = DEFAULT_FILENAME_PATTERN;
		}
		String folder = LightContext.getFolderPath(FolderTypes.REPORT);
		SimpleDateFormat sdf = new SimpleDateFormat(namePattern);
		return folder + sdf.format(new Date());
	}

	private static String getFileName() {
		String folder = LightContext.getFolderPath(FolderTypes.REPORT);
		return folder + "log.txt";
	}

}
