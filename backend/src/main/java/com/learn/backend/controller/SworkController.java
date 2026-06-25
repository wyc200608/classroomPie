package com.learn.backend.controller;

import com.learn.backend.common.Result;
import com.learn.backend.entity.Swork;
import com.learn.backend.service.SworkService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 学生作业 Controller
 */
@RestController
@RequestMapping("/api/swork")
public class SworkController {

    private static final Logger log = Logger.getLogger(SworkController.class.getName());

    private final SworkService sworkService;

    public SworkController(SworkService sworkService) {
        this.sworkService = sworkService;
    }

    @PostMapping("/getAnswers")
    public Result<List<Map<String, Object>>> getAnswers(@RequestBody Map<String, String> params) {
        String twid = params.get("twid");
        String kind = params.get("kind");
        List<Map<String, Object>> result;
        switch (kind) {
            case "1" -> result = sworkService.getSubmit(twid);
            case "2" -> result = sworkService.getNoSubmit(twid);
            default -> result = sworkService.getAll(twid);
        }
        return Result.success(result);
    }

    @PostMapping("/updateCorrection")
    public Result<Void> updateCorrection(@RequestBody Map<String, String> params) {
        String id = params.get("id");
        String correction = params.get("correction");
        int result = sworkService.updateCorrection(id, correction);
        return result >= 1 ? Result.success() : Result.failed();
    }

    @PostMapping("/updateScore")
    public Result<Void> updateScore(@RequestBody Map<String, String> params) {
        String id = params.get("id");
        String score = params.get("score");
        int result = sworkService.updateScore(id, score);
        return result >= 1 ? Result.success() : Result.failed();
    }

    @PostMapping("/updateSContent")
    public Result<Void> updateSContent(@RequestBody Map<String, String> params) {
        String swid = params.get("swid");
        String content = params.get("content");
        int result = sworkService.updateSContent(swid, content);
        return result >= 1 ? Result.success() : Result.failed();
    }

    @PostMapping("/insert")
    public Result<Map<String, Object>> insert(@RequestBody Swork swork) {
        String swid = String.valueOf(new Date().getTime());
        Swork newSwork = new Swork(
            swid,
            swork.twid(),
            swork.sid(),
            swork.scontent(),
            swork.spublish(),
            swork.correct(),
            swork.score(),
            swork.correction()
        );
        int result = sworkService.insert(newSwork);
        return Result.success(Map.of("result", result));
    }
}
