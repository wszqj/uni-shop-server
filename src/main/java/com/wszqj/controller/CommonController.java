package com.wszqj.controller;

import com.wszqj.constant.BaseConstant;
import com.wszqj.mapper.UserMapper;
import com.wszqj.pojo.entry.User;
import com.wszqj.pojo.result.Result;
import com.wszqj.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * 通用控制器，处理文件上传
 */
@RestController
@RequestMapping("/common")
@Slf4j
@RequiredArgsConstructor
public class CommonController {

    private final UserMapper userMapper;

    // 从配置文件中读取上传目录
    @Value("${file.upload-dir}")
    private String uploadDir;

    // 静态资源访问路径前缀
    private static final String FILE_ACCESS_URL_PREFIX = "/uploads/";
    /**
     * 处理文件上传请求
     *
     * @param file 上传的文件
     * @return 包含上传状态和文件 URL 的响应
     */
    @PostMapping("/upload")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("上传文件：{}", file.getOriginalFilename());

        if (file.isEmpty()) {
            return Result.error("未选择文件进行上传");
        }
        try {
            // 确保上传目录存在
            File directory = new File(uploadDir + FILE_ACCESS_URL_PREFIX);
            if (!directory.exists() && !directory.mkdirs()) {
                return Result.error("文件夹创建失败");
            }
            // 获取文件的原始名称
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                throw new IllegalArgumentException("无效的文件名");
            }
            // 创建文件的存储路径
            Path path = Paths.get(uploadDir + FILE_ACCESS_URL_PREFIX, originalFilename);
            // 将文件写入到指定路径
            Files.copy(file.getInputStream(), path);
            // 生成可以供前端访问的 URL 地址
            String fileUrl = FILE_ACCESS_URL_PREFIX + originalFilename;
            // 获取当前操作用户信息
            Map<String, Object> claim = ThreadLocalUtil.get();
            Integer userId = (Integer) claim.get(BaseConstant.ID);
            // 更新数据库
            userMapper.updateById(User.builder()
                    .id(userId)
                    .avatar(fileUrl)
                    .build());
            return Result.success(fileUrl, "文件上传成功");
        } catch (IOException e) {
            log.error("文件上传失败：", e);
            return Result.error("文件上传失败");
        }
    }
}
