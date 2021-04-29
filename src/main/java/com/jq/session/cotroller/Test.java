package com.jq.session.cotroller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class Test {

    @RequestMapping("/")
    public void test() {
        FastDFS.fileUpload();
    }

    @RequestMapping("/download")
    public ResponseEntity downloadFile() throws IOException {
        String groupName = "group2";
        String remoteFileName = "M00/00/00/wKhZg2CJStuARQAmAAG7ddzNwW469.jpeg";

        //调用工具类中的下载方法，返回byte[]
        byte[] bytes = FastDFS.fileDownLoad(groupName, remoteFileName);
        System.out.println(bytes.length);

        //为客户浏览器提供下载框 
        //告诉浏览器,我响应的流类型的数据,由浏览器去进行解析
        HttpHeaders headers = new HttpHeaders();
//        String filename = new String(new String("file.jpg").getBytes("utf-8"), "iso-8859-1");
        //文件名称
        headers.setContentDispositionFormData("attachment", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + ".jpg");
        //文件长度
        headers.setContentLength(bytes.length);
        //文件类型，浏览器进行下载
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //参数1，返回的byte[]
        //参数2，请求头，指定了文件名称，指定了文件长度，指定了文件的类型(流类型)
        //参数3，请求的状态，200代表OK
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }
}