package com.bandeira.clinica_alves_oliveira.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class UploadService {

    public boolean uploadImage(MultipartFile imagem){

        boolean sucessUpload = false;

        if(!imagem.isEmpty()){
            String nameFile = imagem.getOriginalFilename();
            try {

                String pastaUploadImagem = "C:\\projetos\\alves-oliveirasssss\\src\\main\\resources\\static\\images\\img-uploads";
                File dir = new File(pastaUploadImagem);
                if(!dir.exists()){
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath() + File.separator + nameFile);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

                stream.write(imagem.getBytes());
                stream.close();

                System.out.println("Stored in: " + serverFile.getAbsolutePath());
                System.out.println("Download file: " + nameFile + " successfully!");

            } catch (Exception e) {
                System.out.println("Error loading file" + nameFile + " =>" + e.getMessage());
            }
        }else
            System.out.println("");

        return sucessUpload;
    }
}
