package mobile.zxjt.report.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.FileUtils;

import up.light.LightContext;
import up.light.folder.FolderTypes;

public abstract class DBConnection {
	private static Connection conn;

	public static Connection getConnection() throws SQLException, IOException {
		if (conn == null) {
			String vFilePath = LightContext.getFolderPath(FolderTypes.REPORT) + "cache.db";
			File vFile = new File(vFilePath);
			if (!vFile.exists()) {
				FileUtils.copyInputStreamToFile(DBConnection.class.getResourceAsStream("template.db"), vFile);
			}
			String vConnStr = "jdbc:sqlite:" + vFilePath;
			conn = DriverManager.getConnection(vConnStr);

			// 开启外键
			Statement vStmt = conn.createStatement();
			vStmt.execute("PRAGMA foreign_keys = ON;");
			vStmt.close();
		}
		return conn;
	}

	public static void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}

}
