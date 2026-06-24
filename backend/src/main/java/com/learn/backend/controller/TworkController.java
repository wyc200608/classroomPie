package com.learn.backend.controller;

import com.learn.backend.common.Result;
import com.learn.backend.entity.Twork;
import com.learn.backend.service.TworkService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 教师作业 Controller
 */
@RestController
@RequestMapping("/twork")
public class TworkController {

    private static final Logger log = Logger.getLogger(TworkController.class.getName());

    private final TworkService tworkService;

    public TworkController(TworkService tworkService) {
        this.tworkService = tworkService;
    }

    @PostMapping("/findWorksForTeacher")
    public Result<List<Map<String, Object>>> findWorksForTeacher(@RequestBody Map<String, String> params) {
        String id = params.get("id");
        return Result.success(tworkService.findTworkByCourseIdForTeacher(id));
    }

    @PostMapping("/findWorksForStudent")
    public Result<List<Map<String, Object>>> findWorksForStudent(@RequestBody Map<String, String> params) {
        String cid = params.get("cid");
        String sid = params.get("sid");
        return Result.success(tworkService.findTworkByCourseIdForStudent(cid, sid));
    }

    @PostMapping("/findWorksByTwid")
    public Result<Map<String, Object>> findWorksByTwid(@RequestBody Map<String, String> params) {
        String id = params.get("id");
        return Result.success(tworkService.findTworkByTwid(id));
    }

    @PostMapping("/update")
    public Result<Void> update(@RequestBody Twork twork) {
        int result = tworkService.update(twork);
        return result >= 1 ? Result.success() : Result.failed();
    }

    @PostMapping("/delete")
    public Result<Void> delete(@RequestBody Map<String, String> params) {
        String id = params.get("id");
        int result = tworkService.delete(id);
        return result >= 1 ? Result.success() : Result.failed();
    }

    @PostMapping("/insert")
    public Result<Map<String, String>> insert(@RequestBody Twork twork) {
        String twid = String.valueOf(new Date().getTime());
        Twork newTwork = new Twork(
            twid,
            twork.tid(),
            twork.cid(),
            twork.wtitle(),
            twork.tcontent(),
            twork.tpublish(),
            twork.deadline(),
            twork.scale()
        );
        int result = tworkService.insert(newTwork);
        return Result.success(Map.of("result", String.valueOf(result), "twid", twid));
    }

    @PostMapping("/getNameList")
    public Result<List<Map<String, Object>>> getNameList(@RequestBody Map<String, String> params) {
        String id = params.get("id");
        return Result.success(tworkService.getNameList(id));
    }
}
