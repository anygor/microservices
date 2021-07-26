package com.epam.mentoring.converter;

import com.epam.mentoring.storage.StorageException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Random;

public class FileConverter {
	public static FileSystemResource multipartFileToFile(MultipartFile multipartFile) {
		try {
			if (multipartFile.isEmpty()) {
				throw new StorageException("Failed to store empty file " + multipartFile.getOriginalFilename());
			}
			final Path location = Paths.get("uploads");
			if (!Files.isDirectory(location)) {
				Files.createDirectories(location);
			}

			String filename = multipartFile.getOriginalFilename();
			filename =
					new Date().getTime()
							+ "-"
							+ (10000 + new Random().nextInt(100000))
							+ "-"
							+ filename; // prevend duplicate time

			Files.copy(multipartFile.getInputStream(), location.resolve(filename));

			return new FileSystemResource(new File(location + "/" + filename));

		} catch (IOException e) {
			throw new StorageException("Failed to store file " + multipartFile.getOriginalFilename(), e);
		}
	}
}
