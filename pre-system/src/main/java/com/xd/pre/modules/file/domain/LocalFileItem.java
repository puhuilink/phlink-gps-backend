package com.xd.pre.modules.file.domain;

import com.xd.pre.common.exception.PreBaseException;

import java.io.*;

public class LocalFileItem extends PersistFileItem {
    String root = null;

    public LocalFileItem(String root) {
        this.root = root;
    }

    @Override
    public byte[] getFileByte() {
        File file = new File(root + File.separator + path);
        InputStream input = null;
        try {
            input = new FileInputStream(file);
            this.fileByte = new byte[input.available()];
            input.read(fileByte);
        } catch (FileNotFoundException e) {
            throw new PreBaseException("文件不存在 " + path);
        }catch (IOException e) {
            throw new PreBaseException("文件读取失败" + path);
        }finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileByte;
    }

    public OutputStream openOutpuStream() {
       File file = new File(root + File.separator + path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            return fos;
        } catch (IOException e) {
            throw new PreBaseException("Open stream error " + path);
        }
    }

    @Override
    public void copy(OutputStream os) {
        File file = new File(root + File.separator + path);
        FileInputStream input = null;
        try {
            input = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                os.write(buf, 0, bytesRead);
            }

        } catch (Exception ex) {
            throw new PreBaseException("下载文件失败" + ex);
        } finally {
            try {
                input.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (path.startsWith("temp")) {
            this.delete();
        }
    }

    @Override
    public boolean delete() {
        File file = new File(root + File.separator + path);
        return file.delete();
    }
}
