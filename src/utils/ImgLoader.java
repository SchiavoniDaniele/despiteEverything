package utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
public class ImgLoader implements HttpHandler{
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String percorsoUri = exchange.getRequestURI().getPath();
		File file = new File("src/resources" + percorsoUri);

		if (file.exists() && !file.isDirectory()) {
			String mime = Files.probeContentType(file.toPath());
			exchange.getResponseHeaders().set("Content-Type", mime);
			byte[] fileEsterno = Files.readAllBytes(file.toPath());
			exchange.sendResponseHeaders(200, fileEsterno.length);
			try(OutputStream os=exchange.getResponseBody()) {
				os.write(fileEsterno);
			}
		} else {
			exchange.sendResponseHeaders(404, -1);
		}

	}
}
